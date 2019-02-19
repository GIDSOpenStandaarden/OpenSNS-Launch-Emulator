package nl.edia.sns.example.producer.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 */
@RestController
public class ValidationController {
	@Value("${sns.consumer.key.public}")
	String publicK;

	@RequestMapping(value = "producer/validate")
	public JwtValueObject validate(JwtValueObject vo) {
		if (vo.getPublicKey() == null) {
			vo.setPublicKey(publicK);
		}
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(
					new X509EncodedKeySpec(Base64.decodeBase64(vo.getPublicKey())));
			DecodedJWT jwt = JWT.decode(vo.getToken());
			Algorithm algorithm = Algorithm.RSA256(publicKey, null);
			algorithm.verify(jwt);
			JwtValueObject valueObject = new JwtValueObject(jwt);
			valueObject.setPublicKey(vo.getPublicKey());
			return valueObject;
		} catch (Exception e) {
			JwtValueObject valueObject = new JwtValueObject();
			valueObject.setError(e.getMessage());
			valueObject.setToken(vo.getToken());
			return valueObject;
		}
	}
}
