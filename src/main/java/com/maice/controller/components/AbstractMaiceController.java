package com.maice.controller.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.maice.model.dto.MaiceServerResponse;

public class AbstractMaiceController {
	
	private MaiceServerResponse responseObject = new MaiceServerResponse();
	
	protected ResponseEntity<MaiceServerResponse> createResponse(Object content){
		ResponseEntity<MaiceServerResponse> response = new ResponseEntity<>(responseObject, HttpStatus.OK);
		responseObject.setResponseObject(content);
		return response;
	}
	
	protected void addInfo(String info) {
		this.responseObject.addInfo(info);
	}
}
