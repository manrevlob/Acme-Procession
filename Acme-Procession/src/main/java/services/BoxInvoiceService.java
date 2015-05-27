package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxInvoiceRepository;
import domain.BoxInvoice;
import domain.Viewer;

@Service
@Transactional
public class BoxInvoiceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxInvoiceRepository boxInvoiceRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private ViewerService viewerService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BoxInvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public BoxInvoice save(BoxInvoice boxInvoice) {
		BoxInvoice result;
		
		Assert.notNull(boxInvoice);	
		
		result = boxInvoiceRepository.save(boxInvoice);
		
		return result;
	}
	
	public BoxInvoice findOne(int boxInvoiceId) {
		BoxInvoice result;

		result = boxInvoiceRepository.findOne(boxInvoiceId);

		return result;
	}

	public Collection<BoxInvoice> findAll() {
		Collection<BoxInvoice> result;

		result = boxInvoiceRepository.findAll();

		return result;
	}
	
	// Other business methods -------------------------------------------------
	
	public Collection<BoxInvoice> findByPrincipal(){
		Collection<BoxInvoice> result;
		Viewer viewer;
		int actorId;
		
		Assert.isTrue(actorService.isViewer());
	 	
	 	viewer= viewerService.findByPrincipal();
	 	actorId = viewer.getId();

		result = boxInvoiceRepository.findByViewerId(actorId);
		
		
		return result;
	}

}
