package controllers.viewer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BoxService;

import controllers.AbstractController;
import domain.Box;

@Controller
@RequestMapping("/box/viewer")
public class BoxViewerController extends AbstractController{
	
		// Services ---------------------------------------------------------------

		@Autowired
		private BoxService boxService;

		// Constructors -----------------------------------------------------------

		public BoxViewerController() {
			super();
		}

		// Listing ----------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<Box> boxes;

			boxes = boxService.findAll();

			result = new ModelAndView("box/list");
			result.addObject("requestURI", "box/viewer/list.do");
			result.addObject("boxes", boxes);

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


}
