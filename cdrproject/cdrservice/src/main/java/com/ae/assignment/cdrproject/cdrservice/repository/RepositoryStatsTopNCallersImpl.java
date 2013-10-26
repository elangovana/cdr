package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.ae.assignment.cdrproject.cdrservice.model.StatsTopNCallers;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@Repository
public class RepositoryStatsTopNCallersImpl extends AbstractRepository<StatsTopNCallers, String> implements
		RepositoryStatsTopNCallers  , Serializable {

	private static final String TOTAL_CALLS_MADE = "totalCallsMade";
	/**
	 * 
	 */
	private static final long serialVersionUID = -1268307904315898596L;
	



	@Override
	public String reportTopCallers(Date fromDate, Date ToDate) {
		MongoClient mongoClient = null;
		DB db;
		try {
			mongoClient = createMongoClient();
			db = createMongoDb(mongoClient);
			DBCollection collection = db.getCollection(GetCollectionName());
			
			DBObject dboltDate = new BasicDBObject("$lte", ToDate);
			DBObject dbogtDate = new BasicDBObject("$gte", fromDate);
			DBObject dboUpdateddateRange = new BasicDBObject();
			dboUpdateddateRange.put("LASTUPDATEDON", dboltDate);
			dboUpdateddateRange.put("LASTUPDATEDON", dbogtDate);
			
			DBObject dboMatch = new BasicDBObject("$match" , dboUpdateddateRange); 
		
			
			// { $match : { score : { $gt : 70, $lte : 90 } } }

			// Now the $group operation
			DBObject groupFields = new BasicDBObject("_id", "$CALLINGNUMBER");
			groupFields.put(TOTAL_CALLS_MADE, new BasicDBObject("$sum", "$TOTALCALLSMADE"));
			DBObject group = new BasicDBObject("$group", groupFields);

			AggregationOutput output = collection.aggregate(dboMatch,group);
			return JSON.serialize(output.results());


		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} finally {
			if (mongoClient != null)
				mongoClient.close();
		}
	}




	@Override
	public String reportTopCallers(Date fromDate, Date ToDate, int topN) {
		MongoClient mongoClient = null;
		DB db;
		try {
			mongoClient = createMongoClient();
			db = createMongoDb(mongoClient);
			DBCollection collection = db.getCollection(GetCollectionName());
			
			DBObject dboltDate = new BasicDBObject("$lte", ToDate);
			DBObject dbogtDate = new BasicDBObject("$gte", fromDate);
			DBObject dboUpdateddateRange = new BasicDBObject();
			dboUpdateddateRange.put("LASTUPDATEDON", dboltDate);
			dboUpdateddateRange.put("LASTUPDATEDON", dbogtDate);
			
			DBObject dboMatch = new BasicDBObject("$match" , dboUpdateddateRange); 
		
			
			// { $match : { score : { $gt : 70, $lte : 90 } } }

			// Now the $group operation
			DBObject groupFields = new BasicDBObject("_id", "$CALLINGNUMBER");
			groupFields.put(TOTAL_CALLS_MADE, new BasicDBObject("$sum", "$TOTALCALLSMADE"));
			DBObject group = new BasicDBObject("$group", groupFields);
			
			DBObject sortFields = new BasicDBObject("$sort", new BasicDBObject(TOTAL_CALLS_MADE, -1));
			
			DBObject limitResults = new BasicDBObject("$limit", topN);
			

			AggregationOutput output = collection.aggregate(dboMatch,group, sortFields,limitResults);
			return JSON.serialize(output.results());


		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} finally {
			if (mongoClient != null)
				mongoClient.close();
		}
	}

}
