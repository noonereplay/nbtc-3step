package cat.nbtc.step.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/web")
public class WebController {

	@GetMapping("index")
	public String hello(Model model) {

		model.addAttribute("name", "John Doe");

		return "index";
	}
}
