package com.fornacif.lotocado.helper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.floreysoft.jmte.Engine;
import com.fornacif.lotocado.model.Event;
import com.fornacif.lotocado.model.EventParticipantIds;
import com.fornacif.lotocado.model.Participant;
import com.fornacif.lotocado.utils.Encryptor;
import com.google.api.server.spi.IoUtil;

public final class EmailHelper {
	public static void sendEmailToOrganizer(Event event) throws MessagingException, IOException {
		String recipientEmail = event.getOrganizerEmail();
		String recipientName = event.getOrganizerName();
		String subject = "[Lotocado] Administration évènement '" + event.getName() + "'";
		String body = getEmailToOrganizerBodyContent(event);
		sendEmail(recipientEmail, recipientName, subject, body);
	}

	public static void sendEmailToParticipant(Event event, Participant participant) throws MessagingException, IOException {
		String recipientEmail = participant.getEmail();
		String recipientName = participant.getName();
		String subject = "[Lotocado] Invitation à l'évènement '" + event.getName() + "'";
		String body = getEmailToParticipantBodyContent(event, participant);
		sendEmail(recipientEmail, recipientName, subject, body);
	}
	
	public static String getOrganizerLink(Event event) {
		return getHostUrl() + "/#/event/" + Encryptor.encryptId(event.getKey().getId());
	}

	private static void sendEmail(String recipientEmail, String recipientName, String subject, String body) throws MessagingException, UnsupportedEncodingException {
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("contact.lotocado@gmail.com", "Lotocado"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail, recipientName));
		message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		message.setContent(body, "text/html; charset=utf-8");
		Transport.send(message);
	}

	private static String getEmailToOrganizerBodyContent(Event event) throws IOException {
		String input = IoUtil.readFile(new File("templates/event.html"));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("event", event);
		model.put("link", getOrganizerLink(event));
		Engine engine = new Engine();
		return engine.transform(input, model);
	}

	private static String getEmailToParticipantBodyContent(Event event, Participant participant) throws IOException {
		String input = IoUtil.readFile(new File("templates/participant.html"));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("event", event);
		model.put("participant", participant);
		model.put("link", getParticipantLink(event, participant));
		Engine engine = new Engine();
		return engine.transform(input, model);
	}

	private static String getParticipantLink(Event event, Participant participant) {
		EventParticipantIds ids = new EventParticipantIds(event.getKey().getId(), participant.getKey().getId());
		return getHostUrl() + "/#/participant/" + Encryptor.encryptEventParticipantIds(ids);
	}

	private static String getHostUrl() {
		String hostUrl;
		String environment = System.getProperty("com.google.appengine.runtime.environment");
		if ("Production".equals(environment)) {
			String applicationId = System.getProperty("com.google.appengine.application.id");
			String version = System.getProperty("com.google.appengine.application.version");
			hostUrl = "http://" + applicationId + ".appspot.com/";
		} else {
			hostUrl = "http://localhost:8888";
		}
		return hostUrl;
	}
}
