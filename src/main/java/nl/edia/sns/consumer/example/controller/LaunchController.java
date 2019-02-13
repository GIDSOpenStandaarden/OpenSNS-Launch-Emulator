package nl.edia.sns.consumer.example.controller;

import nl.edia.sns.consumer.example.service.JwtService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
		String template = IOUtils.toString(getClass().getResourceAsStream("/post_template.html"));
		String body = template.replace("JWT_TICKET", jwtService.generateJwt(vo)).replace("ACTION", vo.getLaunchUrl());
		return ResponseEntity.ok(body);
	}

	public static class FormValueObject {
		String subject;
		String issuer;
		String audience;
		String email;
		String firstName;
		String middleName;
		String lastName;

		public String getLaunchUrl() {
			return launchUrl;
		}

		public void setLaunchUrl(String launchUrl) {
			this.launchUrl = launchUrl;
		}

		String launchUrl;

		public String getAudience() {
			return audience;
		}

		public void setAudience(String audience) {
			this.audience = audience;
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
