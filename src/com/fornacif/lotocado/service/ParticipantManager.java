package com.fornacif.lotocado.service;

import com.fornacif.lotocado.helper.ExclusionHelper;
import com.fornacif.lotocado.model.EncryptedRequest;
import com.fornacif.lotocado.model.EventParticipantIds;
import com.fornacif.lotocado.model.ParticipantLight;
import com.fornacif.lotocado.model.ParticipantResponse;
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

@Api(name = "lotocado", version = "v2")
public class ParticipantManager {

	private final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	
	public ParticipantResponse getParticipant(EncryptedRequest encryptedRequest) throws BadRequestException {
		Transaction transaction = datastoreService.beginTransaction();
		try {
			EventParticipantIds ids = Encryptor.decryptEventParticipantIds(encryptedRequest.getEncryptedValue());
			Key eventKey = KeyFactory.createKey(Constants.EVENT_ENTITY, ids.getEventId());
			Entity eventEntity = datastoreService.get(eventKey);
			
			Key participantKey = KeyFactory.createKey(eventKey, Constants.PARTICIPANT_ENTITY, ids.getParticipantId());
			Entity participantEntity = datastoreService.get(participantKey);
	
			ParticipantResponse participantResponse = new ParticipantResponse();
			participantResponse.setEventName((String) eventEntity.getProperty(Constants.EVENT_NAME));
			participantResponse.setName((String) participantEntity.getProperty(Constants.PARTICIPANT_NAME));
			participantResponse.setToName((String) participantEntity.getProperty(Constants.PARTICIPANT_TO_NAME));
			
			Query query = new Query(Constants.PARTICIPANT_ENTITY).setAncestor(eventKey);
			PreparedQuery preparedQuery = datastoreService.prepare(query);
			Iterable<Entity> participantIterable = preparedQuery.asIterable();
			for (Entity otherParticipantEntity : participantIterable) {
				ParticipantLight participantLight = new ParticipantLight();
				participantLight.setName((String) otherParticipantEntity.getProperty(Constants.PARTICIPANT_NAME));
				participantLight.setExcludedNames(ExclusionHelper.getExcludedNames(datastoreService, otherParticipantEntity));
				participantResponse.getParticipants().add(participantLight);
			}
			
			participantEntity.setProperty(Constants.PARTICIPANT_IS_RESULT_CONSULTED, true);
			datastoreService.put(participantEntity);
			
			transaction.commit();
			
			return participantResponse;
		}
		catch (EntityNotFoundException e) {
			throw new BadRequestException("{\"code\": \"" + Constants.PARTICIPANT_NOT_FOUND_ERROR_CODE + "\"}");
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}	
	}


}
