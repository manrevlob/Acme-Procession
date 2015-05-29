package controllers.bigBrother;

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
import services.CostumeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Costume;
import forms.BrotherhoodSelectForm;
import forms.CreateCostumesForm;
import forms.EditCostumeForm;

@Controller
@RequestMapping("/costume/bigBrother")
public class CostumeBigBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CostumeService costumeService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private BrotherService brotherService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CostumeBigBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Collection<Costume> costumes;
		Brotherhood brotherhood;
		String uri;
		boolean isBigBrother;

		brotherhood = brotherhoodService.findOneIfPrincipal(brotherhoodId);
		costumes = costumeService.findByBrotherhood(brotherhood);
		uri = "costume/bigBrother/list.do";

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
	public ModelAndView createSave(
			@Valid CreateCostumesForm createCostumesForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createModelAndView(createCostumesForm);
		} else {
			try {
				costumeService.saveAll(createCostumesForm);
				result = new ModelAndView(
						"redirect:/costume/bigBrother/list.do?brotherhoodId="
								+ createCostumesForm.getBrotherhood().getId());
			} catch (Throwable oops) {
				if (oops.getMessage().equals("costume.bothPricesNull.error")) {
					result = createModelAndView(createCostumesForm,
							"costume.bothPricesNull.error");
				} else {
					result = createModelAndView(createCostumesForm,
							"costume.commit.error");
				}
			}
		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int costumeId) {
		ModelAndView result;
		EditCostumeForm editCostumeForm;
		Costume costume;
		
		editCostumeForm = new EditCostumeForm();

		costume = costumeService.findOneIfPrincipal(costumeId);
		editCostumeForm.setCostume(costume);
		
		if(editCostumeForm.getCostume().getSalePrice() == null) {
			editCostumeForm.setNoToSale(true);
		}
		if(editCostumeForm.getCostume().getRentalPrice() == null) {
			editCostumeForm.setNoToRental(true);
		}
		
		result = editModelAndView(editCostumeForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid EditCostumeForm editCostumeForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = editModelAndView(editCostumeForm);
		} else {
			try {
				costumeService.editOne(editCostumeForm);
				result = new ModelAndView(
						"redirect:/costume/bigBrother/list.do?brotherhoodId="
								+ editCostumeForm.getCostume().getBrotherhood().getId());
			} catch (Throwable oops) {
				if (oops.getMessage().equals("costume.bothPricesNull.error")) {
					result = editModelAndView(editCostumeForm,
							"costume.bothPricesNull.error");
				} else {
					result = editModelAndView(editCostumeForm, "costume.commit.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/markAsAvailable", method = RequestMethod.GET)
	public ModelAndView markAsAvailable(@RequestParam int costumeId) {
		ModelAndView result;
		Costume costume;

		try {
			costume = costumeService.findOneIfRented(costumeId);
			costumeService.markAsAvailable(costume);
		} catch (Throwable oops) {
		}

		result = new ModelAndView(
				"redirect:/costume/bigBrother/list.do?brotherhoodId="
						+ costumeService.findOne(costumeId).getBrotherhood()
								.getId());

		return result;
	}

	// Details ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int costumeId) {
		ModelAndView result;
		Costume costume;

		costume = costumeService.findOneIfPrincipal(costumeId);

		result = new ModelAndView("costume/details");
		result.addObject("costume", costume);

		return result;
	}

	// Search -----------------------------------------------------------------

	@RequestMapping(value = "/findByBrotherhood", method = RequestMethod.GET)
	public ModelAndView find() {
		ModelAndView result;
		BrotherhoodSelectForm brotherhoodSelectForm;

		brotherhoodSelectForm = new BrotherhoodSelectForm();

		result = findModelAndView(brotherhoodSelectForm);
		result.addObject(brotherhoodSelectForm);

		return result;
	}

	@RequestMapping(value = "/findByBrotherhood", method = RequestMethod.POST, params = "search")
	public ModelAndView find(
			@Valid BrotherhoodSelectForm brotherhoodSelectForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = findModelAndView(brotherhoodSelectForm);
		} else {
			try {
				result = new ModelAndView(
						"redirect:/costume/bigBrother/list.do?brotherhoodId="
								+ brotherhoodSelectForm.getBrotherhood()
										.getId());
			} catch (Throwable oops) {
				result = findModelAndView(brotherhoodSelectForm,
						"costume.commit.error");
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

	protected ModelAndView editModelAndView(EditCostumeForm editCostumeForm) {
		ModelAndView result;

		result = editModelAndView(editCostumeForm, null);

		return result;
	}

	protected ModelAndView editModelAndView(EditCostumeForm editCostumeForm, String message) {
		ModelAndView result;

		result = new ModelAndView("costume/edit");
		result.addObject("editCostumeForm", editCostumeForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView findModelAndView(
			BrotherhoodSelectForm brotherhoodSelectForm) {
		ModelAndView result;

		result = findModelAndView(brotherhoodSelectForm, null);

		return result;
	}

	protected ModelAndView findModelAndView(
			BrotherhoodSelectForm brotherhoodSelectForm, String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		boolean isBigBrother;

		brotherhoods = brotherhoodService.findOwns();

		try {
			isBigBrother = brotherhoodSelectForm.getBrotherhood()
					.getBigBrothers()
					.contains(brotherService.findByPrincipal());
		} catch (Throwable opps) {
			isBigBrother = false;
		}

		result = new ModelAndView("costume/findByBrotherhood");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);
		result.addObject("isBigBrother", isBigBrother);

		return result;
	}

}
