package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import domain.Brotherhood;
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

	public Procession create() {
		Procession result;
		
		result = new Procession();
		
		return result;
	}

	public Procession save(Procession procession) {
		Assert.notNull(procession);
		
		procession = processionRepository.save(procession);
		
		return procession;
	}

	public void delete(Procession procession) {
		Assert.notNull(procession);
		// Comprobamos que el usuario es hermano
		Assert.isTrue(actorService.isBrother());
		// Comprobamos que el usuario tiene permisos
		Assert.isTrue(procession.getBrotherhood().getBigBrothers().contains(brotherService.findByPrincipal()));
		// Comprobamos que aún nadie está registrado en la procesión
		Assert.isTrue(procession.getRegistrations().size() == 0);
		
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
		
		Assert.isTrue(result.getBrotherhood().getBigBrothers().contains(brotherService.findByPrincipal()));
		
		return result;
	}

	public void addStretch(AddStretchToProcessionForm addStretchToProcessionForm) {
		Procession procession;
		Stretch stretch;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOders;

		procession = addStretchToProcessionForm.getProcession();
		stretch = addStretchToProcessionForm.getStretch();

		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(stretch.getBrotherhood().getBigBrothers().contains(brotherService.findByPrincipal()));
		Assert.isTrue(stretch.getBrotherhood().equals(procession.getBrotherhood()));

		stretchOrder = stretchOrderService.createByStretchAndProcession(stretch, procession);

		stretchOders = procession.getStretchOrders();
		stretchOders.add(stretchOrder);
		procession.setStretchOrders(stretchOders);

		save(procession);
	}

}
