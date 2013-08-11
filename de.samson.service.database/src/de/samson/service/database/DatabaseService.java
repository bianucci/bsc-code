package de.samson.service.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;

import de.samson.service.database.entities.config.CoilConfig;
import de.samson.service.database.entities.config.CoilConfigID;
import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.RegisterConfig;
import de.samson.service.database.entities.config.RegisterConfigID;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;
import de.samson.service.database.entities.data.ReglerData;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.service.database.entities.description.GeraeteDescID;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.util.DefaultEntityFactory;

public class DatabaseService extends Observable {
	private static EntityManager em;
	private static EntityManagerFactory emf;

	static List<Standort> currentDBState = new Vector<Standort>();

	public static void start() throws Exception {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		HashMap<String, Object> props = new HashMap<String, Object>();

		props.put(PersistenceUnitProperties.CLASSLOADER,
				Activator.class.getClassLoader());

		props.put(
				PersistenceUnitProperties.JDBC_URL,
				"jdbc:mysql://" + store.getString("IP") + ":"
						+ store.getInt("PORT"));

		props.put(PersistenceUnitProperties.JDBC_PASSWORD,
				store.getString("PASSWORD"));

		props.put(PersistenceUnitProperties.JDBC_USER,
				store.getString("USERNAME"));

		Map<String, Object> props2 = new HashMap<String, Object>();
		props.put(PersistenceUnitProperties.CLASSLOADER,
				Activator.class.getClassLoader());

		PersistenceProvider persPro = new PersistenceProvider();

		try {
			emf = persPro.createEntityManagerFactory("todo", props);

			em = emf.createEntityManager(props2);

			TypedQuery<Standort> createQuery = em.createQuery(
					"Select s from Standort s", Standort.class);
			currentDBState = createQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	public static ReglerConfig getReglerConfigByID(int id) {
		return em.find(ReglerConfig.class, id);
	}

	public static List<String> getAllREvisionNrForRegler(String reglerTyp) {
		TypedQuery<String> q = em.createQuery(
				"Select s.revision from GeraeteDesc s where s.geraeteKennung ='"
						+ reglerTyp + "'", String.class);
		return q.getResultList();
	}

	public static List<Standort> getStandortList() {
		return currentDBState;
	}

	public static List<String> getAllReglerTypes() {
		TypedQuery<String> q = em.createQuery(
				"Select DISTINCT(s.geraeteTyp) from GeraeteDesc s",
				String.class);
		return q.getResultList();
	}

	public static ReglerData getReglerDataByID(int id) {
		return em.find(ReglerData.class, id);
	}

	public static List<GeraeteDesc> getAllSTR_Geraet() {
		TypedQuery<GeraeteDesc> q = em.createQuery(
				"Select s from GeraeteDesc s", GeraeteDesc.class);
		return q.getResultList();
	}

	public static Object findEntityByID(Class<?> c, Object id) {
		return em.find(c, id);
	}

	public static void addEntity(Object o) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(o);
		transaction.commit();
	}

	public static void removeEntity(Object o) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.remove(o);
		transaction.commit();
	}

	public static void persistEntity(Object o) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(o);
		transaction.commit();
	}

	public static Standort getStandortConfigByID(int id) {
		return em.find(Standort.class, id);
	}

	public static GatewayConfig getGConfigByID(int id) {
		return em.find(GatewayConfig.class, id);
	}

	public static ReglerConfig addDefaultReglerConfigToGateway(GatewayConfig gwc) {
		ReglerConfig defaultConfig = DefaultEntityFactory
				.createReglerConfig(gwc);
		persistEntity(defaultConfig);
		return defaultConfig;
	}

	public static GatewayConfig addDefaultGatewayToStandort(Standort s) {
		GatewayConfig gwc = DefaultEntityFactory.createGatewayConfig(s);
		persistEntity(gwc);
		return gwc;
	}

	public static Standort addDefaultStandort() {
		Standort s = DefaultEntityFactory.createNewStandort();
		persistEntity(s);
		return s;
	}

	public static List<RegisterConfig> addRegisterConfigsInRange(
			ReglerConfig rc, int beginHRNR, int endHRNR) {
		List<RegisterConfig> added = new ArrayList<RegisterConfig>();
		List<HRegDesc> availableRegs = rc
				.getReglerDescription().getRegisterList();

		for (int i = 0; i < availableRegs.size(); i++) {
			int regNr = availableRegs.get(i).getHrnr() - 40000;
			if ((regNr > beginHRNR) && (regNr < endHRNR)) {
				HRegDesc s = availableRegs.get(i);
				added.add(DefaultEntityFactory.createNewRegisterConfig(rc, s));
			} else if (regNr > endHRNR)
				break;
		}
		rc.getRegisterConfigs().addAll(added);
		persistEntity(rc);
		return added;
	}

	public static void updateReglerConfig(ReglerConfig rc, List<Object> toAdd,
			List<Object> toDelete, String statNR, String revNR, String reglerTyp) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		for (Object o : toAdd) {
			if (o instanceof HRegDesc) {
				RegisterConfig c = DefaultEntityFactory
						.createNewRegisterConfig(rc,
								(HRegDesc) o);
				rc.getRegisterConfigs().add(c);
			} else if (o instanceof CoilDesc) {
				CoilConfig c = DefaultEntityFactory.createNewCoilConfig(rc,
						(CoilDesc) o);
				rc.getCoilsConfigs().add(c);
			} else if (o instanceof RegisterConfig) {
				rc.getRegisterConfigs().add((RegisterConfig) o);
			} else if (o instanceof CoilConfig) {
				rc.getCoilsConfigs().add((CoilConfig) o);
			}
		}

		for (Object o : toDelete) {
			if (o instanceof HRegDesc) {
				RegisterConfig c = (RegisterConfig) findEntityByID(
						RegisterConfig.class,
						new RegisterConfigID(
								rc.getnId(),
								((HRegDesc) o).getHrnr() - 40000));
				rc.getRegisterConfigs().remove(c);
			} else if (o instanceof CoilDesc) {
				CoilConfig c = (CoilConfig) findEntityByID(
						CoilConfig.class,
						new CoilConfigID(rc.getnId(), ((CoilDesc) o)
								.getClnr()));
				rc.getCoilsConfigs().remove(c);
			} else if (o instanceof RegisterConfig) {
				rc.getRegisterConfigs().remove(o);
			} else if (o instanceof CoilConfig) {
				rc.getCoilsConfigs().remove(o);
			}
		}

		if (rc.getnDeviceid() != Integer.valueOf(statNR))
			rc.setnDeviceid(Integer.valueOf(statNR));

		if ((rc.getDescFileRevision() != revNR) || (rc.getsTyp() != reglerTyp)) {
			GeraeteDesc s = (GeraeteDesc) findEntityByID(
					GeraeteDesc.class, new GeraeteDescID(
							reglerTyp, Integer.valueOf(revNR)));

			rc.setReglerDescription(s);
			rc.setsTyp(reglerTyp);
			rc.setDescFileRevision(revNR);
		}
		transaction.commit();
	}

	public static void refreshEntity(Object o) {
		em.refresh(o);
	}

	public static void refreshData() {
		TypedQuery<Standort> createQuery = em.createQuery(
				"Select s from Standort s", Standort.class);
		currentDBState.clear();
		currentDBState.addAll(createQuery.getResultList());
		for (Standort s : currentDBState) {
			refreshEntity(s);
		}
	}

	public static List<String> getAllGatewayGroups() {
		TypedQuery<String> q = em
				.createQuery("Select DISTINCT(g.nGruppe) from GatewayConfig g",
						String.class);
		return q.getResultList();
	}

	public static void shutDown() {
		emf.close();
	}
}
