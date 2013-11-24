package com.fornacif.lotocado.service;

import static com.fornacif.lotocado.helper.EmailHelper.getOrganizerLink;
import static com.fornacif.lotocado.helper.EmailHelper.sendEmailToOrganizer;
import static com.fornacif.lotocado.helper.EmailHelper.sendEmailToParticipant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import com.fornacif.lotocado.model.DrawingLotsRequest;
import com.fornacif.lotocado.model.DrawingLotsResponse;
import com.fornacif.lotocado.model.Event;
import com.fornacif.lotocado.model.Participant;
import com.fornacif.lotocado.utils.Constants;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

@Api(name = "lotocado", version = "v2")
public class RandomMatcher {

	private static final int MAX_RETRY_COUNT = 10000;

	private final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	
	private final Logger LOGGER = Logger.getLogger(RandomMatcher.class.getName());

	public DrawingLotsResponse createDrawingLots(DrawingLotsRequest drawingLotsRequest) throws BadRequestException {
		Event event = drawingLotsRequest.getEvent();
		List<Participant> participants = drawingLotsRequest.getParticipants();

		Transaction transaction = datastoreService.beginTransaction();
		try {
			saveEvent(event);
			saveParticipants(event.getKey(), participants);

			boolean performRandom = true;
			int retryCount = 0;
			while (performRandom && retryCount < MAX_RETRY_COUNT) {
				performRandom = randomize(participants);
				retryCount++;
			}

			if (retryCount != MAX_RETRY_COUNT) {
				try {
					saveResults(participants);
				} catch (EntityNotFoundException e) {
					throw new BadRequestException("{\"code\": \"" + Constants.NOT_PERSISTED_ERROR_CODE + "\"}");
				}

				try {
					sendEmailToOrganizer(event);
				} catch (MessagingException | IOException e) {
					throw new BadRequestException("{\"code\": \"" + Constants.SEND_MAIL_TO_ORGANIZER_ERROR_CODE + "\"}");
				}

				for (Participant participant : participants) {
					try {
						sendEmailToParticipant(event, participant);
					} catch (MessagingException | IOException e) {
						throw new BadRequestException("{\"code\": \"" + Constants.SEND_MAIL_TO_PARTICIPANT_ERROR_CODE + "\",\"participantName\":" + participant.getName() + "}");
					}
				}

				transaction.commit();
				
				DrawingLotsResponse response = new DrawingLotsResponse();
				response.setOrganizerLink(getOrganizerLink(event));
				return response;
			} else {
				throw new BadRequestException("{\"code\": \"" + Constants.NO_RESULT_ERROR_CODE + "\"}");
			}
		}
		catch (BadRequestException e) {
			throw e;
		} catch (Throwable t) {
			throw new BadRequestException("{\"code\": \"" + Constants.DATA_ERROR_CODE + "\"}");
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	private void saveEvent(Event event) {
		Entity eventEntity = new Entity(Constants.EVENT_ENTITY);
		eventEntity.setProperty(Constants.EVENT_NAME, event.getName());
		eventEntity.setProperty(Constants.EVENT_ORGANIZER_NAME, event.getOrganizerName());
		eventEntity.setProperty(Constants.EVENT_ORGANIZER_EMAIL, event.getOrganizerEmail());
		eventEntity.setProperty(Constants.EVENT_DATE, event.getDate());
		datastoreService.put(eventEntity);
		event.setKey(eventEntity.getKey());
	}

	private void saveParticipants(Key eventKey, List<Participant> participants) {
		Map<String, Key> hashKeyToKey = new HashMap<>();

		for (Participant participant : participants) {
			Entity participantEntity = new Entity(Constants.PARTICIPANT_ENTITY, eventKey);
			participantEntity.setProperty(Constants.PARTICIPANT_EMAIL, participant.getEmail());
			participantEntity.setProperty(Constants.PARTICIPANT_NAME, participant.getName());
			participantEntity.setProperty(Constants.PARTICIPANT_IS_RESULT_CONSULTED, participant.isResultConsulted());
			participantEntity.setProperty(Constants.PARTICIPANT_EVENT_KEY, eventKey);
			datastoreService.put(participantEntity);

			Key key = participantEntity.getKey();
			participant.setKey(key);
			participant.setEntity(participantEntity);
			
			hashKeyToKey.put(participant.getHashKey(), key);
		}

		for (Participant participant : participants) {
			List<String> exclusionHashKeys = participant.getExclusionHashKeys();
			List<Key> exclusionKeys = new ArrayList<>();

			for (String exclusionHashKey : exclusionHashKeys) {
				exclusionKeys.add(hashKeyToKey.get(exclusionHashKey));
			}
			participant.setExclusionKeys(exclusionKeys);
		}
	}

	private void saveResults(List<Participant> participants) throws EntityNotFoundException {
		for (Participant participant : participants) {
			Entity participantEntity = participant.getEntity();
			participantEntity.setProperty(Constants.PARTICIPANT_TO_KEY, participant.getToKey());
			participantEntity.setProperty(Constants.PARTICIPANT_TO_NAME, participant.getToName());
			participantEntity.setProperty(Constants.PARTICIPANT_EXCLUSION_KEYS, participant.getExclusionKeys());
			datastoreService.put(participantEntity);
		}
	}

	private void resetDrawingLots(List<Participant> participants) {
		for (Participant participant : participants) {
			participant.setToKey(null);
			participant.setToName(null);
			participant.setFromKey(null);
		}
	}

	private boolean randomize(List<Participant> participants) {
		Random random = new Random();

		for (Participant currentParticipant : participants) {
			List<Participant> recievers = new ArrayList<Participant>();
			for (Participant reciever : participants) {
				if (!currentParticipant.equals(reciever) && !currentParticipant.getExclusionKeys().contains(reciever.getKey()) && reciever.getFromKey() == null) {
					recievers.add(reciever);
				}
			}

			if (recievers.size() != 0) {
				int receiverPosition = random.nextInt(recievers.size());
				Participant reciever = recievers.get(receiverPosition);
				currentParticipant.setToKey(reciever.getEntity().getKey());
				currentParticipant.setToName(reciever.getName());
				reciever.setFromKey(currentParticipant.getKey());
			} else {
				resetDrawingLots(participants);
				return true;
			}
		}

		return false;
	}

}
