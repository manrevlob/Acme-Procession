package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RegistrationRepository;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RegistrationRepository registrationRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public RegistrationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Registration findOne(int registrationId) {
		Registration result;

		result = registrationRepository.findOne(registrationId);

		return result;
	}

	public Collection<Registration> findAll() {
		Collection<Registration> result;

		result = registrationRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
