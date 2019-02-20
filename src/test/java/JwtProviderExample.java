import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class JwtProviderExample {
	public static void main(String[] args) throws Exception {
		String token = args[0];

		// Get the algorithm name from the JWT.
		String algorithmName = JWT.decode(token).getAlgorithm();
		// Get the issuer name from the JWT.
		String issuer = JWT.decode(token).getIssuer();

		// Lookup the issuer.
		String publicK = getPublicKeyForIssuer(issuer); // Public key from appendix A

		// Get the algorithm from the public key and algorithm name.
		Algorithm algorithm = getAlgorithm(publicK, algorithmName);

		// Decode and verify the token.
		DecodedJWT jwt = JWT.require(algorithm)
				.withAudience("therapieland.nl") // Make sure to require yourself to be the audience.
				.build()
				.verify(token);

		// Read the parameters from the jwt token.
		String subject = jwt.getSubject();
		String resourceId = jwt.getClaim("resource_id").asString();
		String email = jwt.getClaim("email").asString();
		String firstName = jwt.getClaim("first_name").asString();
		String middleName = jwt.getClaim("middle_name").asString();
		String lastName = jwt.getClaim("last_name").asString();

		System.out.println(String.format("The SNS launch recieved the user with id %s for resource %s, the user email is %s, the user is known as %s %s %s.",
				subject,
				resourceId,
				email,
				firstName,
				middleName,
				lastName));
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
