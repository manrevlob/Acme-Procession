package controllers.administrator;

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
import controllers.AbstractController;
import domain.BoxInstance;


@Controller
@RequestMapping("/boxInstance/administrator")
public class BoxInstanceAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------

		@Autowired
		private BoxInstanceService boxInstanceService;

		// Constructors -----------------------------------------------------------

		public BoxInstanceAdministratorController() {
			super();
		}

		// Listing ----------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam int boxId) {
			ModelAndView result;
			Collection<BoxInstance> boxInstances;

			boxInstances = boxInstanceService.findByBox(boxId);

			result = new ModelAndView("boxInstance/list");
			result.addObject("requestURI", "boxInstance/administrator/list.do");
			result.addObject("boxInstances", boxInstances);

			return result;
		}

		// Creation ---------------------------------------------------------------
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int boxId) {
			ModelAndView result;
			BoxInstance boxInstance;

			boxInstance = boxInstanceService.create(boxId);

			result = createEditModelAndView(boxInstance);

			return result;
		}
		
		// Edition ----------------------------------------------------------------	
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int boxInstanceId) {
			ModelAndView result;
			BoxInstance boxInstance;

			boxInstance = boxInstanceService.findOne(boxInstanceId);
			result = createEditModelAndView(boxInstance);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid BoxInstance boxInstance, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(boxInstance);
			} else {
				try {
					boxInstanceService.saveBoxInstance(boxInstance);
					result = new ModelAndView("redirect:/box/administrator/list.do");
					
				} catch (Throwable oops) {
					
					if(oops.getMessage().equals("cant edit")){
						result = createEditModelAndView(boxInstance,"boxInstance.cantEdit.error");
					}else{
						result = createEditModelAndView(boxInstance,"boxInstance.commit.error");
					}
				}
			}
			return result;
		}

		// Ancillary methods ------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(BoxInstance boxInstance) {
			ModelAndView result;

			result = createEditModelAndView(boxInstance, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(BoxInstance boxInstance, String message) {
			ModelAndView result;

			result = new ModelAndView("boxInstance/edit");
			result.addObject("boxInstance", boxInstance);
			result.addObject("message", message);

			return result;
		}

	

}
