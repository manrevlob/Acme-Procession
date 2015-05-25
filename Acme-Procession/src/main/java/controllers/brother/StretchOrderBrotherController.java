package controllers.brother;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RegistrationInvoiceService;
import services.RegistrationService;
import services.StretchOrderService;
import controllers.AbstractController;
import domain.Registration;
import domain.RegistrationInvoice;
import domain.StretchOrder;

@Controller
@RequestMapping("/stretchOrder/brother")
public class StretchOrderBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StretchOrderService stretchOrderService;
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private RegistrationInvoiceService registrationInvoiceService;

	// Constructors -----------------------------------------------------------

	public StretchOrderBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam int stretchOrderId) {
		ModelAndView result;
		StretchOrder stretchOrder;
		Registration registration;
		RegistrationInvoice registrationInvoice;
		String error;

		stretchOrder = stretchOrderService.findOne(stretchOrderId);

		registrationInvoice = registrationInvoiceService
				.generateInvoice(stretchOrder);

		registration = registrationService.create(stretchOrder,
				registrationInvoice);
		error = null;

		try {
			registrationService.save(registration);
			result = new ModelAndView("redirect:/registration/brother/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView(
					"redirect:/stretchOrder/list.do?processionId="
							+ stretchOrder.getProcession().getId());
			if (oops.getMessage().equals(
					"registration.otherRegistrationCreated.error")) {
				error = "registration.otherRegistrationCreated.error";
			} else {
				if (oops.getMessage().equals(
						"registration.registerIsClosed.error")) {
					error = "registration.registerIsClosed.error";
				} else {
					error = "stretch.commit.error";
				}
			}
		}

		result.addObject("error", error);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
