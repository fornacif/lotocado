package com.fornacif.lotocado.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fornacif.lotocado.utils.Constants;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public final class ExclusionHelper {
	
	@SuppressWarnings("unchecked")
	public static List<String> getExcludedNames(DatastoreService datastoreService, Entity participantEntity) throws EntityNotFoundException {
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
