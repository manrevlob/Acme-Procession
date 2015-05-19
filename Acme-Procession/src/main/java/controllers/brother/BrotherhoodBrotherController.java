package controllers.brother;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brother;
import domain.Brotherhood;
import forms.AddBigBrotherForm;

@Controller
@RequestMapping("/brotherhood/brother")
public class BrotherhoodBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public BrotherhoodBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		boolean isAuthorized;
		String uri;

		brotherhoods = brotherhoodService.findMines();
		isAuthorized = brotherService.findByPrincipal().getIsAuthorized();
		uri = "brotherhood/brother/list.do";

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", uri);
		result.addObject("isAuthorized", isAuthorized);

		return result;
	}

	@RequestMapping(value = "/listOwns", method = RequestMethod.GET)
	public ModelAndView listOwns() {
		ModelAndView result;
		boolean isAuthorized;
		Collection<Brotherhood> brotherhoods;
		String uri;

		brotherhoods = brotherhoodService.findOwns();
		isAuthorized = brotherService.findByPrincipal().getIsAuthorized();
		uri = "brotherhood/brother/listOwns.do";

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", uri);
		result.addObject("isAuthorized", isAuthorized);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.create();

		result = createEditModelAndView(brotherhood);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOne(brotherhoodId);
		result = createEditModelAndView(brotherhood);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Brotherhood brotherhood,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(brotherhood);
		} else {
			try {
				brotherhoodService.save(brotherhood);
				result = new ModelAndView(
						"redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				if (oops.getMessage().equals("brotherhood.invalidYear.error")) {
					result = createEditModelAndView(brotherhood,
							"brotherhood.invalidYear.error");
				} else {
					result = createEditModelAndView(brotherhood,
							"brotherhood.commit.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/addBigBrother", method = RequestMethod.GET)
	public ModelAndView addOrganiser(@RequestParam int brotherhoodId) {
		ModelAndView result;
		AddBigBrotherForm addBigBrotherForm;

		addBigBrotherForm = new AddBigBrotherForm();

		addBigBrotherForm.setBrotherhood(brotherhoodService
				.findOneIfPrincipal(brotherhoodId));
		result = addBrotherModelAndView(addBigBrotherForm);

		return result;
	}

	@RequestMapping(value = "/addBigBrother", method = RequestMethod.POST, params = "save")
	public ModelAndView addOrganiser(
			@Valid AddBigBrotherForm addBigBrotherForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = addBrotherModelAndView(addBigBrotherForm);
		} else {
			try {
				brotherhoodService.addBrother(addBigBrotherForm);
				result = new ModelAndView(
						"redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				result = addBrotherModelAndView(addBigBrotherForm,
						"brotherhood.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Brotherhood brotherhood) {
		ModelAndView result;

		result = createEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Brotherhood brotherhood,
			String message) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView addBrotherModelAndView(
			AddBigBrotherForm addBigBrotherForm) {
		ModelAndView result;

		result = addBrotherModelAndView(addBigBrotherForm, null);

		return result;
	}

	protected ModelAndView addBrotherModelAndView(
			AddBigBrotherForm addBigBrotherForm, String message) {
		ModelAndView result;
		Collection<Brother> brothers;

		brothers = brotherService.findAllBrothersNotAdded(addBigBrotherForm
				.getBrotherhood());

		result = new ModelAndView("brotherhood/addBigBrother");
		result.addObject("addBigBrotherForm", addBigBrotherForm);
		result.addObject("brothers", brothers);
		result.addObject("message", message);

		return result;
	}

}
