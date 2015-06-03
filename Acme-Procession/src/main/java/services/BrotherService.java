package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Brother;
import domain.MessageFolder;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BrotherRepository brotherRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public BrotherService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Brother create() {
		Brother result;

		result = new Brother();

		result.setUserAccount(createUserAccount());
		result.setMessageFolders(createMessageFolders());

		return result;
	}

	public void save(Brother brother) {

		Assert.notNull(brother);

		if (brother.getId() == 0) {
			String passwordCoded;

			passwordCoded = PasswordCode.encode(brother.getUserAccount()
					.getPassword());
			brother.getUserAccount().setPassword(passwordCoded);
			brother.setIsAuthorized(false);
		}

		brotherRepository.save(brother);
	}

	public Brother findOne(int brotherId) {
		Brother result;

		result = brotherRepository.findOne(brotherId);

		return result;
	}

	public Collection<Brother> findAll() {
		Collection<Brother> result;

		result = brotherRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Brother findByPrincipal() {
		Brother result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = brotherRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;
	}

	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;

		authority = new Authority();
		authority.setAuthority("BROTHER");

		authorities = new ArrayList<Authority>();
		authorities.add(authority);

		result = new UserAccount();
		result.setAuthorities(authorities);

		return result;
	}

	public Collection<MessageFolder> createMessageFolders() {
		Collection<MessageFolder> result;
		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;

		inbox = new MessageFolder();
		outbox = new MessageFolder();
		trashbox = new MessageFolder();

		result = new ArrayList<MessageFolder>();
		result.add(inbox);
		result.add(outbox);
		result.add(trashbox);

		return result;
	}

	public void registerToTheSystem(Brother brother) {
		Integer year;
		Integer month;

		year = Calendar.getInstance().get(Calendar.YEAR);
		month = Calendar.getInstance().get(Calendar.MONTH);

		Assert.isTrue(brother.getCreditCard().getExpirationYear() >= year,"credit card expired");

		if (brother.getCreditCard().getExpirationYear().equals(year)) {
			Assert.isTrue(brother.getCreditCard().getExpirationMonth() > month,"credit card expired");
		}

		brother.setMessageFolders(saveSystemFolders(brother));

		save(brother);
	}

	public Collection<MessageFolder> saveSystemFolders(Brother brother) {
		List<MessageFolder> result;
		List<MessageFolder> aux;
		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;

		aux = (List<MessageFolder>) brother.getMessageFolders();
		inbox = aux.get(0);
		outbox = aux.get(1);
		trashbox = aux.get(2);

		inbox.setName("Inbox");
		outbox.setName("Outbox");
		trashbox.setName("Trashbox");

		inbox = messageFolderService.save(inbox);
		outbox = messageFolderService.save(outbox);
		trashbox = messageFolderService.save(trashbox);

		result = new ArrayList<MessageFolder>();
		result.add(inbox);
		result.add(outbox);
		result.add(trashbox);

		return result;
	}

	public Collection<Brother> findAllBrothersNotAdded(Brotherhood brotherhood) {
		Collection<Brother> result;

		Assert.notNull(brotherhood);

		result = brotherRepository.findAllBrothersNotAdded(brotherhood.getId());

		return result;
	}

	public Brother registerToBrotherhood(Brotherhood brotherhood) {
		Brother result;
		Collection<Brotherhood> brotherhoods;

		Assert.notNull(brotherhood);
		Assert.isTrue(actorService.isBrother());
		// Comprobamos si el hermano ya esta registrado en esa misma hermandad
		Assert.isTrue(
				findByBrotherhoodAndBrother(brotherhood.getId(),
						findByPrincipal()).size() == 0,
				"brotherhood.otherRegistrationCreated.error");

		result = findByPrincipal();

		brotherhoods = result.getBrotherhoods();
		brotherhoods.add(brotherhood);

		result.setBrotherhoods(brotherhoods);

		save(result);

		return result;
	}

	private Collection<Brother> findByBrotherhoodAndBrother(int brotherhoodId,
			Brother brother) {
		Collection<Brother> result;

		result = brotherRepository.findByBrotherhoodAndBrother(brotherhoodId,
				brother.getId());

		return result;
	}

	public Brother findByCostumeInvoice(int costumeInvoiceId) {
		Brother result;

		result = brotherRepository.findByCostumeInvoice(costumeInvoiceId);

		return result;
	}

	public Brother findByRegistrationInvoice(int registrationInvoiceId) {
		Brother result;

		result = brotherRepository.findByRegistrationInvoice(registrationInvoiceId);

		return result;
	}

	// Dashboard
	public Collection<Brother> findByBrotherhoodAndBrother() {
		Collection<Brother> result;

		Assert.isTrue(actorService.isAdministrator());

		result = brotherRepository.findAllOrderByNumReg();

		return result;
	}

	public Collection<Brother> findByNumBrotherhood() {
		Collection<Brother> result;

		Assert.isTrue(actorService.isAdministrator());

		result = brotherRepository.findByNumBrotherhood();

		return result;
	}

	public Collection<Brother> findWithAutoAndCostumePay() {
		Collection<Brother> result;

		Assert.isTrue(actorService.isAdministrator());

		result = brotherRepository.findWithAutoAndCostumePay();

		return result;
	}

	public Collection<Object[]> findAllTotalCostOfRegistration() {
		Collection<Object[]> result;

		Assert.isTrue(actorService.isAdministrator());

		result = brotherRepository.findAllTotalCostOfRegistration();

		return result;
	}

	public Collection<Object[]> findAllTotalCostOfCostume() {
		Collection<Object[]> result;

		Assert.isTrue(actorService.isAdministrator());

		result = brotherRepository.findAllTotalCostOfCostume();

		return result;
	}

}
