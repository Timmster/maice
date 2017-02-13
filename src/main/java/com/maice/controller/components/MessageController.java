package com.maice.controller.components;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.maice.model.dto.MaiceServerResponse;

@Component
@RequestScope
public class MessageController extends AbstractMaiceController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private static HashMap<String, Properties> messagesForCountryCode = new HashMap<String, Properties>();

	private static final String[] languages = {"de"};
	
	
	public MessageController() {
		if (MessageController.messagesForCountryCode.size() == 0) {
			for (String lang : languages) {
			  try {
			    InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("languages\\messages_" + lang + ".properties");
		      Properties p = new Properties();
		      p.load(in);
		      in.close();
		      messagesForCountryCode.put(lang, p);
		    } catch (Exception e) {
		      LOG.error("Could not load Properties File " + lang + ". Exception: " + e.getMessage());
		    }
			}
		}
	}

	public ResponseEntity<MaiceServerResponse> getMessages() {
		return createResponse(messagesForCountryCode);
	}

}
