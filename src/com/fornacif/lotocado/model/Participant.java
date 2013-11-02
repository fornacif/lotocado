package com.fornacif.lotocado.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiSerializationProperty;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class Participant {

	@ApiSerializationProperty(ignored = AnnotationBoolean.TRUE)
	private Key key;
	private String hashKey;

	private String email;
	private String name;

	@ApiSerializationProperty(ignored = AnnotationBoolean.TRUE)
	private Key toKey;
	private String toName;

	@ApiSerializationProperty(ignored = AnnotationBoolean.TRUE)
	private List<Key> exclusionKeys = new ArrayList<>();
	private List<String> exclusionHashKeys = new ArrayList<>();

	@ApiSerializationProperty(ignored = AnnotationBoolean.TRUE)
	private Entity entity;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
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

	public Key getToKey() {
		return toKey;
	}

	public void setToKey(Key toKey) {
		this.toKey = toKey;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public List<Key> getExclusionKeys() {
		return exclusionKeys;
	}

	public void setExclusionKeys(List<Key> exclusionKeys) {
		this.exclusionKeys = exclusionKeys;
	}
	
	public List<String> getExclusionHashKeys() {
		return exclusionHashKeys;
	}

	public void setExclusionHashKeys(List<String> exclusionHashKeys) {
		this.exclusionHashKeys = exclusionHashKeys;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		if (getToKey() == null) {
			return "Person(" + key + " > NONE)";
		} else {
			return "Person(" + key + " > " + getToKey() + ")";
		}
	}
}
