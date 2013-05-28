package com.monkeybook.spring.mvc.controllers;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    @Autowired
    Comparator<String> comparator;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {

	model.addAttribute("message", "Spring 3 MVC Hello World");
	return "hello";
    }

    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public String compare(@RequestParam("input1") String input1, @RequestParam("input2") String input2, Model model) {

	int result = comparator.compare(input1, input2);
	String inEnglish = (result < 0) ? "less than" : (result > 0 ? "greater than" : "equal to");

	String output = "According to our Comparator, '" + input1 + "' is " + inEnglish + "'" + input2 + "'";

	model.addAttribute("output", output);
	return "compareResult";
    }

}
