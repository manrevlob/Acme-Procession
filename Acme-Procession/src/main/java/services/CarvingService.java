package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CarvingRepository;
import domain.Brotherhood;
import domain.Carving;

@Service
@Transactional
public class CarvingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CarvingRepository carvingRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public CarvingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Carving findOne(int carvingId) {
		Carving result;

		result = carvingRepository.findOne(carvingId);

		return result;
	}

	public Collection<Carving> findAll() {
		Collection<Carving> result;

		result = carvingRepository.findAll();

		return result;
	}

	public Carving create(Brotherhood brotherhood) {
		Carving result;

		Assert.isTrue(actorService.isBrother());

		result = new Carving();
		result.setBrotherhood(brotherhood);

		return result;
	}

	public void save(Carving carving) {
		Assert.notNull(carving);
		Assert.isTrue(actorService.isBrother());

		carvingRepository.save(carving);
	}

	public void delete(Carving carving) {
		Assert.notNull(carving);
		Assert.isTrue(actorService.isBrother());

		carvingRepository.delete(carving);
	}

	// Other business methods -------------------------------------------------

	public Collection<Carving> findByBrotherhood(int brotherhoodId) {
		Collection<Carving> result;

		result = carvingRepository.findByBrotherhood(brotherhoodId);

		return result;
	}

}
