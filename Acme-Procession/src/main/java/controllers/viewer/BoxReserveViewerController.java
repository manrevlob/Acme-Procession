package controllers.viewer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BoxInstanceService;
import services.BoxReserveService;
import controllers.AbstractController;
import domain.BoxInstance;
import domain.BoxReserve;
import forms.CreateBoxReserveForm;

@Controller
@RequestMapping("/boxReserve/viewer")
public class BoxReserveViewerController extends AbstractController {
	
		// Services ---------------------------------------------------------------

		@Autowired
		private BoxReserveService boxReserveService;
		@Autowired
		private BoxInstanceService boxInstanceService;

		// Constructors -----------------------------------------------------------

		public BoxReserveViewerController() {
			super();
		}

		// Listing ----------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<BoxReserve> boxReserves;

			boxReserves = boxReserveService.findByPrincipal();

			result = new ModelAndView("boxReserve/list");
			result.addObject("requestURI", "boxReserve/viewer/list.do");
			result.addObject("boxReserves", boxReserves);

			return result;
		}

		// Edition ----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid BoxReserve boxReserve, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(boxReserve);
			} else {
				try {
					boxReserveService.save(boxReserve);
					result = new ModelAndView("redirect:/boxReserve/viewer/list.do");
				} catch (Throwable oops) {
						result = createEditModelAndView(boxReserve,"boxReserve.commit.error");
				}
			}

			return result;
		}
		
		// Cancel -----------------------------------------------------------------
		
		@RequestMapping(value = "/cancel", method = RequestMethod.GET)
		public ModelAndView cancel(@RequestParam int boxReserveId) {
			ModelAndView result;
			BoxReserve boxReserve;
			
			boxReserve = boxReserveService.findOne(boxReserveId);
			
			result = new ModelAndView("redirect:../viewer/list.do");
			
			try{
				boxReserveService.cancel(boxReserve);
			}catch (Exception e) {
				if(e.getMessage()=="cant cancelled"){
					result.addObject("error","boxReserve.cancelReserve.error");
				}else{
					result.addObject("error","boxReserve.cancelReserve.error");
				}
			}
			
			return result;
		}
		
		// Cancel -----------------------------------------------------------------
		
		@RequestMapping(value = "/selectInstance", method = RequestMethod.GET)
		public ModelAndView selectInstance(@RequestParam int boxId) {
			ModelAndView result;
			CreateBoxReserveForm createBoxReserveForm;
			
			createBoxReserveForm = boxReserveService.createBoxReserveForm(boxId);
		
			result = selecInstanceModelAndView(createBoxReserveForm);
			
			return result;
		}
		
		@RequestMapping(value = "/selectInstance", method = RequestMethod.POST, params = "save")
		public ModelAndView selectInstance(@Valid CreateBoxReserveForm createBoxReserveForm, BindingResult binding) {
			ModelAndView result;
			BoxReserve boxReserve;

			if (binding.hasErrors()) {
				result = selecInstanceModelAndView(createBoxReserveForm);
				
			} else {
				try {
					boxReserve = boxReserveService.create(createBoxReserveForm);
					result = createEditModelAndView(boxReserve);
					
				} catch (Throwable oops) {	
					if(oops.getMessage()=="cant reserve"){
						result = selecInstanceModelAndView(createBoxReserveForm,"boxReserve.cantReserve.error");
					}else{
					result = selecInstanceModelAndView(createBoxReserveForm,"boxReserve.commit.error");
				}
					}
			}

			return result;
		}
		// Details --------------------------------------------------------------------------------
		
		@RequestMapping(value = "/details", method = RequestMethod.GET)
		public ModelAndView details(@RequestParam int boxReserveId) {
			ModelAndView result;
			BoxReserve boxReserve;

			boxReserve = boxReserveService.findOneByPrincipal(boxReserveId);

			result = new ModelAndView("boxReserve/details");
			result.addObject("boxReserve", boxReserve);

			return result;
		}
		
		// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(BoxReserve boxReserve) {
			ModelAndView result;

			result = createEditModelAndView(boxReserve, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(BoxReserve boxReserve,
				String message) {
			ModelAndView result;

			result = new ModelAndView("boxReserve/create");
			result.addObject("boxReserve", boxReserve);
			result.addObject("message", message);

			return result;
		}
		
		protected ModelAndView selecInstanceModelAndView(CreateBoxReserveForm createBoxReserveForm) {
			ModelAndView result;

			result = selecInstanceModelAndView(createBoxReserveForm, null);

			return result;
		}

		protected ModelAndView selecInstanceModelAndView(CreateBoxReserveForm createBoxReserveForm,
				String message) {
			ModelAndView result;
			Collection<BoxInstance> boxInstances;
			
			boxInstances = boxInstanceService.findAvailablesByBox(createBoxReserveForm.getBox().getId());

			result = new ModelAndView("boxReserve/selectInstance");
			result.addObject("createBoxReserveForm", createBoxReserveForm);
			result.addObject("message", message);
			result.addObject("boxInstances", boxInstances);

			return result;
		}
		
}
