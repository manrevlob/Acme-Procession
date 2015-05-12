package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {
	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Actor findOne(int actorId) {
		Actor result;

		result = actorRepository.findOne(actorId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = actorRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = actorRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;
	}

	private boolean checkRole(String role) {
		boolean result;
		Collection<Authority> authorities;

		result = false;
		authorities = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authorities) {
			result = result || a.getAuthority().equals(role);
		}

		return result;
	}

	public boolean isAdministrator() {
		boolean result;

		result = checkRole(Authority.ADMINISTRATOR);

		return result;
	}

	public boolean isBrother() {
		boolean result;

		result = checkRole(Authority.BROTHER);

		return result;
	}

	public boolean isViewer() {
		boolean result;

		result = checkRole(Authority.VIEWER);

		return result;
	}

	public boolean isCustomer() {
		boolean result;

		result = checkRole(Authority.BROTHER) || checkRole(Authority.VIEWER);

		return result;
	}

}
