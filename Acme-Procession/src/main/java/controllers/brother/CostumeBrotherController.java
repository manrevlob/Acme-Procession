package controllers.brother;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.CostumeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Costume;
import forms.CreateCostumesForm;

@Controller
@RequestMapping("/costume/brother")
public class CostumeBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CostumeService costumeService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CostumeBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int brotherhoodId) {
		ModelAndView result;
		CreateCostumesForm createCostumesForm;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOneIfPrincipal(brotherhoodId);

		createCostumesForm = new CreateCostumesForm();
		createCostumesForm.setBrotherhood(brotherhood);

		result = createModelAndView(createCostumesForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid CreateCostumesForm createCostumesForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createModelAndView(createCostumesForm);
		} else {
			try {
				costumeService.saveAll(createCostumesForm);
				result = new ModelAndView(
						"redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				result = createModelAndView(createCostumesForm,
						"costume.commit.error");
			}
		}

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int costumeId) {
		ModelAndView result;
		Costume costume;

		costume = costumeService.findOne(costumeId);
		result = editModelAndView(costume);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Costume costume, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = editModelAndView(costume);
		} else {
			try {
				costumeService.save(costume);
				result = new ModelAndView(
						"redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				result = editModelAndView(costume, "costume.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(
			CreateCostumesForm createCostumesForm) {
		ModelAndView result;

		result = createModelAndView(createCostumesForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(
			CreateCostumesForm createCostumesForm, String message) {
		ModelAndView result;

		result = new ModelAndView("costume/create");
		result.addObject("createCostumesForm", createCostumesForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(Costume costume) {
		ModelAndView result;

		result = editModelAndView(costume, null);

		return result;
	}

	protected ModelAndView editModelAndView(Costume costume, String message) {
		ModelAndView result;

		result = new ModelAndView("costume/edit");
		result.addObject("costume", costume);
		result.addObject("message", message);

		return result;
	}

}
