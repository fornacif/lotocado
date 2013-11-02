package com.fornacif.lotocado.model;

public class EventParticipantIds {
	private Long eventId;
	private Long participantId;

	public EventParticipantIds(Long eventId, Long participantId) {
		this.eventId = eventId;
		this.participantId = participantId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

}
