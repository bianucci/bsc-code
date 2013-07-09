package de.samson.service.database.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class EntityList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -8777089895647172819L;

	EntityManager em;

	@SuppressWarnings("unchecked")
	public EntityList(EntityManager em, String clazz) {
		super();
		this.em = em;
		Query q = em.createQuery("Select e from " + clazz + " e");
		this.addAll(q.getResultList());
	}

	@Override
	public boolean add(E e) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(e);
		t.commit();
		return super.add(e);
	}

	@Override
	public E remove(int index) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(get(index));
		t.commit();
		return super.remove(index);
	}

	@Override
	public void clear() {
		EntityTransaction t = em.getTransaction();
		t.begin();
		for (Object o : this) {
			em.remove(o);
		}
		t.commit();
		super.clear();
	}

}
