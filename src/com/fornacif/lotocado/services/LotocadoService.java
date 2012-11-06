package com.fornacif.lotocado.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fornacif.lotocado.models.Person;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Service
public class LotocadoService {
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	private final Key ancestor = KeyFactory.createKey("Ancestor", 1);

	private final List<Person> persons = Arrays.asList(
			new Person("François", "francois.fornaciari@gmail.com", 1), 
			new Person("Isabelle", "isabelle.roudotfornaciari@gmail.com", 1), 
			new Person("Mauro", "mauroforna@gmail.com", 2), 
			new Person("Marta", "marta.scherma@gmail.com", 2),
			new Person("Nicolas", "plancton9@gmail.com", 3), 
			new Person("Cyndie", "cyndie.for@gmail.com", 3), 
			new Person("Guido", "fornaciariguido@gmail.com", 4), 
			new Person("Sandra", "fornaciarisandra@gmail.com", 4));

	public boolean isUserAuthorized(String email) {
		return persons.contains(new Person(email));
	}

	public void initDatastore() throws EntityNotFoundException {
		Query personsQuery = new Query("Person").setAncestor(ancestor);
		datastore.put(createPersons());
		List<Entity> entities = datastore.prepare(personsQuery).asList(FetchOptions.Builder.withDefaults());
		defineGiftTo(entities);
	}

	private void defineGiftTo(List<Entity> entities) throws EntityNotFoundException {
		for (Entity entity : entities) {
			long couple = (Long) entity.getProperty("couple");

			Filter differentCoupleFilter = new FilterPredicate("couple", FilterOperator.NOT_EQUAL, couple);
			Filter notAlreadyChoosedFilter = new FilterPredicate("hasGift", FilterOperator.EQUAL, false);
			Filter filter = CompositeFilterOperator.and(differentCoupleFilter, notAlreadyChoosedFilter);

			Query personsQuery = new Query("Person").setAncestor(ancestor).setFilter(filter);
			List<Entity> filteredPersons = datastore.prepare(personsQuery).asList(FetchOptions.Builder.withDefaults());

			Random random = new Random();
			int index = random.nextInt(filteredPersons.size());

			Entity giftToPerson = filteredPersons.get(index);
			Entity currentEntity = datastore.get(entity.getKey());
			currentEntity.setProperty("giftTo", giftToPerson.getKey());
			datastore.put(currentEntity);

			giftToPerson.setProperty("hasGift", true);
			datastore.put(giftToPerson);
		}
	}

	public String giftTo(String email) throws EntityNotFoundException {
		Filter sameEmailFilter = new FilterPredicate("email", FilterOperator.EQUAL, email);
		Query personQuery = new Query("Person").setAncestor(ancestor).setFilter(sameEmailFilter);
		Entity connectedPerson = datastore.prepare(personQuery).asList(FetchOptions.Builder.withDefaults()).get(0);

		Key gitfToKey = (Key) connectedPerson.getProperty("giftTo");
		Entity giftToPerson = datastore.get(gitfToKey);
		String name = (String) giftToPerson.getProperty("name");
		
		return name;
	}

	private List<Entity> createPersons() {
		List<Entity> result = new ArrayList<Entity>();
		for (int i = 0; i < persons.size(); i++) {
			Entity entity = new Entity("Person", i + 1, ancestor);
			entity.setProperty("name", persons.get(i).getName());
			entity.setProperty("email", persons.get(i).getEmail());
			entity.setProperty("couple", persons.get(i).getCouple());
			entity.setProperty("hasGift", persons.get(i).getHasGift());
			result.add(entity);
		}
		return result;
	}

}
