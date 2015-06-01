package controllers.viewer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BoxInvoiceService;
import services.BoxReserveService;
import controllers.AbstractController;
import domain.BoxInvoice;
import domain.BoxReserve;

@Controller
@RequestMapping("/boxInvoice/viewer")
public class BoxInvoiceViewerController extends AbstractController {
	
		// Services ---------------------------------------------------------------

		@Autowired
		private BoxInvoiceService boxInvoiceService;
		@Autowired
		private BoxReserveService boxReserveService;

		// Constructors -----------------------------------------------------------

		public BoxInvoiceViewerController() {
			super();
		}

		// Listing ----------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<BoxInvoice> invoices;

			invoices = boxInvoiceService.findByPrincipal();

			result = new ModelAndView("invoice/list");
			result.addObject("requestURI", "boxInvoice/viewer/list.do");
			result.addObject("invoices", invoices);

			return result;
		}
		
		// Details  -------------------------------------------------------------
		
		@RequestMapping(value = "/details", method = RequestMethod.GET)
		public ModelAndView details(@RequestParam int boxInvoiceId) {
			ModelAndView result;
			BoxInvoice boxInvoice;
			BoxReserve boxReserve;

			boxInvoice = boxInvoiceService.findOne(boxInvoiceId);
			boxReserve = boxReserveService.findByBoxInvoice(boxInvoiceId);
			

			result = new ModelAndView("invoice/details");
			result.addObject("invoice", boxInvoice);
			result.addObject("boxReserve", boxReserve);

			return result;
		}
		
		// Pay -------------------------------------------------------------------

		@RequestMapping(value = "/pay", method = RequestMethod.GET)
		public ModelAndView pay(@RequestParam int boxInvoiceId) {
			ModelAndView result;
			BoxInvoice boxInvoice;

			boxInvoice = boxInvoiceService.findOne(boxInvoiceId);

			boxInvoiceService.payInvoice(boxInvoice);

			result = new ModelAndView("redirect:/boxInvoice/viewer/list.do");

			return result;
		}

		
		
}
