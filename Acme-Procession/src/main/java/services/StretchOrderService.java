package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StretchOrderRepository;
import domain.Procession;
import domain.StretchOrder;

@Service
@Transactional
public class StretchOrderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StretchOrderRepository stretchOrderRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherService brotherService;

	@Autowired
	private RegistrationService registrationService;

	// Constructors -----------------------------------------------------------

	public StretchOrderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public StretchOrder findOne(int stretchOrderId) {
		StretchOrder result;

		result = stretchOrderRepository.findOne(stretchOrderId);

		return result;
	}

	public Collection<StretchOrder> findAll() {
		Collection<StretchOrder> result;

		result = stretchOrderRepository.findAll();

		return result;
	}

	public StretchOrder save(StretchOrder stretchOrder) {
		StretchOrder result;

		Assert.notNull(stretchOrder);

		result = stretchOrderRepository.save(stretchOrder);

		return result;
	}

	public void delete(StretchOrder stretchOrder) {
		Assert.notNull(stretchOrder);

		stretchOrderRepository.delete(stretchOrder);
	}
	
	// Other business methods -------------------------------------------------

	public Collection<StretchOrder> findByProcession(Procession procession) {
		Collection<StretchOrder> result;
		
		Assert.notNull(procession);
		
		result = stretchOrderRepository.findByProcession(procession);
		
		return result;
	}

	public void moveToUp(StretchOrder stretchOrderToUp, Collection<StretchOrder> stretchOrders) {
		StretchOrder stretchOrderToDown;
		
		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(stretchOrderToUp.getOrderNumber() > 1);
		// Comprobamos que el hermano tiene permisos.
		Assert.isTrue(stretchOrderToUp.getProcession().getBrotherhood().getBigBrothers().contains(brotherService.findByPrincipal()));
		
		stretchOrderToDown = (StretchOrder) stretchOrders.toArray()[stretchOrderToUp.getOrderNumber() - 2];
		
		stretchOrderToUp.setOrderNumber(stretchOrderToUp.getOrderNumber() - 1);
		stretchOrderToDown.setOrderNumber(stretchOrderToDown.getOrderNumber() + 1);
		
		save(stretchOrderToUp);
		save(stretchOrderToDown);
	}

	public void moveToDown(StretchOrder stretchOrderToDown, Collection<StretchOrder> stretchOrders) {
		StretchOrder stretchOrderToUp;
		
		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(stretchOrderToDown.getOrderNumber() < stretchOrders.size());
		// Comprobamos que el hermano tiene permisos.
		Assert.isTrue(stretchOrderToDown.getProcession().getBrotherhood().getBigBrothers().contains(brotherService.findByPrincipal()));
		
		stretchOrderToUp = (StretchOrder) stretchOrders.toArray()[stretchOrderToDown.getOrderNumber()];
		
		stretchOrderToUp.setOrderNumber(stretchOrderToUp.getOrderNumber() - 1);
		stretchOrderToDown.setOrderNumber(stretchOrderToDown.getOrderNumber() + 1);
		
		save(stretchOrderToUp);
		save(stretchOrderToDown);
	}

	public void deleteAndReorder(StretchOrder stretchOrder, Collection<StretchOrder> stretchOrders) {
		boolean foundAndDeleted;
		
		Assert.isTrue(actorService.isBrother());
		// Comprobamos que aún no haya un registro en la dupla tramo-procesión indicada.
		Assert.isTrue(registrationService.findByProcessionAndStretch(stretchOrder.getProcession(), stretchOrder.getStretch()).size() == 0);
		// Comprobamos que el hermano tiene permisos.
		Assert.isTrue(stretchOrder.getProcession().getBrotherhood().getBigBrothers().contains(brotherService.findByPrincipal()));
		
		foundAndDeleted = false;
		
		for(StretchOrder so : stretchOrders) {
			if(foundAndDeleted) {
				so.setOrderNumber(so.getOrderNumber() - 1);
				save(so);
			} else if(so.equals(stretchOrder)) {
				delete(so);
				foundAndDeleted = true;
			}
		}
	}

}
