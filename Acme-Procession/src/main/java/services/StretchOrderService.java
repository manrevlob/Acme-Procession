package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.StretchOrderRepository;
import domain.StretchOrder;

@Service
@Transactional
public class StretchOrderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StretchOrderRepository stretchOrderRepository;

	// Supporting services ----------------------------------------------------

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
	
	// Other business methods -------------------------------------------------

}
