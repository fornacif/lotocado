package com.fornacif.lotocado.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiSerializationProperty;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class Participant {
	private String hashKey;
	private Long id;
	private String email;
	private String name;
	private Long giverId;
	private Long receiverId;
	private List<String> exclusionHashKeys = new ArrayList<String>();
	private List<Long> exclusionIds = new ArrayList<Long>();

	@ApiSerializationProperty(ignored = AnnotationBoolean.TRUE)
	private Key eventKey;

	@ApiSerializationProperty(ignored = AnnotationBoolean.TRUE)
	private Entity entity;

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getGiverId() {
		return giverId;
	}

	public void setGiverId(Long giverId) {
		this.giverId = giverId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public List<String> getExclusionHashKeys() {
		return exclusionHashKeys;
	}

	public void setExclusionHashKeys(List<String> exclusionHashKeys) {
		this.exclusionHashKeys = exclusionHashKeys;
	}

	public List<Long> getExclusionIds() {
		return exclusionIds;
	}

	public void setExclusionIds(List<Long> exclusionIds) {
		this.exclusionIds = exclusionIds;
	}

	public Key getEventKey() {
		return eventKey;
	}

	public void setEventKey(Key eventKey) {
		this.eventKey = eventKey;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		if (getGiverId() == null) {
			return "Person(" + id + " > NONE)";
		} else {
			return "Person(" + id + " > " + getGiverId() + ")";
		}
	}
}
