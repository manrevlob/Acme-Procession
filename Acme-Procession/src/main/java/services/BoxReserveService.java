package services;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxReserveRepository;
import domain.BoxInvoice;
import domain.BoxReserve;
import domain.Money;
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
	@Autowired
	private BoxInvoiceService boxInvoiceService;

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
		BoxInvoice boxInvoice;
		
		Assert.notNull(boxReserve);
		Assert.isTrue(actorService.isViewer());
		
		if(boxReserve.getId()==0){
			boxInvoice = generateBoxInvoice(boxReserve);
			boxReserve.setBoxInvoice(boxInvoice);
		}

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
	
	public void cancel(BoxReserve boxReserve) {
		
		Assert.isTrue(actorService.isViewer());
		Assert.isTrue(boxReserve.getViewer() == viewerService.findByPrincipal());
		
		Assert.isTrue(canBeCancelled(boxReserve),"cant cancelled");
		
		boxReserve.setIsCancelled(true);
		
		save(boxReserve);
	}
	
	public boolean canBeCancelled(BoxReserve boxReserve) {
		boolean result;
		Calendar todayDate;
		Calendar maxCancellableDate;
		
		todayDate = Calendar.getInstance();
		maxCancellableDate = Calendar.getInstance();
		
		maxCancellableDate.setTime(boxReserve.getDate());
		maxCancellableDate.add(Calendar.HOUR, -24);
		
		result = todayDate.getTimeInMillis() < maxCancellableDate.getTimeInMillis();
		
		return result;
	}
	
	public BoxInvoice generateBoxInvoice(BoxReserve boxReserve){
		BoxInvoice result;
		Date moment;
		long milliseconds;
		Money totalCost;
		BoxInvoice boxInvoice;
		
		milliseconds = System.currentTimeMillis() - 100;
		moment = new Date(milliseconds);
		
		boxInvoice = new BoxInvoice();
		boxInvoice.setCreateMoment(moment);
		
		totalCost = new Money();
		totalCost.setAmount(boxReserve.getBoxInstance().getPrice().getAmount());
		totalCost.setCurrency(boxReserve.getBoxInstance().getPrice().getCurrency());
		
		boxInvoice.setTotalCost(totalCost);
		
		result = boxInvoiceService.save(boxInvoice);
		
		return result;
	}
}
