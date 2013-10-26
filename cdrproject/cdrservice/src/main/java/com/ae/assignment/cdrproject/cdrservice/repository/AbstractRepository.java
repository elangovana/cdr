package com.ae.assignment.cdrproject.cdrservice.repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.nosql.annotations.NoSql;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@Repository
public abstract class AbstractRepository<T extends Object, indexType>
		implements RepositoryGeneric<T, indexType> {

	@Resource(name = "dbProperties")
	Properties dbProperties;

	public void setDbProperties(Properties dbProperties) {
		this.dbProperties = dbProperties;
	}

	private EntityManagerFactory getFactory() {
		if (factory == null)
			factory = Persistence.createEntityManagerFactory("cdr",
					dbProperties);
		return factory;
	}

	transient private EntityManagerFactory factory;

	private transient EntityManager em;

	protected EntityManager getEm() {
		if (em == null)
			em = getFactory().createEntityManager(dbProperties);
		return em;
	}

	protected void setEm(EntityManager em) {
		this.em = em;
	}

	private Class<T> entityClass;

	static final String ECLIPSELINK_NOSQL_PROPERTY_MONGO_HOST = "eclipselink.nosql.property.mongo.host";

	static final String ECLIPSELINK_NOSQL_PROPERTY_MONGO_PORT = "eclipselink.nosql.property.mongo.port";

	static final String ECLIPSELINK_NOSQL_PROPERTY_MONGO_DB = "eclipselink.nosql.property.mongo.db";

	protected Class<T> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	protected AbstractRepository() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass
				.getActualTypeArguments()[0];
	}

	public T findById(indexType id) {
		String queryStr;
		try {
			queryStr = String.format("Select s from %s s where s.%s =:arg1",
					entityClass.getSimpleName(), GetPrimaryKeyField());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Query query = getEm().createQuery(queryStr);
		query.setParameter("arg1", id);
		@SuppressWarnings("unchecked")
		T result = (T) query.getSingleResult();
		return result;
	}

	public T Save(T item) {
		getEm().getTransaction().begin();

		getEm().merge(item);
		getEm().flush();

		getEm().getTransaction().commit();
		return item;

	}

	String GetPrimaryKeyField() throws Exception {


		for (Field f : entityClass.getDeclaredFields()) {
			if (f.getAnnotation(Id.class) != null) {
				return f.getName();
			}
		}

		throw new Exception(String.format(
				"No @Id annotation found on any fields in class %s",
				entityClass.getName()));
	}

	public String GetCollectionName() {

		NoSql nosqlAnnotitaion = getEntityClass().getAnnotation(NoSql.class);
		return nosqlAnnotitaion.dataType();

	}

	static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		for (Field field : type.getDeclaredFields()) {
			fields.add(field);
		}

		if (type.getSuperclass() != null) {
			fields = getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}

	
	protected DB createMongoDb(MongoClient mongoClient) {
		return mongoClient.getDB(dbProperties
				.getProperty(AbstractRepository.ECLIPSELINK_NOSQL_PROPERTY_MONGO_DB));
	}

	protected MongoClient createMongoClient() throws UnknownHostException {
		return new MongoClient(
				dbProperties
						.getProperty(AbstractRepository.ECLIPSELINK_NOSQL_PROPERTY_MONGO_HOST),
				Integer.parseInt(dbProperties
						.getProperty(AbstractRepository.ECLIPSELINK_NOSQL_PROPERTY_MONGO_PORT)));
	}
}
