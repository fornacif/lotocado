package com.fornacif.lotocado.model;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiSerializationProperty;
import com.google.appengine.api.datastore.Key;

public class Event {
	@ApiSerializationProperty(ignored = AnnotationBoolean.TRUE)
	private Key key;

	private String name;
	private String organizerName;
	private String organizerEmail;
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDay() {
		Calendar calendar = Calendar.getInstance(Locale.FRENCH);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public String getMonth() {
		String[] months = new DateFormatSymbols(Locale.FRENCH).getMonths();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return months[calendar.get(Calendar.MONTH)];
	}
	
	public int getYear() {
		Calendar calendar = Calendar.getInstance(Locale.FRENCH);
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

}
