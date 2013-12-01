package com.fornacif.lotocado.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantResponse {
	private String name;
	private String toName;
	private String eventName;
	private List<ParticipantLight> participants = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public List<ParticipantLight> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantLight> participants) {
		this.participants = participants;
	}
	
	

}
