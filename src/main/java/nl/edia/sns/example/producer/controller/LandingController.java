package nl.edia.sns.example.producer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.Base64;

/**
 *
 */
@Controller
public class LandingController {
	@RequestMapping(value = "producer/landing")
	public String landing(String request) throws Exception {
		return "redirect:landing.html?request=" + URLEncoder.encode(request, "utf8");
	}
}
