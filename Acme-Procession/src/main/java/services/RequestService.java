package services;

import java.sql.Date;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import utilities.CodeGenerator;
import domain.Administrator;
import domain.Brother;
import domain.Request;
import forms.AddCommentToRequestForm;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RequestRepository requestRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private BrotherService brotherService;
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public RequestService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Request create() {
		Request result;
		Brother brother;
		Date creationMoment;
		String code;
		String status;

		Assert.isTrue(actorService.isBrother(), "Request.notBrother.error");

		brother = brotherService.findByPrincipal();

		checkCanCreate(brother);

		creationMoment = new Date(System.currentTimeMillis() - 100);
		code = CodeGenerator.generate();
		status = "pending";

		result = new Request();

		result.setCode(code);
		result.setCreationMoment(creationMoment);
		result.setBrother(brother);
		result.setStatus(status);
		result.setComments(null);

		return result;
	}

	public Request save(Request request) {
		Request result;

		Assert.notNull(request);
		Assert.isTrue(
				actorService.isBrother() || actorService.isAdministrator(),
				"Request.notUserValid.error");

		result = requestRepository.save(request);

		return result;
	}

	public Request findOne(int requestId) {
		Request result;

		result = requestRepository.findOne(requestId);

		return result;
	}

	public Collection<Request> findAll() {
		Collection<Request> result;

		result = requestRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<Request> findByPrincipal() {
		Collection<Request> result;
		Brother brother;

		Assert.isTrue(actorService.isBrother());

		brother = brotherService.findByPrincipal();
		result = requestRepository.findByBrotherId(brother.getId());

		return result;
	}

	public Collection<String> AuthBrother() {
		Collection<String> result;
		Brother brother;

		Assert.isTrue(actorService.isBrother());

		brother = brotherService.findByPrincipal();
		result = requestRepository.AuthBrother(brother.getId());

		return result;
	}

	public void checkCanCreate(Brother brother) {
		Collection<String> statusRequest;

		statusRequest = AuthBrother();

		if (statusRequest.size() > 0) {
			Assert.isTrue(!statusRequest.contains("pending"),
					"request.pending.error");
			Assert.isTrue(!statusRequest.contains("accepted"),
					"request.accepted.error");
		}
	}

	public void saveRequest(Request request) {
		Assert.notNull(request, "Request.notValid.error");
		Assert.isTrue(request.getStatus().equals("pending"),
				"request.notPending.error");

		save(request);
	}

	public Collection<Request> findAllRequestPending() {
		Collection<Request> result;

		Assert.isTrue(actorService.isAdministrator());

		result = requestRepository.findAllRequestPending();

		return result;
	}

	public void acceptRequest(AddCommentToRequestForm addCommentToRequestForm) {
		Request request;
		Brother brother;
		String comment;
		Administrator administrator;
		Collection<Request> requests;

		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(addCommentToRequestForm);

		request = addCommentToRequestForm.getRequest();
		comment = addCommentToRequestForm.getComment();

		if (comment != null) {
			request.setComments(comment);
		}
		Assert.isTrue(request.getStatus().equals("pending"));

		administrator = administratorService.findByPrincipal();

		request.setStatus("accepted");
		request.setAdministrator(administrator);

		request = save(request);

		brother = request.getBrother();
		brother.setIsAuthorized(true);

		requests = administrator.getRequests();
		requests.add(request);
		administrator.setRequests(requests);

		brotherService.save(brother);
		administratorService.save(administrator);
	}

	public void rejectedRequest(AddCommentToRequestForm addCommentToRequestForm) {
		Request request;
		String comment;
		Administrator administrator;
		Collection<Request> requests;
		String status;

		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(addCommentToRequestForm);

		request = addCommentToRequestForm.getRequest();

		status = "pending";

		Assert.isTrue(request.getStatus().equals(status));

		comment = addCommentToRequestForm.getComment();

		if (comment == null || comment.length() == 0) {
			throw new IllegalArgumentException("need comments");
		}

		request.setComments(comment);

		administrator = administratorService.findByPrincipal();

		request.setStatus("rejected");
		request.setAdministrator(administrator);

		request = save(request);

		requests = administrator.getRequests();
		requests.add(request);
		administrator.setRequests(requests);

		administratorService.save(administrator);
	}

}
