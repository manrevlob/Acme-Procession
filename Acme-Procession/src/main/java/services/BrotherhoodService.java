package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BrotherhoodRepository;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BrotherhoodRepository brotherhoodRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BrotherhoodService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Brotherhood findOne(int brotherhoodId) {
		Brotherhood result;

		result = brotherhoodRepository.findOne(brotherhoodId);

		return result;
	}

	public Collection<Brotherhood> findAll() {
		Collection<Brotherhood> result;

		result = brotherhoodRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
