package de.samson.service.database.impl;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import de.samson.service.database.entities.description.STR_Geraet;

public class EntityListTest {

	@Test
	public void testListNotEmpty() {
		final String PERSISTENCE_UNIT_NAME = "todo";
		EntityManagerFactory factory;
		EntityManager em;

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();

		EntityList<STR_Geraet> e = new EntityList<>(em, "STR_Geraet");
		assertTrue(e.size() > 0);
		System.out.println(e);
	}

	@Test
	public void testClear() {
	}

	@Test
	public void testEntityList() {
	}

	@Test
	public void testAddE() {
	}

	@Test
	public void testRemoveInt() {
	}

}
