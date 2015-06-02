package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CarvingRepository;
import domain.Brotherhood;
import domain.Carving;
import domain.Procession;
import domain.Stretch;

@Service
@Transactional
public class CarvingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CarvingRepository carvingRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private BrotherService brotherService;

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

	public Carving save(Carving carving) {
		Carving result;

		Assert.notNull(carving);
		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(carving.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));

		result = carvingRepository.save(carving);

		return result;
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

	public Collection<Carving> findByProcession(Procession procession) {
		Collection<Carving> result;

		Assert.notNull(procession);

		result = carvingRepository.findByProcession(procession);

		return result;
	}

	public Collection<Carving> findByStretch(Stretch stretch) {
		Collection<Carving> result;

		Assert.notNull(stretch);

		result = carvingRepository.findByStretch(stretch);

		return result;
	}

}
