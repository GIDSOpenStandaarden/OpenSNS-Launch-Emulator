package nl.edia.sns.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.edia.sns.example.consumer.controller.JwtLaunchValueObject;
import nl.edia.sns.example.producer.controller.JwtValidationValueObject;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
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

	public String generateJwt(JwtLaunchValueObject vo) {
		String resourceId = vo.getResource_id();
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
				.withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
				.withClaim("email", email)
				.withClaim("resource_id", resourceId)
				.withClaim("first_name", firstName)
				.withClaim("middle_name", middleName)
				.withClaim("last_name", lastName)
				.sign(Algorithm.RSA256(publicKey, privateKey));
		return jwt;
	}

	public JwtValidationValueObject validate(JwtValidationValueObject vo) {
		if (StringUtils.isEmpty(vo.getPublicKey())) {
			vo.setPublicKey(publicK);
		}
		if (StringUtils.isEmpty(vo.getAlgorithm())) {
			// Get the algorithm name from the JWT.
			vo.setAlgorithm(JWT.decode(vo.getToken()).getAlgorithm());
		}
		try {

			Algorithm algorithm = getAlgorithm(vo.getPublicKey(), vo.getAlgorithm());

			DecodedJWT jwt = JWT.require(algorithm).build().verify(vo.getToken());

			return new JwtValidationValueObject(jwt);
		} catch (Exception e) {
			JwtValidationValueObject valueObject = new JwtValidationValueObject();
			valueObject.setError(e.getMessage());
			valueObject.setToken(vo.getToken());
			return valueObject;
		}
	}

	/**
	 * This method should lookup the public key configured with the issuer from the configuration
	 * and / or persistent storage.
	 *
	 * @param issuer the issuer from the JWT token.
	 * @return a public key encoded as String
	 */
	private static String getPublicKeyForIssuer(String issuer) {
		// Return the test key.
		return "MIIBHjANBgkqhkiG9w0BAQEFAAOCAQsAMIIBBgKB/gC+0zqjfI2zKvvjwUwE4JiLYyUqazpxWD+hmyLCEXgzfbHIWvwRD54M8PJqCt+9Iq3PBIvpZoJezQ5rztEWN6OI7qoXq4ygZ4YTXGU+ErfqLlvyMv/PfbuHU7oRS+4W0iq2mPwQQXSKMDJz4qSORa75p6xMMHd38xJgHQ6tBwPFMbwhpGsGpCFpxRqlMR735D8gRbhFbSexxMhbyqpQTro0u6xPFoAecldiCJ8KNlp2/NNcRgMZKVIU3rwhp52JcnI90by8UZoD0ItlRoXdaBmmQORWRrm2SC1rRu+KFidzjxe2cRiFVXqthqe1Ttm29atUeVftJhEgb7UpxKJPAgMBAAE=";
	}

	/**
	 * Unfortunately, this implementation of JWT has no helper method for selecting the right
	 * algorithm from the header. The public key must match the algorithm type (RSA or EC), but
	 * the size of the hash algorithm can vary.
	 *
	 * @param publicKey
	 * @param algorithmName
	 * @return in instance of the {@link Algorithm} class.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IllegalArgumentException if the algorithmName is not one of RS{256,384,512} or ES{256,384,512}
	 */
	private static Algorithm getAlgorithm(String publicKey, String algorithmName) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalArgumentException {
		switch (algorithmName) {
			case "RS256": {
				return Algorithm.RSA256(getRsaPublicKey(publicKey), null);
			}
			case "RS384": {
				return Algorithm.RSA384(getRsaPublicKey(publicKey), null);
			}
			case "RS512": {
				return Algorithm.RSA512(getRsaPublicKey(publicKey), null);
			}
			case "ES256": {
				return Algorithm.ECDSA256(getEcPublicKey(publicKey), null);
			}
			case "ES384": {
				return Algorithm.ECDSA384(getEcPublicKey(publicKey), null);
			}
			case "ES512": {
				return Algorithm.ECDSA512(getEcPublicKey(publicKey), null);
			}
			default:
				throw new IllegalArgumentException(String.format("Unsupported algorithm %s", algorithmName));
		}

	}

	/**
	 * Parses a public key to an instance of {@link ECPublicKey}.
	 *
	 * @param publicKey the string representation of the public key.
	 * @return an instance of {@link ECPublicKey}.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static ECPublicKey getEcPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		return (ECPublicKey) keyFactory.generatePublic(
				new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));
	}

	/**
	 * Parses a public key to an instance of {@link RSAPublicKey}.
	 *
	 * @param publicKey the string representation of the public key.
	 * @return an instance of {@link RSAPublicKey}.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static RSAPublicKey getRsaPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPublicKey) keyFactory.generatePublic(
				new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));
	}

}
