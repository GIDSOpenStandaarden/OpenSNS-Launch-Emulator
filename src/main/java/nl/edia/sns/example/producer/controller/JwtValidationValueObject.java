package nl.edia.sns.example.producer.controller;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 *
 */
public class JwtValidationValueObject {
	String token;
	String header;
	String payload;
	String signature;
	String error;
	String publicKey;
	String algorithm;

	public JwtValidationValueObject() {
	}

	public JwtValidationValueObject(DecodedJWT jwt) {
		this.token = jwt.getToken();
		this.header = jwt.getHeader();
		this.payload = jwt.getPayload();
		this.signature = jwt.getSignature();
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
