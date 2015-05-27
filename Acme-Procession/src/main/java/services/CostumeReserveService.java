package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CostumeReserveRepository;
import domain.Costume;
import domain.CostumeReserve;

@Service
@Transactional
public class CostumeReserveService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CostumeReserveRepository costumeReserveRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public CostumeReserveService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public CostumeReserve findOne(int costumeReserveId) {
		CostumeReserve result;

		result = costumeReserveRepository.findOne(costumeReserveId);

		return result;
	}

	public Collection<CostumeReserve> findAll() {
		Collection<CostumeReserve> result;

		result = costumeReserveRepository.findAll();

		return result;
	}

	public CostumeReserve create(Costume costume) {
		CostumeReserve result;

		Assert.isTrue(actorService.isBrother());

		result = new CostumeReserve();

		result.setBrother(brotherService.findByPrincipal());
		result.setCostume(costume);

		return result;
	}

	public CostumeReserve save(CostumeReserve costumeReserve) {
		CostumeReserve result;

		Assert.notNull(costumeReserve);

		result = costumeReserveRepository.save(costumeReserve);

		return result;
	}

	// Other business methods -------------------------------------------------

	public CostumeReserve findOneIfPrincipal(int costumeReserveId) {
		CostumeReserve result;

		result = findOne(costumeReserveId);

		Assert.isTrue(result.getBrother().equals(
				brotherService.findByPrincipal()));

		return result;
	}

	public Collection<CostumeReserve> findByPrincipal() {
		Collection<CostumeReserve> result;

		Assert.isTrue(actorService.isBrother());

		result = costumeReserveRepository.findByPrincipal(brotherService
				.findByPrincipal());

		return result;
	}

}
