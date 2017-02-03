package com.maice.model.dto;

import java.util.ArrayList;
import java.util.List;

public class MaiceServerResponse {

	private List<String> infos = new ArrayList<String>();
	private Object responseObject;
	
	public List<String> getInfos() {
		return infos;
	}

	public void addInfo(String info) {
		this.infos.add(info);
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

}
