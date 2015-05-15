package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Administrator;
import domain.Box;

@Service
@Transactional
public class BoxService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxRepository boxRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public BoxService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Box create() {
		Box result;

		Assert.isTrue(actorService.isAdministrator());

		result = new Box();
		
		result.setAdministrator(administratorService.findByPrincipal());

		return result;
	}

	public Box save(Box box) {
		Box result;

		Assert.notNull(box);
		Assert.isTrue(actorService.isAdministrator());

		result = boxRepository.save(box);

		return result;
	}
	
	public Box findOne(int boxId) {
		Box result;

		result = boxRepository.findOne(boxId);

		return result;
	}

	public Collection<Box> findAll() {
		Collection<Box> result;

		result = boxRepository.findAll();

		return result;
	}
	
	// Other business methods -------------------------------------------------
	
	public Collection<Box> findByAdministrator(){
	Collection<Box> result;
	Administrator administrator;
	int actorId;
 	
 	administrator= administratorService.findByPrincipal();
 	actorId = administrator.getId();

	result = boxRepository.findByAdministratorId(actorId);
	
	return result;
}
	
	

}
