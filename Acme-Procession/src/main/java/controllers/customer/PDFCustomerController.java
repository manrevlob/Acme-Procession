package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BoxInvoiceService;
import services.BoxReserveService;
import services.BrotherService;
import services.CostumeInvoiceService;
import services.CostumeReserveService;
import services.RegistrationInvoiceService;
import services.RegistrationService;
import services.ViewerService;
import controllers.AbstractController;
import domain.BoxInvoice;
import domain.BoxReserve;
import domain.Brother;
import domain.CostumeInvoice;
import domain.CostumeReserve;
import domain.Registration;
import domain.RegistrationInvoice;
import domain.Viewer;

@Controller
@RequestMapping("/pdf/customer")
public class PDFCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherService brotherService;
	@Autowired
	private ViewerService viewerService;
	@Autowired
	private CostumeInvoiceService costumeInvoiceService;
	@Autowired
	private CostumeReserveService costumeReserveService;
	@Autowired
	private RegistrationInvoiceService registrationInvoiceService;
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private BoxReserveService boxReserveService;
	@Autowired
	private BoxInvoiceService boxInvoiceService;

	// Constructors -----------------------------------------------------------

	public PDFCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@RequestParam int invoiceId) {
		ModelAndView result = null;
		CostumeInvoice costumeInvoice;
		Brother brother = null;
		CostumeReserve costumeReserve;
		Registration registration;
		RegistrationInvoice registrationInvoice;
		BoxReserve boxReserve;
		BoxInvoice boxInvoice;
		Viewer viewer;

		costumeInvoice = costumeInvoiceService.findOne(invoiceId);
		registrationInvoice = registrationInvoiceService.findOne(invoiceId);
		boxInvoice = boxInvoiceService.findOne(invoiceId);

		if (costumeInvoice != null) {
			brother = brotherService.findByCostumeInvoice(invoiceId);
			costumeInvoiceService.checkIfPrincipal(costumeInvoice);
			costumeReserve = costumeReserveService
					.findByCostumeInvoice(invoiceId);
			result = new ModelAndView("pdfView", "costumeInvoice",
					costumeInvoice).addObject("brother", brother).addObject(
					"costumeReserve", costumeReserve);
		} else if (registrationInvoice != null) {
			brother = brotherService.findByRegistrationInvoice(invoiceId);
			registrationInvoiceService.checkIfPrincipal(registrationInvoice);
			registration = registrationService
					.findByRegistrationInvoice(invoiceId);
			result = new ModelAndView("pdfView", "registrationInvoice",
					registrationInvoice).addObject("brother", brother)
					.addObject("registration", registration);
		} else if (boxInvoice != null) {
			viewer = viewerService.findByBoxInvoice(invoiceId);
			boxInvoiceService.checkIfPrincipal(boxInvoice);
			boxReserve = boxReserveService.findByBoxInvoice(invoiceId);
			result = new ModelAndView("pdfView", "boxInvoice", boxInvoice)
					.addObject("viewer", viewer).addObject("boxReserve",
							boxReserve);
		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
