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

import services.BoxService;
import controllers.AbstractController;
import domain.Box;


@Controller
@RequestMapping("/box/administrator")
public class BoxAdministratorController extends AbstractController {
	
		// Services ---------------------------------------------------------------

		@Autowired
		private BoxService boxService;

		// Constructors -----------------------------------------------------------

		public BoxAdministratorController() {
			super();
		}

		// Listing ----------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<Box> boxes;

			boxes = boxService.findAll();

			result = new ModelAndView("box/list");
			result.addObject("requestURI", "box/administrator/list.do");
			result.addObject("boxes", boxes);

			return result;
		}
		
		@RequestMapping(value = "/listOwns", method = RequestMethod.GET)
		public ModelAndView listOwns() {
			ModelAndView result;
			Collection<Box> boxes;

			boxes = boxService.findByPrincipal();

			result = new ModelAndView("box/list");
			result.addObject("requestURI", "box/administrator/list.do");
			result.addObject("boxes", boxes);

			return result;
		}

		// Creation ---------------------------------------------------------------
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Box box;

			box = boxService.create();

			result = createEditModelAndView(box);

			return result;
		}
		
		// Edition ----------------------------------------------------------------	
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int boxId) {
			ModelAndView result;
			Box box;

			box = boxService.findOneIfPrincipal(boxId);
			result = createEditModelAndView(box);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Box box, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(box);
			} else {
				try {
					boxService.save(box);
					result = new ModelAndView("redirect:list.do");
					
				} catch (Throwable oops) {
						result = createEditModelAndView(box,"box.commit.error");
				}
			}
			return result;
		}
		
		// Details -----------------------------------------------------------------

		@RequestMapping(value = "/details", method = RequestMethod.GET)
		public ModelAndView details(@RequestParam int boxId) {
			ModelAndView result;
			Box box;

			box = boxService.findOne(boxId);

			result = new ModelAndView("box/details");
			result.addObject("box", box);

			return result;
		}


		// Ancillary methods ------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Box box) {
			ModelAndView result;

			result = createEditModelAndView(box, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(Box box, String message) {
			ModelAndView result;

			result = new ModelAndView("box/edit");
			result.addObject("box", box);
			result.addObject("message", message);

			return result;
		}

	

}
