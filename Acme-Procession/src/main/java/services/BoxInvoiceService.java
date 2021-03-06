package services;

import java.util.Collection;
import java.util.Date;

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
		Assert.isTrue(actorService.isViewer());
		
		if(boxInvoice.getId()!=0){
			checkIfPrincipal(boxInvoice);
		}
		
		
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
	
	public void delete(BoxInvoice boxInvoice) {
		Assert.notNull(boxInvoice);
		
		Assert.isTrue(actorService.isViewer());
		
		boxInvoiceRepository.delete(boxInvoice);
	}
	
	// Other business methods -------------------------------------------------
	
	public BoxInvoice findOneIfPrincipal(int boxInvoiceId) {
		BoxInvoice result;
		
		Assert.isTrue(actorService.isViewer());
		
		result = findOne(boxInvoiceId);
		
		checkIfPrincipal(result);

		return result;
	}
	
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
	
	public void payInvoice(BoxInvoice boxInvoice) {
		long milliseconds;
		Date date;
		
		Assert.isTrue(actorService.isViewer());
		Assert.isTrue(boxInvoice.getPaidMoment() == null);

		milliseconds = System.currentTimeMillis();
		date = new Date(milliseconds - 10);

		boxInvoice.setPaidMoment(date);

		save(boxInvoice);
	}
	
	public void checkIfPrincipal(BoxInvoice boxInvoice){
		Viewer viewer;
		Collection<BoxInvoice> boxInvoices;
		
		Assert.isTrue(actorService.isViewer());
		
		viewer= viewerService.findByPrincipal();
		
		boxInvoices = boxInvoiceRepository.findByViewerId(viewer.getId());
		
		Assert.isTrue(boxInvoices.contains(boxInvoice));
		
	}

}
