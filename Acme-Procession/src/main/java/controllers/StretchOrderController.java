package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import services.StretchOrderService;
import domain.Procession;
import domain.StretchOrder;

@Controller
@RequestMapping("/stretchOrder")
public class StretchOrderController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StretchOrderService stretchOrderService;

	@Autowired
	private ProcessionService processionService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public StretchOrderController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int processionId) {
		ModelAndView result;
		Collection<StretchOrder> stretchOrders;
		Procession procession;
		String uri;

		procession = processionService.findOne(processionId);
		stretchOrders = stretchOrderService.findByProcession(procession);
		uri = "stretchOrder/list.do";

		result = new ModelAndView("stretchOrder/list");
		result.addObject("stretchOrders", stretchOrders);
		result.addObject("requestURI", uri);

		return result;
	}
	
	// Details -----------------------------------------------------------------

	// Create -----------------------------------------------------------------

	// Edit -------------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
