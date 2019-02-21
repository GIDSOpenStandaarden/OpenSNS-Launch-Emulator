package nl.edia.sns.example.producer.controller;

import nl.edia.sns.example.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ValidationController {
	@Value("${sns.consumer.key.public}")
	String publicK;

	@Autowired
	JwtService jwtService;

	@RequestMapping(value = "validate")
	public JwtValidationValueObject validate(JwtValidationValueObject vo) {
		JwtValidationValueObject valueObject = jwtService.validate(vo);
		return valueObject;
	}
}
