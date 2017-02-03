package com.maice.controller.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.maice.model.MaiceConstants;
import com.maice.model.dto.MaiceServerResponse;

@Component
@RequestScope
public class MessageController extends AbstractMaiceController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private static HashMap<String, Properties> messagesForCountryCode = new HashMap<String, Properties>();

	public MessageController() {
		if (MessageController.messagesForCountryCode.size() == 0) {
			String[] files = new File(MaiceConstants.PATH_LANGUAGES).list();
			for (String f : files) {
				loadPropertiesFile(f.substring(9, 11), f);
			}
		}
	}

	private void loadPropertiesFile(String countryCode, String filename) {
		try {
			InputStream in = new FileInputStream(new File(MaiceConstants.PATH_LANGUAGES + "/" + filename));
			Properties p = new Properties();
			p.load(in);
			in.close();
			messagesForCountryCode.put(countryCode, p);
		} catch (Exception e) {
			LOG.error("Could not load Properties File " + filename + ". Exception: " + e.getMessage());
		}
	}

	public ResponseEntity<MaiceServerResponse> getMessages() {
		return createResponse(messagesForCountryCode);
	}

}
