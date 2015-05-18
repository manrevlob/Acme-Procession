package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import services.RegistrationService;
import services.StretchOrderService;
import controllers.AbstractController;
import domain.Procession;
import domain.Registration;
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
	private ProcessionService processionService;

	// Constructors -----------------------------------------------------------

	public StretchOrderBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Register ---------------------------------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int stretchOrderId) {
		ModelAndView result;
		StretchOrder stretchOrder;
		Registration registration;
		String error;

		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		registration = registrationService.create(stretchOrder);
		error = null;

		try{
			registrationService.save(registration);
		}catch(Throwable oops){
			if (oops.getMessage().equals("registration.otherRegistrastionCreated.error")) {
				error = "registration.otherRegistrastionCreated.error";
			}
		}
		
		result = new ModelAndView(
				"redirect:/stretchOrder/list.do?processionId="
						+ stretchOrder.getProcession().getId());
		result.addObject("error", error);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/moveToUp", method = RequestMethod.GET)
	public ModelAndView moveToUp(@RequestParam int stretchOrderId,
			@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;

		procession = processionService.findOne(processionId);
		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(procession);
		stretchOrderService.moveToUp(stretchOrder, stretchOrders);

		result = new ModelAndView(
				"redirect:/stretchOrder/list.do?processionId=" + processionId);

		return result;
	}

	@RequestMapping(value = "/moveToDown", method = RequestMethod.GET)
	public ModelAndView moveToDown(@RequestParam int stretchOrderId,
			@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;

		procession = processionService.findOne(processionId);
		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(procession);
		stretchOrderService.moveToDown(stretchOrder, stretchOrders);

		result = new ModelAndView(
				"redirect:/stretchOrder/list.do?processionId=" + processionId);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int stretchOrderId,
			@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;

		procession = processionService.findOne(processionId);
		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(procession);
		stretchOrderService.deleteAndReorder(stretchOrder, stretchOrders);

		result = new ModelAndView(
				"redirect:/stretchOrder/list.do?processionId=" + processionId);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
