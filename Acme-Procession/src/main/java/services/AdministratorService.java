package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Administrator;

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
}
