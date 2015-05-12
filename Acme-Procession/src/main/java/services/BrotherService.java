package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brother;

@Service
@Transactional
public class BrotherService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BrotherRepository brotherRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BrotherService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

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

}
