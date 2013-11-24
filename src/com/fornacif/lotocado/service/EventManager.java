package com.fornacif.lotocado.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.fornacif.lotocado.helper.EmailHelper;
import com.fornacif.lotocado.model.ParticipantRequest;
import com.fornacif.lotocado.model.EncryptedRequest;
import com.fornacif.lotocado.model.Event;
import com.fornacif.lotocado.model.EventResponse;
import com.fornacif.lotocado.model.Participant;
import com.fornacif.lotocado.model.ParticipantLight;
import com.fornacif.lotocado.utils.Constants;
import com.fornacif.lotocado.utils.Encryptor;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.FilterOperator;

@Api(name = "lotocado", version = "v2")
public class EventManager {

	private final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

	public EventResponse getEvent(EncryptedRequest encryptedRequest) throws BadRequestException {
		try {
			Long eventId = Encryptor.decryptId(encryptedRequest.getEncryptedValue());
			Key eventKey = KeyFactory.createKey(Constants.EVENT_ENTITY, eventId);
			Entity eventEntity = datastoreService.get(eventKey);

			EventResponse eventResponse = new EventResponse();
			eventResponse.setName((String) eventEntity.getProperty(Constants.EVENT_NAME));
			eventResponse.setDate((Date) eventEntity.getProperty(Constants.EVENT_DATE));
			eventResponse.setOrganizerName((String) eventEntity.getProperty(Constants.EVENT_ORGANIZER_NAME));
			eventResponse.setOrganizerEmail((String) eventEntity.getProperty(Constants.EVENT_ORGANIZER_EMAIL));

			Query query = new Query(Constants.PARTICIPANT_ENTITY);
			query.setFilter(FilterOperator.EQUAL.of(Constants.PARTICIPANT_EVENT_KEY, eventKey));
			PreparedQuery preparedQuery = datastoreService.prepare(query);
			Iterable<Entity> participantIterable = preparedQuery.asIterable();
			for (Entity participantEntity : participantIterable) {
				ParticipantLight participantLight = new ParticipantLight();
				participantLight.setEncryptedEventId(Encryptor.encryptId(eventId));
				participantLight.setEncryptedParticipantId(Encryptor.encryptId(participantEntity.getKey().getId()));
				participantLight.setName((String) participantEntity.getProperty(Constants.PARTICIPANT_NAME));
				participantLight.setEmail((String) participantEntity.getProperty(Constants.PARTICIPANT_EMAIL));
				participantLight.setResultConsulted((boolean) participantEntity.getProperty(Constants.PARTICIPANT_IS_RESULT_CONSULTED));
				participantLight.setExcludedNames(getExcludedNames(participantEntity));
				eventResponse.getParticipants().add(participantLight);
			}

			return eventResponse;
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("{\"code\": \"" + Constants.EVENT_NOT_FOUND_ERROR_CODE + "\"}");
		}
	}

	public void resendEmail(ParticipantRequest participantRequest) throws BadRequestException {
		Transaction transaction = datastoreService.beginTransaction();
		try {
			Long eventId = Encryptor.decryptId(participantRequest.getParticipant().getEncryptedEventId());
			Key eventKey = KeyFactory.createKey(Constants.EVENT_ENTITY, eventId);
			Entity eventEntity = datastoreService.get(eventKey);

			Long participantId = Encryptor.decryptId(participantRequest.getParticipant().getEncryptedParticipantId());
			Key participantKey = KeyFactory.createKey(eventKey, Constants.PARTICIPANT_ENTITY, participantId);
			Entity participantEntity = datastoreService.get(participantKey);

			participantEntity.setProperty(Constants.PARTICIPANT_EMAIL, participantRequest.getParticipant().getEmail());
			datastoreService.put(participantEntity);

			Event event = new Event();
			event.setKey(eventKey);
			event.setDate((Date) eventEntity.getProperty(Constants.EVENT_DATE));
			event.setName((String) eventEntity.getProperty(Constants.EVENT_NAME));
			event.setOrganizerName((String) eventEntity.getProperty(Constants.EVENT_ORGANIZER_NAME));
			event.setOrganizerEmail((String) eventEntity.getProperty(Constants.EVENT_ORGANIZER_EMAIL));

			Participant participant = new Participant();
			participant.setKey(participantKey);
			participant.setEntity(participantEntity);
			participant.setName((String) participantEntity.getProperty(Constants.PARTICIPANT_NAME));
			participant.setEmail((String) participantEntity.getProperty(Constants.PARTICIPANT_EMAIL));

			EmailHelper.sendEmailToParticipant(event, participant);
			
			transaction.commit();
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("{\"code\": \"" + Constants.PARTICIPANT_NOT_FOUND_ERROR_CODE + "\"}");
		} catch (MessagingException | IOException e) {
			throw new BadRequestException("{\"code\": \"" + Constants.PARTICIPANT_EMAIL_NOT_RESEND_ERROR_CODE + "\"}");
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<String> getExcludedNames(Entity participantEntity) throws EntityNotFoundException {
		List<String> excudedNames = new ArrayList<>();
		List<Key> exlusionKeys = (List<Key>) participantEntity.getProperty(Constants.PARTICIPANT_EXCLUSION_KEYS);
		if (exlusionKeys != null) {
			Map<Key, Entity> excludedParticipants = datastoreService.get(exlusionKeys);
			for (Key excludedParticipantKey : excludedParticipants.keySet()) {
				Entity excludedParticipantEntity = datastoreService.get(excludedParticipantKey);
				excudedNames.add((String) excludedParticipantEntity.getProperty(Constants.PARTICIPANT_NAME));
			}
		}
		return excudedNames;
	}

}
