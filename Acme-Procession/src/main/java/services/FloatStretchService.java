package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatStretchRepository;
import domain.Brotherhood;
import domain.FloatStretch;

@Service
@Transactional
public class FloatStretchService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FloatStretchRepository floatStretchRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public FloatStretchService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FloatStretch findOne(int floatStretchId) {
		FloatStretch result;

		result = floatStretchRepository.findOne(floatStretchId);

		return result;
	}

	public Collection<FloatStretch> findAll() {
		Collection<FloatStretch> result;

		result = floatStretchRepository.findAll();

		return result;
	}

	public FloatStretch create(Brotherhood brotherhood) {
		FloatStretch result;

		// Comprobamos que sea hermano.
		Assert.isTrue(actorService.isBrother());
		// Comprobamos que el usuario tenga permisos sobre la hermandad.
		Assert.isTrue(brotherhood.getBigBrothers().contains(brotherService.findByPrincipal()));

		result = new FloatStretch();
		result.setBrotherhood(brotherhood);

		return result;
	}

	public FloatStretch save(FloatStretch floatStretch) {
		FloatStretch result;

		// Comprobamos que no sea nulo el objeto.
		Assert.notNull(floatStretch);
		// Comprobamos que el usuario tiene permisos sobre la hermandad
		// asociada.
		Assert.isTrue(floatStretch.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		// Comprobamos que el tramo no tiene ninguna instancia asociada.
		Assert.isTrue(floatStretch.getStretchOrders() == null
				|| floatStretch.getStretchOrders().size() == 0);

		result = floatStretchRepository.save(floatStretch);

		return result;
	}

	public void delete(FloatStretch floatStretch) {
		// Comprobamos que no sea nulo el objeto.
		Assert.notNull(floatStretch);
		// Comprobamos que el usuario tiene permisos sobre la hermandad
		// asociada.
		Assert.isTrue(floatStretch.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		// Comprobamos que el tramo no tiene ninguna instancia asociada.
		Assert.isTrue(floatStretch.getStretchOrders() == null
				|| floatStretch.getStretchOrders().size() == 0);

		floatStretchRepository.delete(floatStretch);
	}

	// Other business methods -------------------------------------------------

	public FloatStretch findOneIfPrincipal(int floatStretchId) {
		FloatStretch result;

		Assert.isTrue(actorService.isBrother());

		result = findOne(floatStretchId);

		// Comprobamos que el tramo pertenezca a una hermandad sobre la que el
		// usuario tenga permisos.
		Assert.isTrue(result.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));

		return result;
	}

	public Collection<FloatStretch> findMines() {
		Collection<FloatStretch> result;

		Assert.isTrue(actorService.isBrother());

		result = floatStretchRepository.findMines(brotherService
				.findByPrincipal().getOwnBrotherhoods());

		return result;
	}

	public Collection<FloatStretch> findByBrotherhood(Brotherhood brotherhood) {
		Collection<FloatStretch> result;

		Assert.notNull(brotherhood);

		result = floatStretchRepository.findByBrotherhood(brotherhood);

		return result;
	}

}
