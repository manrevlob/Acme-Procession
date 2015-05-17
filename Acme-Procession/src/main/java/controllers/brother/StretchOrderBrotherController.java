package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import services.StretchOrderService;
import controllers.AbstractController;
import domain.Procession;
import domain.StretchOrder;

@Controller
@RequestMapping("/stretchOrder/brother")
public class StretchOrderBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------
	
	@Autowired
	private StretchOrderService stretchOrderService;
	
	@Autowired
	private ProcessionService processionService;
	
	// Constructors -----------------------------------------------------------
	
	public StretchOrderBrotherController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/moveToUp", method = RequestMethod.GET)
	public ModelAndView moveToUp(@RequestParam int stretchOrderId, @RequestParam int processionId) {
		ModelAndView result;
		Procession procession;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		
		procession = processionService.findOne(processionId);
		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(procession);
		stretchOrderService.moveToUp(stretchOrder, stretchOrders);
		
		result = new ModelAndView("redirect:/stretchOrder/list.do?processionId=" + processionId);
		
		return result;
	}
	
	@RequestMapping(value = "/moveToDown", method = RequestMethod.GET)
	public ModelAndView moveToDown(@RequestParam int stretchOrderId, @RequestParam int processionId) {
		ModelAndView result;
		Procession procession;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		
		procession = processionService.findOne(processionId);
		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(procession);
		stretchOrderService.moveToDown(stretchOrder, stretchOrders);
		
		result = new ModelAndView("redirect:/stretchOrder/list.do?processionId=" + processionId);
		
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int stretchOrderId, @RequestParam int processionId) {
		ModelAndView result;
		Procession procession;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		
		procession = processionService.findOne(processionId);
		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(procession);
		stretchOrderService.deleteAndReorder(stretchOrder, stretchOrders);
		
		result = new ModelAndView("redirect:/stretchOrder/list.do?processionId=" + processionId);
		
		return result;
	}

}
