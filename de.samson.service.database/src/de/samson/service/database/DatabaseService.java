package de.samson.service.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.RegisterDataID;
import de.samson.service.database.entities.data.ReglerData;
import de.samson.service.database.entities.data.WmwData;
import de.samson.service.database.entities.data.WmzData;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.entities.description.CoilDescID;
import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.service.database.entities.description.GeraeteDescID;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.entities.description.HRegDescID;
import de.samson.service.database.entities.description.WmwDesc;
import de.samson.service.database.entities.description.WmzDesc;
import de.samson.service.database.entities.histdata.CoilDataSource;
import de.samson.service.database.entities.histdata.HRegDataSource;
import de.samson.service.database.entities.histdata.HistDataSet;
import de.samson.service.database.entities.histdata.HistValue;
import de.samson.service.database.entities.histdata.WmwDataSource;
import de.samson.service.database.ientities.histdata.HistDataSource;
import de.samson.service.database.util.DefaultEntityFactory;

public class DatabaseService extends Observable {
	private static EntityManager em;
	private static EntityManagerFactory emf;

	static List<Standort> currentDBState = new Vector<Standort>();
	private static Map<String, Object> props2;

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

		props2 = new HashMap<String, Object>();
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
		List<HRegDesc> availableRegs = rc.getReglerDescription()
				.getRegisterList();

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

	private static void addRegisterConfigWithDataForReglerConfig(HRegDesc desc,
			ReglerConfig rc) {

		RegisterConfig c = DefaultEntityFactory.createNewRegisterConfig(rc,
				desc);
		rc.getRegisterConfigs().add(c);

		RegisterData d = DefaultEntityFactory.createNewRegisterData(c);
		rc.getReglerData().getRegisterData().add(d);

		c.setData(d);
		d.setConfig(c);
		persistEntity(c);
	}

	private static void addCoilConfigWithDataForReglerConfig(CoilDesc desc,
			ReglerConfig rc) {

		CoilConfig c = DefaultEntityFactory.createNewCoilConfig(rc, desc);
		rc.getCoilsConfigs().add(c);

		CoilData d = DefaultEntityFactory.createNewCoilData(c);
		rc.getReglerData().getCoilsData().add(d);

		c.setData(d);
		d.setConfig(c);
		persistEntity(c);
	}

	public static void updateReglerConfig(ReglerConfig rc, List<Object> toAdd,
			List<Object> toDelete, String statNR, String revNR, String reglerTyp) {

		refreshEntity(rc);

		List<WmwData> wmwDataAdded = new ArrayList<WmwData>();

		for (Object o : toAdd) {
			if (o instanceof HRegDesc) {

				addRegisterConfigWithDataForReglerConfig((HRegDesc) o, rc);
				if (((HRegDesc) o).hasWmwDesc()) {

					RegisterData rd = em.find(
							RegisterData.class,
							new RegisterDataID(rc.getnId(), ((HRegDesc) o)
									.getHrnr() - 40000));

					WmzData wmz = findWmzAndCreateIfNotExisting(rd,
							((HRegDesc) o).getLinkedWmwDesc());

					WmwData wmw = findWmwAndCreateIfNotExisting(
							((HRegDesc) o).getLinkedWmwDesc(), wmz);
					if (!wmwDataAdded.contains(wmw))
						wmwDataAdded.add(wmw);
				}

			} else if (o instanceof CoilDesc) {
				addCoilConfigWithDataForReglerConfig((CoilDesc) o, rc);
			} else if (o instanceof RegisterConfig) {

				HRegDescID id = new HRegDescID();
				id.setGeraeteKennung(String.valueOf(rc.getnDeviceid()));
				id.setHrnr(((RegisterConfig) o).getnRegisternr() + 40000);
				id.setRevision(Integer.valueOf(((RegisterConfig) o)
						.getReglerConfig().getDescFileRevision()));
				HRegDesc desc = (HRegDesc) findEntityByID(HRegDesc.class, id);

				rc.getRegisterConfigs().add((RegisterConfig) o);
				persistEntity(rc);

				if (desc.hasWmwDesc()) {
					RegisterData rd = em.find(
							RegisterData.class,
							new RegisterDataID(rc.getnId(), ((HRegDesc) o)
									.getHrnr() - 40000));

					WmzData wmz = findWmzAndCreateIfNotExisting(rd,
							((HRegDesc) o).getLinkedWmwDesc());

					WmwData wmw = findWmwAndCreateIfNotExisting(
							((HRegDesc) o).getLinkedWmwDesc(), wmz);
					if (!wmwDataAdded.contains(wmw))
						wmwDataAdded.add(wmw);
				}

			} else if (o instanceof CoilConfig) {
				rc.getCoilsConfigs().add((CoilConfig) o);
				persistEntity(rc);
			}
		}

		for (int i = 0; i < wmwDataAdded.size(); i++) {
			setDataRegister(wmwDataAdded.get(i));
		}

		for (Object o : toDelete) {
			if (o instanceof HRegDesc) {
				RegisterConfig c = (RegisterConfig) findEntityByID(
						RegisterConfig.class, new RegisterConfigID(rc.getnId(),
								((HRegDesc) o).getHrnr() - 40000));
				if (c != null) {
					removeEntity(c);
					if (c.getData().getDataSource() != null) {
						removeEntity(c.getData().getDataSource());
					}
				}

			} else if (o instanceof CoilDesc) {
				CoilConfig c = (CoilConfig) findEntityByID(CoilConfig.class,
						new CoilConfigID(rc.getnId(), ((CoilDesc) o).getClnr()));
				if (c != null) {
					removeEntity(c);
					if (c.getData().getDataSource() != null) {
						removeEntity(c.getData().getDataSource());
					}
				}

			} else if (o instanceof RegisterConfig) {
				removeEntity(o);
				if (((RegisterConfig) o).getData().getDataSource() != null) {
					removeEntity(((RegisterConfig) o).getData().getDataSource());
				}

			} else if (o instanceof CoilConfig) {
				removeEntity(o);
				if (((CoilConfig) o).getData().getDataSource() != null) {
					removeEntity(((CoilConfig) o).getData().getDataSource());
				}
			}
			refreshEntity(rc);
		}

		if (rc.getnDeviceid() != Integer.valueOf(statNR)) {
			rc.setnDeviceid(Integer.valueOf(statNR));
			persistEntity(rc);
		}

		if (!(rc.getDescFileRevision().equals(revNR))
				|| !(rc.getsTyp().equals(reglerTyp))) {
			GeraeteDesc s = (GeraeteDesc) findEntityByID(GeraeteDesc.class,
					new GeraeteDescID(reglerTyp, Integer.valueOf(revNR)));

			rc.setReglerDescription(s);
			rc.setsTyp(reglerTyp);
			rc.setDescFileRevision(revNR);
			persistEntity(rc);
		}
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

	public static void addNewDataSourceForHoldingReg(RegisterData rd) {
		// Set description for register datasource
		HRegDescID id = new HRegDescID();
		id.setGeraeteKennung(rd.getReglerData().getReglerConfig().getsTyp());
		id.setHrnr(rd.getnRegisternr() + 40000);
		id.setRevision(Integer.valueOf(rd.getReglerData().getReglerConfig()
				.getDescFileRevision()));
		HRegDesc desc = (HRegDesc) findEntityByID(HRegDesc.class, id);
		HRegDataSource ds = DefaultEntityFactory.createNewHRegDataSource(desc);

		// establish references between datasource and register data
		rd.setDataSource(ds);
		// ds.setData(rd); causes duplicate entry exceptions

		persistEntity(ds);

		// create default hist value for later comparison
		ds.addHistVal(DefaultEntityFactory.createNewHistValue(ds));

		// store the new Entity instances
		persistEntity(rd);
	}

	public static void addNewDataSourceForCoil(CoilData cd) {
		// Set description for coil datasource
		CoilDescID id = new CoilDescID();
		id.setGeraeteKennung(cd.getReglerData().getReglerConfig().getsTyp());
		id.setClnr(cd.getnCoilnr());
		id.setRevision(Integer.valueOf(cd.getReglerData().getReglerConfig()
				.getDescFileRevision()));
		CoilDesc desc = (CoilDesc) findEntityByID(CoilDesc.class, id);
		CoilDataSource ds = DefaultEntityFactory.createNewCoilDataSource(desc);

		// establish references between datasource and coil data
		cd.setDataSource(ds);
		// ds.setData(cd);causes duplicate entry exceptions

		// create default historical value for later comparison
		ds.addHistVal(DefaultEntityFactory.createNewHistValue(ds));

		// store the new Entity instances
		persistEntity(cd);
	}

	public static void addNewDataSourceForWMW(RegisterData rd, WmwDesc wmwDesc) {
		// create WMZ DATA if not available
		WmzData wmz = findWmzAndCreateIfNotExisting(rd, wmwDesc);

		// create WMW Data and set data regsiter
		WmwData data = findWmwAndCreateIfNotExisting(wmwDesc, wmz);
		// setDataRegister(data);

		// create datasource with default histval
		WmwDataSource ds = DefaultEntityFactory.createNewWmwDataSource(wmwDesc,
				data);
		ds.addHistVal(DefaultEntityFactory.createNewHistValue(ds));
		data.setDataSource(ds);
		persistEntity(data);
		return;
	}

	private static WmwData setDataRegister(WmwData data) {
		HRegDesc registerEinheitIsStoredIn = data.getDescription()
				.getRegisterEinheitIsStoredIn();
		List<HRegDesc> werteRegister = data.getDescription().getWerteRegister();

		RegisterDataID id = new RegisterDataID(data.getWmz().getReglerConfig()
				.getnId(), registerEinheitIsStoredIn.getHrnr() - 40000);
		RegisterData registerData = (RegisterData) findEntityByID(
				RegisterData.class, id);
		if (registerData != null)
			registerData.setWmw(data);

		for (int l = 0; l < werteRegister.size(); l++) {
			id = new RegisterDataID(data.getWmz().getReglerConfig().getnId(),
					werteRegister.get(l).getHrnr() - 40000);
			registerData = (RegisterData) findEntityByID(RegisterData.class, id);
			if (registerData != null)
				registerData.setWmw(data);
		}
		persistEntity(data);
		return data;
	}

	private static WmzData findWmzAndCreateIfNotExisting(RegisterData rd,
			WmwDesc wmwDesc) {
		WmzDesc wmzDesc = wmwDesc.getWmz();
		ReglerConfig reglerConfig = rd.getReglerData().getReglerConfig();
		Query q = em
				.createQuery("SELECT wmz FROM WmzData wmz WHERE "
						+ "wmz.reglerConfig = :reglerConf AND wmz.description = :wmzDesc");
		q.setParameter("reglerConf", reglerConfig);
		q.setParameter("wmzDesc", wmzDesc);
		WmzData wmz = null;
		if (q.getResultList().size() == 0) {
			wmz = new WmzData();
			wmz.setAllWMW(new ArrayList<WmwData>());
			wmz.setDescription(wmzDesc);
			wmz.setReglerConfig(reglerConfig);
			persistEntity(wmz);
		} else if (q.getResultList().size() == 1) {
			wmz = (WmzData) q.getSingleResult();
		}
		return wmz;
	}

	private static WmwData findWmwAndCreateIfNotExisting(WmwDesc wmwDesc,
			WmzData wmz) {

		Query q = em.createQuery("SELECT wmw FROM WmwData wmw WHERE "
				+ "wmw.description = :description AND wmw.wmz = :wmzData");
		q.setParameter("description", wmwDesc);
		q.setParameter("wmzData", wmz);

		WmwData data = null;
		if (q.getResultList().size() == 0) {
			data = new WmwData();
			data.setDescription(wmwDesc);
			data.setEinheit(0);
			data.setRd(new ArrayList<RegisterData>());
			data.setValue(0);
			data.setWmz(wmz);
			persistEntity(data);
		} else if (q.getResultList().size() == 1) {
			data = (WmwData) q.getSingleResult();
		}
		return data;
	}

	public static List<HistDataSource> getAllDataSources() {
		// EntityManager tempEM = emf.createEntityManager(props2);
		TypedQuery<HistDataSource> q = em.createQuery(
				"Select s from HistDataSource s", HistDataSource.class);
		return q.getResultList();
	}

	public static HistDataSet getHistDataSetByID(int id) {
		return (HistDataSet) findEntityByID(HistDataSet.class, id);

	}

	public static HistDataSource getDataSourceByID(int id) {
		return (HistDataSource) findEntityByID(HistDataSource.class, id);
	}

	public static void addHistValue(HistDataSource source) {
		HistValue newHistVal = new HistValue();

		newHistVal.setData_source(source);
		newHistVal.setRec_time(new Date(System.currentTimeMillis()));

		newHistVal.setValue(source.getCurrentValue());
		source.getHistoricalValues().add(newHistVal);

		System.out.println("New hist value " + newHistVal.getValue()
				+ " added for HistDataSource " + source.getId());
	}

	public static void removeWmwDataSource(WmwData wmwData) {
		WmwDataSource dataSource = wmwData.getDataSource();
		removeEntity(dataSource);
		refreshEntity(wmwData);
	}
}
