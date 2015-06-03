package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StretchRepository;
import domain.Procession;
import domain.Stretch;

@Service
@Transactional
public class StretchService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StretchRepository stretchRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public StretchService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Stretch findOne(int stretchId) {
		Stretch result;

		result = stretchRepository.findOne(stretchId);

		return result;
	}

	public Collection<Stretch> findAll() {
		Collection<Stretch> result;

		result = stretchRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<Stretch> findAvailables(Procession procession) {
		Collection<Stretch> result;

		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(procession.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));

		result = stretchRepository.findAvailableStretches(
				procession.getBrotherhood(), procession);

		return result;
	}

}
