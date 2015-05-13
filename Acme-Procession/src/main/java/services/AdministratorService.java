package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Administrator;
import domain.MessageFolder;
import forms.RegistrationAdminForm;

@Service
@Transactional
public class AdministratorService {
	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository administratorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageFolderService messageFolderService;

	// Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		Administrator result;

		result = new Administrator();
		result.setUserAccount(createUserAccount());
		result.setMessageFolders(createMessageFolders());
		
		return result;
	}

	public Administrator findOne(int administratorId) {
		Administrator result;

		result = administratorRepository.findOne(administratorId);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = administratorRepository.findAll();

		return result;
	}

	public void save(Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(actorService.isAdministrator());

		if (administrator.getId() == 0) {
			String passwordCoded;

			passwordCoded = PasswordCode.encode(administrator.getUserAccount()
					.getPassword());
			administrator.getUserAccount().setPassword(passwordCoded);
		}

		administratorRepository.save(administrator);
	}

	// Other business methods -------------------------------------------------
	
	public Administrator findByPrincipal() {
		Administrator administrator;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		administrator = administratorRepository.findByPrincipal(userAccount
				.getId());

		Assert.notNull(administrator);

		return administrator;
	}
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		
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
	
	public void registerToTheSystem(Administrator admin) {
		
		admin.setMessageFolders(saveSystemFolders(admin));
		
		save(admin);
		
	}
	
	public Collection<MessageFolder> saveSystemFolders(Administrator admin) {
		List<MessageFolder> result;
		List<MessageFolder> aux;
		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;
		
		aux = (List<MessageFolder>)admin.getMessageFolders();
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
	
	
	public Administrator convertToAdministrator(Administrator administrator,RegistrationAdminForm registrationAdminForm) {
		Assert.notNull(registrationAdminForm);
		Assert.notNull(administrator);

		administrator.setName(registrationAdminForm.getName());
		administrator.setSurname(registrationAdminForm.getSurname());
		administrator.setEmail(registrationAdminForm.getEmail());

		administrator.getUserAccount().setUsername(registrationAdminForm.getUsername());
		
		administrator.getUserAccount().setPassword(registrationAdminForm.getPassword());

		return administrator;
	}
	
	public void checkPassword(RegistrationAdminForm registrationAdminForm) {
		String password;
		String secondPassword;
		
		password = registrationAdminForm.getPassword();
		secondPassword = registrationAdminForm.getSecondPassword();
		
		if(!password.equals(secondPassword)){
			throw new IllegalArgumentException("passwords dont match");
		}
		
	}
	
}
