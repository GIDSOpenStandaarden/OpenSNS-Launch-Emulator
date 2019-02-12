import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.UUID;

public class JwtExample {
	public static void main(String[] args) throws Exception {
		String subject = "treatment_id_124";
		String issuer = "wikiwijk.nl";
		String audience = "therapieland.nl";
		String email = "klaas@devries.nl";
		String firstName = "Klaas";
		String middleName = "de";
		String lastName = "Vries";
		String publicK = "..."; // Public key from appendix A
		String privateK = "..."; // Public key from appendix B
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(
				new X509EncodedKeySpec(Base64.decodeBase64(publicK)));
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(
				new PKCS8EncodedKeySpec(Base64.decodeBase64(privateK)));


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

		System.out.println(jwt);
	}
}
