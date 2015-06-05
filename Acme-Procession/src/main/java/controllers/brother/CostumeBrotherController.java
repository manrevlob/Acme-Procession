package controllers.brother;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherService;
import services.BrotherhoodService;
import services.CostumeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Costume;
import forms.BrotherhoodAndSizeSelectForm;

@Controller
@RequestMapping("/costume/brother")
public class CostumeBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CostumeService costumeService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public CostumeBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int brotherhoodId,
			@RequestParam int minSize, @RequestParam int maxSize) {
		ModelAndView result;
		Collection<Costume> costumes;
		Brotherhood brotherhood;
		boolean isBigBrother;

		String uri;

		brotherhood = brotherhoodService.findOne(brotherhoodId);
		costumes = costumeService.findAvailablesByBrotherhoodAndSize(
				brotherhood, minSize, maxSize);
		uri = "costume/brother/list.do";

		try {
			isBigBrother = brotherhood.getBigBrothers().contains(
					brotherService.findByPrincipal());
		} catch (Throwable opps) {
			isBigBrother = false;
		}

		result = new ModelAndView("costume/list");
		result.addObject("costumes", costumes);
		result.addObject("requestURI", uri);
		result.addObject("isBigBrother", isBigBrother);

		return result;
	}

	// Create -----------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Details ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int costumeId) {
		ModelAndView result;
		Costume costume;

		costume = costumeService.findOneIfAvailable(costumeId);

		result = new ModelAndView("costume/details");
		result.addObject("costume", costume);

		return result;
	}

	// Search -----------------------------------------------------------------

	@RequestMapping(value = "/findBySize", method = RequestMethod.GET)
	public ModelAndView find() {
		ModelAndView result;
		BrotherhoodAndSizeSelectForm brotherhoodAndSizeSelectForm;

		brotherhoodAndSizeSelectForm = new BrotherhoodAndSizeSelectForm();

		result = findModelAndView(brotherhoodAndSizeSelectForm);
		result.addObject(brotherhoodAndSizeSelectForm);

		return result;
	}

	@RequestMapping(value = "/findBySize", method = RequestMethod.POST, params = "search")
	public ModelAndView find(
			@Valid BrotherhoodAndSizeSelectForm brotherhoodAndSizeSelectForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = findModelAndView(brotherhoodAndSizeSelectForm);
		} else {
			try {
				Assert.isTrue(
						brotherhoodAndSizeSelectForm.getMinSize() <= brotherhoodAndSizeSelectForm
								.getMaxSize(),
						"costume.minSizeHigherThanMaxSize.error");
				result = new ModelAndView(
						"redirect:/costume/brother/list.do?brotherhoodId="
								+ brotherhoodAndSizeSelectForm.getBrotherhood()
										.getId() + "&minSize="
								+ brotherhoodAndSizeSelectForm.getMinSize()
								+ "&maxSize="
								+ brotherhoodAndSizeSelectForm.getMaxSize());
			} catch (Throwable oops) {
				if (oops.getMessage().equals(
						"costume.minSizeHigherThanMaxSize.error")) {
					result = findModelAndView(brotherhoodAndSizeSelectForm,
							"costume.minSizeHigherThanMaxSize.error");
				} else {
					result = findModelAndView(brotherhoodAndSizeSelectForm,
							"costume.commit.error");
				}
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView findModelAndView(
			BrotherhoodAndSizeSelectForm brotherhoodAndSizeSelectForm) {
		ModelAndView result;

		result = findModelAndView(brotherhoodAndSizeSelectForm, null);

		return result;
	}

	protected ModelAndView findModelAndView(
			BrotherhoodAndSizeSelectForm brotherhoodAndSizeSelectForm,
			String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = brotherhoodService.findMines();

		result = new ModelAndView("costume/findBySize");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);

		return result;
	}

}
