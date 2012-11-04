package com.fornacif.lotocado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fornacif.lotocado.services.LotocadoService;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class LotocadoController {

	@Autowired
	private LotocadoService lotocadoService;

	private UserService userService = UserServiceFactory.getUserService();

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView initDatastore() {
		if (userService.isUserLoggedIn() && userService.isUserAdmin()) {
			lotocadoService.initDatastore();
			return genericModelAndView("init", userService.getCurrentUser());
		} else {
			return new ModelAndView("redirect:" + userService.createLoginURL("/init"));
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView lotocado() throws EntityNotFoundException {
		return showMainPage();
	}

	private ModelAndView showMainPage() throws EntityNotFoundException {
		if (!userService.isUserLoggedIn()) {
			return new ModelAndView("redirect:" + userService.createLoginURL("/"));
		} else {
			User user = userService.getCurrentUser();

			String page = "lotocado";
			if (!lotocadoService.isUserAuthorized(user.getEmail())) {
				page = "forbidden";
			}

			ModelAndView modelAndView = genericModelAndView(page, user);
			if (lotocadoService.isUserAuthorized(user.getEmail())) {
				modelAndView.addObject("giftTo", lotocadoService.giftTo(user.getEmail()));
			}
			return modelAndView;
		}
	}

	private ModelAndView genericModelAndView(String page, User user) {
		String logoutURL = userService.createLogoutURL("/");

		ModelAndView modelView = new ModelAndView(page);
		modelView.addObject("logoutURL", logoutURL);
		modelView.addObject("nickname", user.getNickname());
		return modelView;
	}

}
