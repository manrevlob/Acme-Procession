package controllers.bigBrother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StretchOrderService;
import controllers.AbstractController;
import domain.StretchOrder;

@Controller
@RequestMapping("/stretchOrder/bigBrother")
public class StretchOrderBigBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StretchOrderService stretchOrderService;

	// Constructors -----------------------------------------------------------

	public StretchOrderBigBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/moveToUp", method = RequestMethod.GET)
	public ModelAndView moveToUp(@RequestParam int stretchOrderId) {
		ModelAndView result;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;

		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(stretchOrder
				.getProcession());
		stretchOrderService.moveToUp(stretchOrder, stretchOrders);

		result = new ModelAndView(
				"redirect:/stretchOrder/list.do?processionId="
						+ stretchOrder.getProcession().getId());

		return result;
	}

	@RequestMapping(value = "/moveToDown", method = RequestMethod.GET)
	public ModelAndView moveToDown(@RequestParam int stretchOrderId) {
		ModelAndView result;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;

		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(stretchOrder
				.getProcession());
		stretchOrderService.moveToDown(stretchOrder, stretchOrders);

		result = new ModelAndView(
				"redirect:/stretchOrder/list.do?processionId="
						+ stretchOrder.getProcession().getId());

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int stretchOrderId) {
		ModelAndView result;
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		String error;

		stretchOrder = stretchOrderService.findOne(stretchOrderId);
		stretchOrders = stretchOrderService.findByProcession(stretchOrder
				.getProcession());
		error = null;

		try {
			stretchOrderService.deleteAndReorder(stretchOrder, stretchOrders);
		} catch (Throwable oops) {
			if (oops.getMessage().equals("stretch.deleteWithRegistrations.error")) {
				error = "stretch.deleteWithRegistrations.error";
			} else {
				error = "stretch.commit.error";
			}
		}

		result = new ModelAndView(
				"redirect:/stretchOrder/list.do?processionId="
						+ stretchOrder.getProcession().getId());
		result.addObject("error", error);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
