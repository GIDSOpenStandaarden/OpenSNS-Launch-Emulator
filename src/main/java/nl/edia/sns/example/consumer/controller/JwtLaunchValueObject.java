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

package nl.edia.sns.example.consumer.controller;

/**
 *
 */
public class JwtLaunchValueObject {
	String resource_id;
	String subject;
	String issuer;
	String audience;
	String email;
	String firstName;
	String middleName;
	String lastName;
	boolean debug = Boolean.FALSE;
	String launchUrl;
	String privacyUrl;

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public boolean getDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
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

	public String getLaunchUrl() {
		return launchUrl;
	}

	public void setLaunchUrl(String launchUrl) {
		this.launchUrl = launchUrl;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPrivacyUrl() {
		return privacyUrl;
	}

	public void setPrivacyUrl(String privacyUrl) {
		this.privacyUrl = privacyUrl;
	}

	public String getResource_id() {
		return resource_id;
	}

	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
