package com.fornacif.lotocado.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantLight {

	private String email;
	private String name;
	private boolean isResultConsulted = false;
	private List<String> excludedNames = new ArrayList<>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isResultConsulted() {
		return isResultConsulted;
	}

	public void setResultConsulted(boolean isResultConsulted) {
		this.isResultConsulted = isResultConsulted;
	}

	public List<String> getExcludedNames() {
		return excludedNames;
	}

	public void setExcludedNames(List<String> excludedNames) {
		this.excludedNames = excludedNames;
	}

}
