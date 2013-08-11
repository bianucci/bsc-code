package de.samson.dataviewer.editor.coiltv;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.util.DataConverterUtil;

public class CoilTableViewerFactory {

	private static CoilValueEditingSupport editingSupport;

	public static TableViewer createTV(Composite parent) {
		TableViewer tv = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		createColumns(tv);
		
		tv.getTable().setHeaderVisible(true);
		tv.getTable().setLinesVisible(true);
		tv.setContentProvider(new CoilTableContentProvider());
		return tv;
	}

	private static void createColumns(TableViewer tv) {
		TableViewerColumn c = addTVColumn(tv, 30, "#");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				int clnr = ((CoilData) element).getnCoilnr();
				return String.valueOf(clnr);
			}
		});

		c = addTVColumn(tv, 150, "Bezeichnung");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CoilData cd = (CoilData) element;
				CoilDesc sc = DataConverterUtil.getCoilDescForData(cd);
				if (sc != null)
					return sc.getBezeichnung();
				else
					return "Error";
			}
		});

		c = addTVColumn(tv, 150, "Wert");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CoilData cd = (CoilData) element;
				CoilDesc sc = DataConverterUtil.getCoilDescForData(cd);

				if (cd.getWert())
					return sc.getText1();
				else
					return sc.getText0();
			}
		});
		editingSupport = new CoilValueEditingSupport(tv);
		c.setEditingSupport(editingSupport);
		
		c = addTVColumn(tv, 150, "Kommentar");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CoilData cd = (CoilData) element;
				CoilDesc sc = DataConverterUtil.getCoilDescForData(cd);
				if (sc != null)
					return sc.getKommentar();
				else
					return "Error";
			}
		});
		
		c = addTVColumn(tv, 150, "Kategorie");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CoilData cd = (CoilData) element;
				CoilDesc sc = DataConverterUtil.getCoilDescForData(cd);
				if (sc != null)
					return sc.getAnzKategorie();
				else
					return "Error";
			}
		});
		
		c = addTVColumn(tv, 150, "Bezeichnung");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CoilData cd = (CoilData) element;
				CoilDesc sc = DataConverterUtil.getCoilDescForData(cd);
				if (sc != null)
					return sc.getBezeichnung();
				else
					return "Error";
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
