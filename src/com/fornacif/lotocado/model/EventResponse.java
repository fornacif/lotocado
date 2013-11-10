package com.fornacif.lotocado.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventResponse {
	private String name;
	private Date date;
	private String organizerName;
	private String organizerEmail;
	private List<ParticipantLight> participants = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public String getOrganizerEmail() {
		return organizerEmail;
	}

	public void setOrganizerEmail(String organizerEmail) {
		this.organizerEmail = organizerEmail;
	}

	public List<ParticipantLight> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantLight> participants) {
		this.participants = participants;
	}

}
