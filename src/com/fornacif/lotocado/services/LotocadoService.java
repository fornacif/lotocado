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
import com.google.appengine.api.datastore.Transaction;

@Service
public class LotocadoService {
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	private final Key ancestor = KeyFactory.createKey("Ancestor", 1);

	private final List<Person> persons = Arrays.asList(
			new Person("François", "francois.fornaciari@gmail.com", 1), 
			new Person("Isabelle", "isabelle.roudotfornaciari@gmail.com", 1), 
			new Person("Mauro", "mauroforna@gmail.com", 2), 
			new Person("Marta", "marta.scherma@gmail.com", 2));

	public boolean isUserAuthorized(String email) {
		return persons.contains(new Person(email));
	}

	public void initDatastore() {
		Transaction transaction = datastore.beginTransaction();
		datastore.put(createPersons());
		transaction.commit();
	}

	public String giftTo(String email) throws EntityNotFoundException {
		Transaction transaction = datastore.beginTransaction();

		Filter sameEmailFilter = new FilterPredicate("email", FilterOperator.EQUAL, email);
		Query personQuery = new Query("Person").setAncestor(ancestor).setFilter(sameEmailFilter);
		Entity connectedPerson = datastore.prepare(transaction, personQuery).asList(FetchOptions.Builder.withDefaults()).get(0);

		Key gitfToKey = (Key) connectedPerson.getProperty("giftTo");

		Entity giftToPerson = null;

		if (gitfToKey != null) {
			giftToPerson = datastore.get(gitfToKey);

		} else {
			long couple = (Long) connectedPerson.getProperty("couple");

			Filter differentCoupleFilter = new FilterPredicate("couple", FilterOperator.NOT_EQUAL, couple);
			Filter notAlreadyChoosedFilter = new FilterPredicate("hasGift", FilterOperator.EQUAL, false);
			Filter filter = CompositeFilterOperator.and(differentCoupleFilter, notAlreadyChoosedFilter);

			Query personsQuery = new Query("Person").setAncestor(ancestor).setFilter(filter);
			List<Entity> persons = datastore.prepare(transaction, personsQuery).asList(FetchOptions.Builder.withDefaults());

			Random random = new Random();
			int index = random.nextInt(persons.size());

			giftToPerson = persons.get(index);
			connectedPerson.setProperty("giftTo", giftToPerson.getKey());
			datastore.put(transaction, connectedPerson);
			
			giftToPerson.setProperty("hasGift", true);
			datastore.put(transaction, giftToPerson);
		}

		String name = (String) giftToPerson.getProperty("name");
		transaction.commit();

		return name;
	}

	private List<Entity> createPersons() {
		List<Entity> result = new ArrayList<Entity>();
		for (int i = 1; i <= persons.size(); i++) {
			Entity entity = new Entity("Person", i, ancestor);
			entity.setProperty("name", persons.get(i - 1).getName());
			entity.setProperty("email", persons.get(i - 1).getEmail());
			entity.setProperty("couple", persons.get(i - 1).getCouple());
			entity.setProperty("hasGift", false);
			result.add(entity);
		}
		return result;
	}

}
