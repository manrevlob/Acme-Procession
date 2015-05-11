package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CostumeReserveRepository;
import domain.CostumeReserve;

@Service
@Transactional
public class CostumeReserveService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CostumeReserveRepository costumeReserveRepository;

	// Supporting services ----------------------------------------------------

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
	// Other business methods -------------------------------------------------

}
