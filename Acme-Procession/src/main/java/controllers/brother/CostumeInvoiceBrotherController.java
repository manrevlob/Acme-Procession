package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CostumeInvoiceService;
import services.CostumeReserveService;
import controllers.AbstractController;
import domain.CostumeInvoice;
import domain.CostumeReserve;

@Controller
@RequestMapping("/costumeInvoice/brother")
public class CostumeInvoiceBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CostumeInvoiceService costumeInvoiceService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CostumeReserveService costumeReserveService;

	// Constructors -----------------------------------------------------------

	public CostumeInvoiceBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CostumeInvoice> invoices;
		String uri;

		invoices = costumeInvoiceService.findAllByBrother();

		uri = "costumeInvoices/brother/list.do";

		result = new ModelAndView("invoice/list");
		result.addObject("invoices", invoices);
		result.addObject("requestURI", uri);

		return result;
	}

	// details ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int costumeInvoiceId) {
		ModelAndView result;
		CostumeInvoice costumeInvoice;
		CostumeReserve costumeReserve;

		costumeReserve = costumeReserveService
				.findByCostumeInvoice(costumeInvoiceId);
		costumeInvoice = costumeInvoiceService.findOne(costumeInvoiceId);

		result = new ModelAndView("invoice/details");
		result.addObject("invoice", costumeInvoice);
		result.addObject("costumeReserve", costumeReserve);

		return result;
	}

	// Pay -------------------------------------------------------------------

	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam int costumeInvoiceId) {
		ModelAndView result;
		CostumeInvoice costumeInvoice;

		costumeInvoice = costumeInvoiceService.findOne(costumeInvoiceId);

		Assert.isTrue(costumeInvoice.getPaidMoment() == null);

		costumeInvoiceService.paidInvoice(costumeInvoice);

		result = new ModelAndView("redirect:/costumeInvoice/brother/list.do");

		return result;
	}

}
