package services;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxReserveRepository;
import utilities.CodeGenerator;
import domain.Box;
import domain.BoxInstance;
import domain.BoxInvoice;
import domain.BoxReserve;
import domain.Money;
import domain.Viewer;
import forms.CreateBoxReserveForm;

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
	@Autowired
	private BoxService boxService;
	@Autowired
	private BoxInstanceService boxInstanceService;

	// Constructors -----------------------------------------------------------

	public BoxReserveService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public BoxReserve create(CreateBoxReserveForm createBoxReserveForm) {
		BoxReserve result;
		String code;
		Date creationMoment;
		long milliseconds;
		Viewer viewer;
		Integer chairs;
		Money cost;
		double amount;
		String currency;

		Assert.isTrue(actorService.isViewer());
		Assert.isTrue(createBoxReserveForm.getChairs()<=createBoxReserveForm.getBoxInstance().getAvailableChairs(),"cant reserve");
		
		code = CodeGenerator.generate();
		milliseconds = System.currentTimeMillis() - 100;
		creationMoment = new Date(milliseconds);
		viewer = viewerService.findByPrincipal();
		chairs = createBoxReserveForm.getChairs();

		result = new BoxReserve();
		result.setBoxInstance(createBoxReserveForm.getBoxInstance());
		result.setDate(createBoxReserveForm.getBoxInstance().getDate());
		result.setReserveCode(code);
		result.setCreateMoment(creationMoment);
		result.setNumbersOfchairs(chairs);
		result.setViewer(viewer);
		
		cost = new Money();
		amount = createBoxReserveForm.getBox().getPrice().getAmount();
		currency = createBoxReserveForm.getBox().getPrice().getCurrency();
		amount= amount * chairs;
		cost.setAmount(amount);
		cost.setCurrency(currency);
		result.setTotalCost(cost);
		
		return result;
	}

	public BoxReserve save(BoxReserve boxReserve) {
		BoxReserve result;
		BoxInvoice boxInvoice;
		BoxInstance boxInstance;
		int chairs;
		
		Assert.notNull(boxReserve);
		Assert.isTrue(actorService.isViewer());
		
		if(boxReserve.getId()==0){
			boxInvoice = generateBoxInvoice(boxReserve);
			boxReserve.setBoxInvoice(boxInvoice);
			
			chairs = boxReserve.getBoxInstance().getAvailableChairs();
			chairs = chairs - boxReserve.getNumbersOfchairs();
			
			boxInstance = boxReserve.getBoxInstance();
			boxInstance.setAvailableChairs(chairs);
			boxInstanceService.save(boxInstance);
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
		BoxInstance boxInstance;
		int chairs;
		int newCHairs;
		BoxInvoice boxInvoice;
		
		Assert.isTrue(actorService.isViewer());
		Assert.isTrue(boxReserve.getViewer() == viewerService.findByPrincipal());
		Assert.isTrue(boxReserve.getIsCancelled()==false);
		
		Assert.isTrue(canBeCancelled(boxReserve),"cant cancelled");
		
		boxInstance = boxReserve.getBoxInstance();
		chairs = boxReserve.getNumbersOfchairs();
		newCHairs = boxInstance.getAvailableChairs() + chairs;
		boxInstance.setAvailableChairs(newCHairs);
		
		boxInvoice = boxReserve.getBoxInvoice();
		boxInvoiceService.delete(boxInvoice);
				
		boxInstanceService.save(boxInstance);
		
		boxReserve.setIsCancelled(true);
		boxReserve.setBoxInvoice(null);
		
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
		BoxInvoice boxInvoice;
		Money cost;
		
		milliseconds = System.currentTimeMillis() - 100;
		moment = new Date(milliseconds);
		cost = boxReserve.getTotalCost();
		
		boxInvoice = new BoxInvoice();
		boxInvoice.setCreateMoment(moment);
		boxInvoice.setTotalCost(cost);
		
		result = boxInvoiceService.save(boxInvoice);
		
		return result;
	}
	
	public CreateBoxReserveForm createBoxReserveForm(int boxId){
		CreateBoxReserveForm result;
		Box box;
		
		box = boxService.findOne(boxId);
		
		result = new CreateBoxReserveForm();
		result.setBox(box);
		
		return result;
	}
	
	public BoxReserve findOneByPrincipal(int boxReserveId){
		BoxReserve result;
		
		result = findOne(boxReserveId);
		
		Assert.isTrue(result.getViewer() == viewerService.findByPrincipal());
		Assert.isTrue(actorService.isViewer());	
		
		return result;
	}
	
	public BoxReserve findByBoxInvoice(int boxInvoiceId){
		BoxReserve	result;
		
		result = boxReserveRepository.findByBoxInvoice(boxInvoiceId);
		
		return result;
	}
}
