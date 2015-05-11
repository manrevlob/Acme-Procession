package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxReserveRepository;
import domain.BoxReserve;

@Service
@Transactional
public class BoxReserveService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxReserveRepository boxReserveRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BoxReserveService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public BoxReserve findOne(int boxReserveId) {
		BoxReserve result;

		result = boxReserveRepository.findOne(boxReserveId);

		return result;
	}

	public Collection<BoxReserve> findAll() {
		Collection<BoxReserve> result;

		result = boxReserveRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
