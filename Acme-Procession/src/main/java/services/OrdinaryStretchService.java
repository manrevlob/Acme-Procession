package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrdinaryStretchRepository;
import domain.OrdinaryStretch;

@Service
@Transactional
public class OrdinaryStretchService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OrdinaryStretchRepository ordinaryStretchRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public OrdinaryStretchService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public OrdinaryStretch findOne(int ordinaryStretchId) {
		OrdinaryStretch result;

		result = ordinaryStretchRepository.findOne(ordinaryStretchId);

		return result;
	}

	public Collection<OrdinaryStretch> findAll() {
		Collection<OrdinaryStretch> result;

		result = ordinaryStretchRepository.findAll();

		return result;
	}

	public OrdinaryStretch create() {
		OrdinaryStretch result;

		// Comprobamos que sea hermano.
		Assert.isTrue(actorService.isBrother());
		// Comprobamos que al menos tenga permisos sobre una hermandad.
		Assert.isTrue(brotherService.findByPrincipal().getOwnBrotherhoods()
				.size() > 0);

		result = new OrdinaryStretch();

		return result;
	}

	public OrdinaryStretch save(OrdinaryStretch ordinaryStretch) {
		OrdinaryStretch result;

		// Comprobamos que no sea nulo el objeto.
		Assert.notNull(ordinaryStretch);
		// Comprobamos que el usuario tiene permisos sobre la hermandad
		// asociada.
		Assert.isTrue(ordinaryStretch.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		// Comprobamos que el tramo no tiene ninguna instancia asociada.
		Assert.isTrue(ordinaryStretch.getStretchOrders() == null
				|| ordinaryStretch.getStretchOrders().size() == 0);

		result = ordinaryStretchRepository.save(ordinaryStretch);

		return result;
	}

	public void delete(OrdinaryStretch ordinaryStretch) {
		// Comprobamos que no sea nulo el objeto.
		Assert.notNull(ordinaryStretch);
		// Comprobamos que el usuario tiene permisos sobre la hermandad
		// asociada.
		Assert.isTrue(ordinaryStretch.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		// Comprobamos que el tramo no tiene ninguna instancia asociada.
		Assert.isTrue(ordinaryStretch.getStretchOrders() == null
				|| ordinaryStretch.getStretchOrders().size() == 0);

		ordinaryStretchRepository.delete(ordinaryStretch);
	}

	// Other business methods -------------------------------------------------

	public OrdinaryStretch findOneIfPrincipal(int ordinaryStretchId) {
		OrdinaryStretch result;

		Assert.isTrue(actorService.isBrother());

		result = findOne(ordinaryStretchId);

		// Comprobamos que el tramo pertenezca a una hermandad sobre la que el
		// usuario tenga permisos.
		Assert.isTrue(result.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));

		return result;
	}

	public Collection<OrdinaryStretch> findMines() {
		Collection<OrdinaryStretch> result;

		Assert.isTrue(actorService.isBrother());

		result = ordinaryStretchRepository.findMines(brotherService
				.findByPrincipal().getOwnBrotherhoods());

		return result;
	}

}
