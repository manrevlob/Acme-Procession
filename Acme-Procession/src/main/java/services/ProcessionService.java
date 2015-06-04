package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import domain.Brotherhood;
import domain.Carving;
import domain.FloatStretch;
import domain.Procession;
import domain.Stretch;
import domain.StretchOrder;
import forms.AddStretchToProcessionForm;

@Service
@Transactional
public class ProcessionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProcessionRepository processionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherService brotherService;

	@Autowired
	private StretchOrderService stretchOrderService;

	@Autowired
	private CarvingService carvingService;

	// Constructors -----------------------------------------------------------

	public ProcessionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Procession findOne(int processionId) {
		Procession result;

		result = processionRepository.findOne(processionId);

		return result;
	}

	public Collection<Procession> findAll() {
		Collection<Procession> result;

		result = processionRepository.findAll();

		return result;
	}

	public Procession create(Brotherhood brotherhood) {
		Procession result;

		result = new Procession();
		result.setBrotherhood(brotherhood);

		return result;
	}

	public Procession save(Procession procession) {
		Assert.notNull(procession);
		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(procession.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		Assert.isTrue(
				procession.getStartMoment().before(procession.getEndMoment()),
				"procession.date.error");

		procession = processionRepository.save(procession);

		return procession;
	}

	public void delete(Procession procession) {
		Assert.notNull(procession);
		// Comprobamos que el usuario es hermano
		Assert.isTrue(actorService.isBrother());
		// Comprobamos que el usuario tiene permisos
		Assert.isTrue(procession.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		// Comprobamos que aún nadie está registrado en la procesión
		Assert.isTrue(procession.getRegistrations() == null
				|| procession.getRegistrations().size() == 0);

		processionRepository.delete(procession);
	}

	// Other business methods -------------------------------------------------

	public Collection<Procession> findByBrotherhood(int brotherhoodId) {
		Collection<Procession> result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOne(brotherhoodId);

		Assert.notNull(brotherhood);

		result = processionRepository.findByBrotherhood(brotherhood);

		return result;
	}

	public Procession findOneIfPrincipal(int processionId) {
		Procession result;

		result = findOne(processionId);

		Assert.isTrue(result.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));

		return result;
	}

	public void addStretch(AddStretchToProcessionForm addStretchToProcessionForm) {
		Procession procession;
		Stretch stretch;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOders;
		Collection<Carving> carvingsInTheProcession;
		Collection<Carving> carvingsOfTheStretch;

		procession = addStretchToProcessionForm.getProcession();
		stretch = addStretchToProcessionForm.getStretch();

		Assert.isTrue(actorService.isBrother());
		// Comprobamos que la hermandad del paso seleccionado es la misma que la
		// hermandad de la procesión
		Assert.isTrue(procession.getBrotherhood().equals(
				stretch.getBrotherhood()));
		// Comprobamos que el usuario tenga permisos sobre la hermandad
		Assert.isTrue(stretch.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		// Comprobamos que, si es un tramo de paso, sus carvings no estén ya en
		// otro paso.
		if (addStretchToProcessionForm.getStretch().getClass()
				.equals(FloatStretch.class)) {
			carvingsInTheProcession = carvingService
					.findByProcession(procession);
			carvingsOfTheStretch = carvingService.findByStretch(stretch);

			for (Carving c : carvingsOfTheStretch) {
				Assert.isTrue(!carvingsInTheProcession.contains(c),
						"procession.duplicatedCarving.error");
			}
		}

		stretchOrder = stretchOrderService.createByStretchAndProcession(
				stretch, procession);

		stretchOrder = stretchOrderService.save(stretchOrder);

		stretchOders = procession.getStretchOrders();
		stretchOders.add(stretchOrder);
		procession.setStretchOrders(stretchOders);

		save(procession);
	}

	public Collection<Procession> findAllAvailables() {
		Collection<Procession> result;
		Assert.isTrue(actorService.isBrother());

		result = processionRepository.findAllAvailables();

		return result;
	}

	public void close(Procession procession) {
		Assert.notNull(procession);
		Assert.isTrue(!procession.getIsClosedByTime());
		Assert.isTrue(!procession.getIsClosedManually());
		Assert.isTrue(procession.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));

		procession.setIsClosedManually(true);

		save(procession);
	}

	public void open(Procession procession) {
		Assert.notNull(procession);
		Assert.isTrue(!procession.getIsClosedByTime());
		Assert.isTrue(procession.getIsClosedManually());
		Assert.isTrue(procession.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));

		procession.setIsClosedManually(false);

		save(procession);
	}
	
	public Collection<Procession> findAllPastDate(){
		Date date;
		Collection<Procession> result;
		long milliseconds;
		
		Assert.isTrue(actorService.isViewer());
		
		milliseconds = System.currentTimeMillis() - 100;
		date = new Date(milliseconds);
		
		result = processionRepository.findAllPastDate(date);
		
		return result;
	}

	// Dashboard
	public Collection<Procession> findAllOrderByNumReg() {
		Collection<Procession> result;

		Assert.isTrue(actorService.isAdministrator());

		result = processionRepository.findAllOrderByNumReg();

		return result;
	}

}
