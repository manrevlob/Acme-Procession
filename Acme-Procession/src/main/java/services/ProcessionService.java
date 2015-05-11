package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProcessionRepository;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProcessionRepository processionRepository;

	// Supporting services ----------------------------------------------------

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
	// Other business methods -------------------------------------------------

}
