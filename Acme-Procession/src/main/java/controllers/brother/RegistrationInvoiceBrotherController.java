package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RegistrationInvoiceService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Registration;
import domain.RegistrationInvoice;

@Controller
@RequestMapping("/registrationInvoice/brother")
public class RegistrationInvoiceBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RegistrationInvoiceService registrationInvoiceService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RegistrationService registrationService;

	// Constructors -----------------------------------------------------------

	public RegistrationInvoiceBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<RegistrationInvoice> registrationInvoices;
		String uri;

		registrationInvoices = registrationInvoiceService.findAllByBrother();

		uri = "registrationInvoice/brother/list.do";

		result = new ModelAndView("invoice/list");
		result.addObject("registrationInvoices", registrationInvoices);
		result.addObject("requestURI", uri);

		return result;
	}

	// details ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int registrationInvoiceId) {
		ModelAndView result;
		RegistrationInvoice registrationInvoice;
		Registration registration;

		registration = registrationService
				.findByRegistrationInvoice(registrationInvoiceId);
		registrationInvoice = registrationInvoiceService
				.findOne(registrationInvoiceId);

		result = new ModelAndView("invoice/details");
		result.addObject("registrationInvoice", registrationInvoice);
		result.addObject("registration", registration);

		return result;
	}

	// Pay -------------------------------------------------------------------

	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam int registrationInvoiceId) {
		ModelAndView result;
		RegistrationInvoice registrationInvoice;

		registrationInvoice = registrationInvoiceService
				.findOne(registrationInvoiceId);

		Assert.isTrue(registrationInvoice.getPaidMoment() == null);

		registrationInvoiceService.paidInvoice(registrationInvoice);

		result = new ModelAndView("redirect:/registrationInvoice/brother/list.do");

		return result;
	}

}
