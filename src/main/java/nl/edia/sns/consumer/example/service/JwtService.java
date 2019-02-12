package nl.edia.sns.consumer.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import nl.edia.sns.consumer.example.controller.LaunchController;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.UUID;

/**
 *
 */
@Service
public class JwtService {
	@Value("${sns.consumer.key.public}")
	String publicK = "..."; // Public key from appendix A
	@Value("${sns.consumer.key.private}")
	String privateK = "..."; // Public key from appendix B

	protected RSAPublicKey publicKey;
	protected RSAPrivateKey privateKey;

	@PostConstruct
	public void init() throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		publicKey = (RSAPublicKey) keyFactory.generatePublic(
				new X509EncodedKeySpec(Base64.decodeBase64(publicK)));
		privateKey = (RSAPrivateKey) keyFactory.generatePrivate(
				new PKCS8EncodedKeySpec(Base64.decodeBase64(privateK)));
	}

	public String generateJwt(LaunchController.FormValueObject vo) {
		String subject = vo.getSubject();
		String issuer = vo.getIssuer();
		String audience = vo.getAudience();
		String email = vo.getEmail();
		String firstName = vo.getFirstName();
		String middleName = vo.getMiddleName();
		String lastName = vo.getLastName();
		String jwt = JWT.create()
				.withIssuedAt(new Date())
				.withJWTId(UUID.randomUUID().toString())
				.withSubject(subject)
				.withIssuer(issuer)
				.withAudience(audience)
				.withClaim("email", email)
				.withClaim("first_name", firstName)
				.withClaim("middle_name", middleName)
				.withClaim("last_name", lastName)
				.sign(Algorithm.RSA256(publicKey, privateKey));
		return jwt;
	}
}
