package com.fornacif.lotocado.model;

import java.util.List;

public class DrawingLotsRequest {
    private Event event;
    private List<Participant> participants;

    public Event getEvent() {
        return event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
