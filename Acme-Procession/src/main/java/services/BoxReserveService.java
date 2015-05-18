package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxReserveRepository;
import domain.Box;
import domain.BoxReserve;
import domain.BoxReserve;
import domain.Viewer;

@Service
@Transactional
public class BoxReserveService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxReserveRepository boxReserveRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	@Autowired
	private ViewerService viewerService;

	// Constructors -----------------------------------------------------------

	public BoxReserveService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public BoxReserve create() {
		BoxReserve result;

		Assert.isTrue(actorService.isViewer());

		result = new BoxReserve();
		
		return result;
	}

	public BoxReserve save(BoxReserve boxReserve) {
		BoxReserve result;
		
		Assert.notNull(boxReserve);
		Assert.isTrue(actorService.isViewer());

		result = boxReserveRepository.save(boxReserve);

		return result;
	}
	
	
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
	
	public Collection<BoxReserve> findByPrincipal(){
		Collection<BoxReserve> result;
		Viewer viewer;
		int actorId;
		
		Assert.isTrue(actorService.isViewer());
	 	
	 	viewer= viewerService.findByPrincipal();
	 	actorId = viewer.getId();

		result = boxReserveRepository.findByViewerId(actorId);
		
		
		return result;
	}

}
