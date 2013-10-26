package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;
import java.net.UnknownHostException;


import org.springframework.stereotype.Repository;

import com.ae.assignment.cdrproject.cdrservice.model.StatsCallPromo;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@Repository
public class RepositoryStatsCallPromoImpl extends
		AbstractRepository<StatsCallPromo, String> implements
		RepositoryStatsCallPromo, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3604751661452164760L;

	public String reportPromoCount(int lastN) {
		MongoClient mongoClient = null;
		DB db;
		try {
			mongoClient = new MongoClient(
					dbProperties
							.getProperty("eclipselink.nosql.property.mongo.host"),
					Integer.parseInt(dbProperties
							.getProperty("eclipselink.nosql.property.mongo.port")));
			db = mongoClient.getDB(dbProperties
					.getProperty("eclipselink.nosql.property.mongo.db"));
			DBCollection collection = db.getCollection(GetCollectionName());

			// Now the $group operation
			DBObject groupFields = new BasicDBObject("_id", "$RULE");
			groupFields.put("total", new BasicDBObject("$sum", 1));
			DBObject group = new BasicDBObject("$group", groupFields);

			AggregationOutput output = collection.aggregate(group);
			return JSON.serialize(output.results());


		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} finally {
			if (mongoClient != null)
				mongoClient.close();
		}
	

		/*
		 * String queryStr = String.format(
		 * "Select s.rule , COUNT(s.rule) as total from %s s group By s.rule",
		 * getEntityClass().getSimpleName());
		 * 
		 * Query query = getEm().createQuery(queryStr);
		 * query.setMaxResults(lastN);
		 */

	

	}

}
