package com.springmvc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDAO<PK extends Serializable, T> {
	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@Autowired
	private SessionFactory sf;
	protected Session getSession() {
		return sf.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}
	public void persist(T entity) {
		getSession().persist(entity);
	}
	public void delete(T entity) {
		getSession().delete(entity);
	}
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}
}