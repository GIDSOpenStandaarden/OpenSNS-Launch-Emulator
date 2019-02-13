package nl.edia.sns.consumer.example.controller;

import nl.edia.sns.consumer.example.service.JwtService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 *
 */
@Controller
public class LaunchController {
	@Autowired
	ResourceLoader resourceLoader;
	@Autowired
	JwtService jwtService;


	@RequestMapping(value = "launch.html", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> launch(FormValueObject vo) throws IOException {
		String templateName = getTemplateName(vo);
		String template = IOUtils.toString(getClass().getResourceAsStream(templateName));
		String jwt = jwtService.generateJwt(vo);
		String body = template.replace("JWT_TOKEN", jwt).replace("ACTION", vo.getLaunchUrl());
		return ResponseEntity.ok(body);
	}

	private String getTemplateName(FormValueObject vo) {
		String templateName;
		if (vo.getDebug()) {
			templateName = "/post_template_debug.html";
		} else {
			templateName = "/post_template.html";
		}
		return templateName;
	}

	public static class FormValueObject {
		String subject;
		String issuer;
		String audience;
		String email;
		String firstName;
		String middleName;
		String lastName;
		boolean debug = Boolean.FALSE;
		String launchUrl;

		public String getAudience() {
			return audience;
		}

		public void setAudience(String audience) {
			this.audience = audience;
		}

		public boolean getDebug() {
			return debug;
		}

		public void setDebug(boolean debug) {
			this.debug = debug;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getIssuer() {
			return issuer;
		}

		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getLaunchUrl() {
			return launchUrl;
		}

		public void setLaunchUrl(String launchUrl) {
			this.launchUrl = launchUrl;
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

	}
}
