package com.maice.controller.rest;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maice.controller.components.MessageController;
import com.maice.controller.components.UserController;
import com.maice.model.dto.MaiceServerResponse;

@RestController
public class RestServiceFacade {

	@Autowired
	private UserController userController;

	@Autowired
	private MessageController messageController;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MaiceServerResponse> handleError(HttpServletRequest req, Exception ex) {
		MaiceServerResponse responseObject = new MaiceServerResponse();
		responseObject.addInfo("Fehler im Server: " + ex.getClass());
		ResponseEntity<MaiceServerResponse> response = new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}

	@RequestMapping("/testrest")
	public ResponseEntity<MaiceServerResponse> home() {
		MaiceServerResponse responseObject = new MaiceServerResponse();
		ResponseEntity<MaiceServerResponse> response = new ResponseEntity<>(responseObject , HttpStatus.OK);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		responseObject.setResponseObject(model);
		return response;
	}

	@RequestMapping("/user")
	public ResponseEntity<MaiceServerResponse> user(Principal user) {
		MaiceServerResponse responseObject = new MaiceServerResponse();
		ResponseEntity<MaiceServerResponse> response = new ResponseEntity<>(responseObject , HttpStatus.OK);
		responseObject.setResponseObject(user);
		return response;
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public ResponseEntity<MaiceServerResponse> register(@RequestBody HashMap<String, String> user) {
		return userController.register(user.get("username"), user.get("password"));
	}

	@RequestMapping(path = "/messages", method = RequestMethod.GET)
	public ResponseEntity<MaiceServerResponse> messages() {
		return messageController.getMessages();
	}
	
}
