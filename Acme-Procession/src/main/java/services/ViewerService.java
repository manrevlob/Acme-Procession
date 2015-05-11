package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ViewerRepository;
import domain.Viewer;

@Service
@Transactional
public class ViewerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ViewerRepository viewerRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ViewerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Viewer findOne(int viewerId) {
		Viewer result;

		result = viewerRepository.findOne(viewerId);

		return result;
	}

	public Collection<Viewer> findAll() {
		Collection<Viewer> result;

		result = viewerRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
