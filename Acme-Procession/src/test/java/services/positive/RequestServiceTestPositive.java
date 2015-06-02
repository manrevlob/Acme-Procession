package services.positive;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.RequestService;
import utilities.AbstractTest;
import domain.Request;
import forms.AddCommentToRequestForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RequestServiceTestPositive extends AbstractTest{

	// Services under test --------------------------------------
	
	@Autowired
	private RequestService requestService;
	
	// Tests ----------------------------------------------------
	
	// Probamos a crear una request
	@Test
	public void testCreateAndSaveRequest(){
		Request request;
		Collection<Request> requests;
		Collection<Request> requestsBefore;
		
		authenticate("brother5");
		
		requestsBefore = requestService.findAll();
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		requestService.saveRequest(request);
		
		requests = requestService.findAll();
		
		Assert.isTrue(requestsBefore.size() < requests.size());
		
		authenticate(null);
	}
	
	//Probamos a aceptar la request por parte de un administrator
	@Test
	public void testAcceptRequest(){
		Request request;
		List<Request> requests;
		int id;
		AddCommentToRequestForm addCommentToRequestForm;
		
		authenticate("admin");
		
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		id = request.getId();
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		
		requestService.acceptRequest(addCommentToRequestForm);

		request = requestService.findOne(id);
		
		Assert.isTrue(request.getStatus() == "accepted");
		Assert.isTrue(request.getBrother().getIsAuthorized() == true);
		
		authenticate(null);
	}
	
	//Probamos a rechazar la request por parte de un admin
	@Test
	public void testRejectRequest(){
		Request request;
		List<Request> requests;
		int id;
		AddCommentToRequestForm addCommentToRequestForm;
		
		authenticate("admin");
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		id = request.getId();
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		addCommentToRequestForm.setComment("comments");
		
		requestService.rejectedRequest(addCommentToRequestForm);

		request = requestService.findOne(id);
		
		Assert.isTrue(request.getStatus() == "rejected");
		
		authenticate(null);
	}
}
