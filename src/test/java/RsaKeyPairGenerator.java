/*
 * Copyright 2019 EDIA B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.security.*;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class RsaKeyPairGenerator {

	public static void main(String[] args) throws Exception {
		new RsaKeyPairGenerator().generate();
	}

	public void generate() throws NoSuchAlgorithmException {
		// Create a new generator
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		// Set the key size
		generator.initialize(2024);
		// Generate a pair
		KeyPair keyPair = generator.generateKeyPair();
		// Output the public key as base64
		String publicK = encodeBase64String(keyPair.getPublic().getEncoded());
		// Output the private key as base64
		String privateK = encodeBase64String(keyPair.getPrivate().getEncoded());

		System.out.println(publicK);
		System.out.println(privateK);
	}
}
