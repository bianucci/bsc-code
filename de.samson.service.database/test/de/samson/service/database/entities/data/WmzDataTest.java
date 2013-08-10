package de.samson.service.database.entities.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.junit.Before;
import org.junit.Test;

import de.samson.service.database.Activator;
import de.samson.service.database.entities.config.Standort;

public class WmzDataTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private List<Standort> currentDBState;

	@Before
	public void startDBConenction() {

		HashMap<String, Object> props = new HashMap<String, Object>();

		// props.put(PersistenceUnitProperties.CLASSLOADER,
		// Activator.class.getClassLoader());

		props.put(PersistenceUnitProperties.JDBC_URL,
				"jdbc:mysql://127.0.0.1:3306");
		props.put(PersistenceUnitProperties.JDBC_PASSWORD, "");
		props.put(PersistenceUnitProperties.JDBC_USER, "root");

		// Map<String, Object> props2 = new HashMap<String, Object>();
		// props.put(PersistenceUnitProperties.CLASSLOADER,
		// Activator.class.getClassLoader());

		PersistenceProvider persPro = new PersistenceProvider();

		try {
			emf = persPro.createEntityManagerFactory("todo", props);

			// em = emf.createEntityManager(props2);

			TypedQuery<Standort> createQuery = em.createQuery(
					"Select s from Standort s", Standort.class);
			currentDBState = createQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {

	}

}
