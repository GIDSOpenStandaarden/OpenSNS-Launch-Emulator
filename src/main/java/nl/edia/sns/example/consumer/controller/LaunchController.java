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

import nl.edia.sns.example.service.JwtService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 *
 */
@Controller
public class LaunchController {
	@Autowired
	ResourceLoader resourceLoader;
	@Autowired
	JwtService jwtService;


	@RequestMapping(value = "launch.html", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> launch(JwtLaunchValueObject vo) throws IOException {
		String templateName = getTemplateName(vo);
		String template = IOUtils.toString(getClass().getResourceAsStream(templateName));
		String jwt = jwtService.generateJwt(vo);
		String body = template.replace("JWT_TOKEN", jwt).replace("ACTION", vo.getLaunchUrl());
		return ResponseEntity.ok(body);
	}

	private String getTemplateName(JwtLaunchValueObject vo) {
		String templateName;
		if (vo.getDebug()) {
			templateName = "/post_template_debug.html";
		} else {
			templateName = "/post_template.html";
		}
		return templateName;
	}

}
