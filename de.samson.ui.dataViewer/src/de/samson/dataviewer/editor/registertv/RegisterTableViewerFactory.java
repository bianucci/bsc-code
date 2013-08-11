package de.samson.dataviewer.editor.registertv;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;

import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.util.DataConverterUtil;

public class RegisterTableViewerFactory {

	private static TableViewer tv;

	public static TableViewer createTV(Group g) {
		tv = new TableViewer(g, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.FULL_SELECTION);

		tv.getTable().setHeaderVisible(true);
		tv.getTable().setLinesVisible(true);
		tv.setContentProvider(new RegTableContentProvider());

		createColumns(tv);

		return tv;
	}

	private static void createColumns(TableViewer tv) {

		TableViewerColumn c = addTVColumn(tv, 50, "#");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				int hrnr = ((RegisterData) element).getnRegisternr() + 40000;
				return String.valueOf(hrnr);
			}
		});

		c = addTVColumn(tv, 150, "Bezeichnung");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);
				if (sr != null)
					return sr.getBezeichnung();
				else
					return "ERROR";
			}
		});

		c = addTVColumn(tv, 150, "Wert");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);

				double s = sr.getSkalierungsfaktor();
				if (s == 1) {
					return String.valueOf((int) rd.getsWert());
				} else {
					String v = String.valueOf(rd.getsWert() / s);

					String[] split = v.split("\\.");
					if ((s == 100) && (split[1].length() < 2))
						v += "0";

					else if ((s == 1000) && (split[1].length() < 3))
						for(int i=0;i<3-split[1].length();i++)
							v+="=";
					return v;
				}
			}
		});
		c.setEditingSupport(new RegValueEditingSupport(tv));

		c = addTVColumn(tv, 50, "Einheit");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);
				return String.valueOf(sr.getEinheit());
			}
		});

		c = addTVColumn(tv, 150, "Kategorie");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);
				return String.valueOf(sr.getAnzKategorie());
			}
		});

		c = addTVColumn(tv, 150, "Kommentar");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);
				return String.valueOf(sr.getKommentar());
			}
		});

		c = addTVColumn(tv, 50, "Min");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);
				return String.valueOf(sr.getaBerAnfang());
			}
		});

		c = addTVColumn(tv, 50, "Max");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);
				return String.valueOf(sr.getaBerEnde());
			}
		});

		c = addTVColumn(tv, 50, "RO");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				RegisterData rd = (RegisterData) element;
				HRegDesc sr = DataConverterUtil
						.getRegisterDescForData(rd);
				return String.valueOf(sr.isRo());
			}
		});
	}

	private static TableViewerColumn addTVColumn(TableViewer tv, int size,
			String title) {
		TableViewerColumn tvc = new TableViewerColumn(tv, SWT.None);
		TableColumn column = tvc.getColumn();
		column.setText(title);
		column.setWidth(size);
		column.setMoveable(true);
		column.setResizable(true);
		return tvc;
	}

}
