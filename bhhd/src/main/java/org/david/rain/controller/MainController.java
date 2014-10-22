package org.david.rain.controller;

import org.david.rain.controller.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/{game}")
public class MainController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String game(@PathVariable(value = "game") String game,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws IOException {
		log.info("index page game is {}", game);

		Integer id = getGameId(request, game);
		if (id.intValue() == -1) {
			response.sendRedirect(request.getContextPath()
					+ "/err/404.jsp");
			return null;
		} else {
			return "index";
		}
	}
}
