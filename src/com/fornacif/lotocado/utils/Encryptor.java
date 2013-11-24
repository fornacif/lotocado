package com.fornacif.lotocado.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.fornacif.lotocado.model.EventParticipantIds;

public final class Encryptor {

	private static StandardPBEStringEncryptor encryptor;
	private static final String ENCRYPTION_PASSWORD = "lotocado";
	private static final String ENCRYPTION_OUTPUT_TYPE = "hexadecimal";
	private static final String ENCRYPTION_SEPARATOR = ":";

	static {
		encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(ENCRYPTION_PASSWORD);
		encryptor.setStringOutputType(ENCRYPTION_OUTPUT_TYPE);
	}

	public static final String encryptId(Long eventId) {
		return encryptor.encrypt(String.valueOf(eventId)).toString();
	}

	public static final Long decryptId(String encryptedValue) {
		String value = encryptor.decrypt(encryptedValue);
		return Long.parseLong(value);
	}

	public static final String encryptEventParticipantIds(EventParticipantIds ids) {
		return encryptor.encrypt(ids.getEventId() + ENCRYPTION_SEPARATOR + ids.getParticipantId());
	}

	public static final EventParticipantIds decryptEventParticipantIds(String encryptedValue) {
		String value = encryptor.decrypt(encryptedValue);
		EventParticipantIds ids = new EventParticipantIds(Long.parseLong(value.split(ENCRYPTION_SEPARATOR)[0]), Long.parseLong(value.split(ENCRYPTION_SEPARATOR)[1]));
		return ids;
	}
}
