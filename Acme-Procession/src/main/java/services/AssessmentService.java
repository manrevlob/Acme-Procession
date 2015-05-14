package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AssessmentRepository;
import domain.Assessment;
import domain.Viewer;

@Service
@Transactional
public class AssessmentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AssessmentRepository assessmentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private ViewerService viewerService;
	
	// Constructors -----------------------------------------------------------

	public AssessmentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Assessment create() {
		Assessment assessment;
		Viewer viewer;
		
		assessment = new Assessment();
		viewer = viewerService.findByPrincipal();
		assessment.setViewer(viewer);
		
		return assessment;
	}
	
	public void save(Assessment assessment){
		Assert.notNull(assessment);
		Assert.isTrue(actorService.isViewer());
		
		assessmentRepository.save(assessment);
	}
	
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

	public Collection<Assessment> findAllByViewer() {
		Collection<Assessment> results;
		int viewerId;
		
		Assert.isTrue(actorService.isViewer());
		
		viewerId = viewerService.findByPrincipal().getId();
		
		results = assessmentRepository.findAllByViewer(viewerId);
		
		return results;
	}

}
