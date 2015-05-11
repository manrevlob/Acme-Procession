package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AssessmentRepository;
import domain.Assessment;

@Service
@Transactional
public class AssessmentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AssessmentRepository assessmentRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public AssessmentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Assessment findOne(int assessmentId) {
		Assessment result;

		result = assessmentRepository.findOne(assessmentId);

		return result;
	}

	public Collection<Assessment> findAll() {
		Collection<Assessment> result;

		result = assessmentRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
