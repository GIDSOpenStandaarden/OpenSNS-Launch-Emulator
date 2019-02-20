import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.UUID;

public class JwtConsumerExample {
	public static void main(String[] args) throws Exception {
		String resourceId = "dagstructuur";
		String subject = "urn:sns:user:wikiwijk.nl:123456";
		String issuer = "wikiwijk.nl";
		String audience = "therapieland.nl";
		String email = "klaas@devries.nl";
		String firstName = "Klaas";
		String middleName = "de";
		String lastName = "Vries";
		String privateK = "MIIEpwIBADANBgkqhkiG9w0BAQEFAASCBJEwggSNAgEAAoH+AL7TOqN8jbMq++PBTATgmItjJSprOnFYP6GbIsIReDN9scha/BEPngzw8moK370irc8Ei+lmgl7NDmvO0RY3o4juqherjKBnhhNcZT4St+ouW/Iy/899u4dTuhFL7hbSKraY/BBBdIowMnPipI5FrvmnrEwwd3fzEmAdDq0HA8UxvCGkawakIWnFGqUxHvfkPyBFuEVtJ7HEyFvKqlBOujS7rE8WgB5yV2IInwo2Wnb801xGAxkpUhTevCGnnYlycj3RvLxRmgPQi2VGhd1oGaZA5FZGubZILWtG74oWJ3OPF7ZxGIVVeq2Gp7VO2bb1q1R5V+0mESBvtSnEok8CAwEAAQKB/VO7cg6Mt8y3fsHIbqfxOV5oScWcOY/Erl8mKJFJgxns/JayvcpqtOpuy6AWV2ixj9y33QC0V15r0fkiTgLWtS5/sykhwFoeMunJ8C7VndfnMbdMA42zWRcfeRTf4YAoBlALPwePASklzu2ktJotH4MyvNrNpY5/nT+JYIgx/LxhIwk/HxJ6uVYiFpAINfAGfBphcgxzKWnV23WvRYtrIJc/XXLvSxK08tvoZfm4c4quf1i3LpTc+1mZmT+jefZoXQcWUnEbCk5Q/8gvDigHMbdOlTqT4/iNj/03PmueWsljiyhbXDYOVGJCaGQpeNaFnhilXPrYEBkAvXIOg6ECfw7l7td0wyPP0vCYFcbQEr3qng9vg2ISVas8gIOU/OeKNSJ9+wbKWcd0DAztxGShuqDZjBXj+RSEL1XrABjDpk9RqpgkBx3NNXEbCBnYg3+LU8HCtUBWi5amaJi8JH2839cVXjdZbPXBPmp5S93SKjmuoiBas8oKITh0yEwwdb8CfwzPAeg765BhD4AmwSzoQRy6Sfxf6R0Z8Uo9a2mxBiGSKPvX7zQMG384208FvTlaW3UoOAhSN6HsfBwWT9pzRIaWAkFP8CWxRiRqzg20FYzTweQZOnqje6YRYSocX64l22zhqV3Y3DdqevIiGpxDFqFM8QXeaAcchCvg6LpTl3ECfwqlC1RynwM1eLhjUhvti5aazjilKrCl/QQOhJx/lXwyaeitLvEZH7C9H+cU8+AbFmfbSJZTfyLDl7bB5B3NnUTLSyLNizAl8WtRLyaYZsx41m15G1xO+gm3+MA4nbIhg6YAJINTp+CoJFqbNDPX+EeimUCYziErv7TA7GRTs60Cfws28F+KnzzBjtXQmNCd5eymOwNKYovFXBt5XWOjyE96boHa1ahHdYfVm0c8KipeL7eLaEv42JbgvOXGr1IAHJ6OFxliSUxnQ5e9H/6ljzzHZ3s0j5wzKZ8EloNNZoTOxqk1h5oQtveaNl1seMoaf2TpPhq6WXDoidz1Ri9l4zECfmzg4k6Jo2YpZVAm1xQU5SPYDawH4DNlWeTMnqBEwfZap7wu79zJkZYdCaegzabb/FxFSu0+21djZbq4+PdtsxIqmg8pObu2s7z+BqC0iM5z01deygAfgP4NRzmQqvECiDmjKWxXZlzQNPxnlu3MJZMrfDXTSzDeIBph1YOIag=="; // Private key from appendix B
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(
				new PKCS8EncodedKeySpec(Base64.decodeBase64(privateK)));


		String jwt = JWT.create()
				.withIssuedAt(new Date())
				.withJWTId(UUID.randomUUID().toString())
				.withSubject(subject)
				.withIssuer(issuer)
				.withAudience(audience)
				.withClaim("resource_id", resourceId)
				.withClaim("email", email)
				.withClaim("first_name", firstName)
				.withClaim("middle_name", middleName)
				.withClaim("last_name", lastName)
				.withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
				.sign(Algorithm.RSA256(null, privateKey));

		System.out.println(jwt);
	}
}
