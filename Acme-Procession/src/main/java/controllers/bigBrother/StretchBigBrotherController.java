package controllers.bigBrother;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brotherhood;
import forms.BrotherhoodAndStretchTypeSelectForm;

@Controller
@RequestMapping("/stretch/bigBrother")
public class StretchBigBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Constructors -----------------------------------------------------------

	public StretchBigBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Search -----------------------------------------------------------------

	@RequestMapping(value = "/findByBrotherhood", method = RequestMethod.GET)
	public ModelAndView find() {
		ModelAndView result;
		BrotherhoodAndStretchTypeSelectForm brotherhoodAndStretchTypeSelectForm;

		brotherhoodAndStretchTypeSelectForm = new BrotherhoodAndStretchTypeSelectForm();

		result = findModelAndView(brotherhoodAndStretchTypeSelectForm);
		result.addObject(brotherhoodAndStretchTypeSelectForm);

		return result;
	}

	@RequestMapping(value = "/findByBrotherhood", method = RequestMethod.POST, params = "search")
	public ModelAndView find(
			@Valid BrotherhoodAndStretchTypeSelectForm brotherhoodAndStretchTypeSelectForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = findModelAndView(brotherhoodAndStretchTypeSelectForm);
		} else {
			try {
				if (brotherhoodAndStretchTypeSelectForm.getType().equals(
						"ordinary")) {
					result = new ModelAndView(
							"redirect:/ordinaryStretch/bigBrother/list.do?brotherhoodId="
									+ brotherhoodAndStretchTypeSelectForm
											.getBrotherhood().getId());
				} else {
					if (brotherhoodAndStretchTypeSelectForm.getType().equals(
							"float")) {
						result = new ModelAndView(
								"redirect:/floatStretch/bigBrother/list.do?brotherhoodId="
										+ brotherhoodAndStretchTypeSelectForm
												.getBrotherhood().getId());
					} else {
						result = findModelAndView(
								brotherhoodAndStretchTypeSelectForm,
								"stretch.nothingSelected.error");
					}
				}
			} catch (Throwable oops) {
				result = findModelAndView(brotherhoodAndStretchTypeSelectForm,
						"stretch.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView findModelAndView(
			BrotherhoodAndStretchTypeSelectForm brotherhoodAndStretchTypeSelectForm) {
		ModelAndView result;

		result = findModelAndView(brotherhoodAndStretchTypeSelectForm, null);

		return result;
	}

	protected ModelAndView findModelAndView(
			BrotherhoodAndStretchTypeSelectForm brotherhoodAndStretchTypeSelectForm,
			String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = brotherhoodService.findOwns();

		result = new ModelAndView("stretch/findByBrotherhood");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);

		return result;
	}

}
