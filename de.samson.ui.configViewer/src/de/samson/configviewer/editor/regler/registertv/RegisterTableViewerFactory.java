package de.samson.configviewer.editor.regler.registertv;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.samson.service.database.entities.description.HoldingRegiterDescription;

public class RegisterTableViewerFactory {

	public static CheckboxTableViewer createTV(Composite parent) {
		CheckboxTableViewer tv = new CheckboxTableViewer(new Table(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION
						| SWT.CHECK));
		createColumns(tv);
		tv.getTable().setHeaderVisible(true);
		tv.getTable().setLinesVisible(true);
		tv.setContentProvider(new RegTableContentProvider());
		return tv;
	}

	private static void createColumns(TableViewer tv) {

		TableViewerColumn c = addTVColumn(tv, 30, "");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}
		});

		c = addTVColumn(tv, 50, "#");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				int hrnr = ((HoldingRegiterDescription) element).getHrnr();
				return String.valueOf(hrnr);
			}
		});

		c = addTVColumn(tv, 150, "Bezeichnung");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((HoldingRegiterDescription) element).getBezeichnung();
			}
		});

		c = addTVColumn(tv, 90, "Kategorie");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((HoldingRegiterDescription) element).getAnzKategorie();
			}
		});

		c = addTVColumn(tv, 300, "Kommentar");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((HoldingRegiterDescription) element).getKommentar();
			}
		});
		
		c = addTVColumn(tv, 50, "RO");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return String.valueOf(((HoldingRegiterDescription) element).isRo());
			}
		});
	}

	public static TableViewerColumn addTVColumn(TableViewer tv, int size,
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
