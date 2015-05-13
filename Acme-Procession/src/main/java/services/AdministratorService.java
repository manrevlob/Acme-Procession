package services;

import java.util.ArrayList;
import java.util.Collection;

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

	// Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		Administrator result;

		result = new Administrator();
		
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
