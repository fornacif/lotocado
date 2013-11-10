package com.fornacif.lotocado.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fornacif.lotocado.model.EncryptedRequest;
import com.fornacif.lotocado.model.EventResponse;
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
import com.google.appengine.api.datastore.Query.FilterOperator;

@Api(name = "lotocado", version = "v1")
public class EventRetriever {

	private final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

	public EventResponse getEvent(EncryptedRequest encryptedRequest) throws BadRequestException {

		try {
			Long eventId = Encryptor.decryptEventId(encryptedRequest.getEncryptedValue());
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
