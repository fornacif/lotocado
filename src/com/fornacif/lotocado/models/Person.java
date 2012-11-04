package com.fornacif.lotocado.models;

import com.google.appengine.api.datastore.Key;

public class Person {
	private Key key;
	private String name;
	private String email;
	private int couple;
	private Key giftTo;

	public Person(String email) {
		this.email = email;
	}

	public Person(String name, String email, int couple) {
		this.name = name;
		this.email = email;
		this.couple = couple;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCouple() {
		return couple;
	}

	public void setCouple(int couple) {
		this.couple = couple;
	}

	public Key getGiftTo() {
		return giftTo;
	}

	public void setGiftTo(Key giftTo) {
		this.giftTo = giftTo;
	}

	@Override
	public boolean equals(Object object) {
		return ((Person) object).getEmail().equals(getEmail());
	}

}
