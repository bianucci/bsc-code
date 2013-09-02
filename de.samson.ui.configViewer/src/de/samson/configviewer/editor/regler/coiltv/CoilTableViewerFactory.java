package de.samson.configviewer.editor.regler.coiltv;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.samson.configviewer.view.View;
import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.CoilDataID;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.ientities.histdata.HistDataSource;

public class CoilTableViewerFactory {

	public static class CheckBoxEditingSupport extends EditingSupport {

		TableViewer viewer;
		ReglerConfig rc;

		public CheckBoxEditingSupport(TableViewer viewer, ReglerConfig rc) {
			super(viewer);
			this.viewer = viewer;
			this.rc = rc;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return new CheckboxCellEditor(viewer.getTable(), SWT.CHECK
					| SWT.READ_ONLY);
		}

		@Override
		protected boolean canEdit(Object element) {
			return rc != null;
		}

		@Override
		protected Object getValue(Object element) {
			CoilDesc desc = (CoilDesc) element;
			CoilDataID id = new CoilDataID(rc.getnId(), desc.getClnr());
			CoilData cd = (CoilData) DatabaseService.findEntityByID(
					CoilData.class, id);
			if (cd != null) {
				return cd.getDataSource() != null;
			} else
				return false;
		}

		@Override
		protected void setValue(Object element, Object value) {
			CoilDesc desc = (CoilDesc) element;
			CoilDataID id = new CoilDataID(rc.getnId(), desc.getClnr());
			CoilData cd = (CoilData) DatabaseService.findEntityByID(
					CoilData.class, id);
			if (cd != null)
				if (cd.getDataSource() == null) {
					DatabaseService.addNewDataSourceForCoil(cd);
				} else {
					HistDataSource dataSource = cd.getDataSource();
					cd.setDataSource(null);
					DatabaseService.removeEntity(dataSource);
				}
			viewer.refresh();
		}

	}

	private static final Image CHECKED = getImage("checked.gif");
	private static final Image UNCHECKED = getImage("unchecked.gif");

	public static CheckboxTableViewer createTV(Composite parent, ReglerConfig rc) {
		CheckboxTableViewer tv = new CheckboxTableViewer(new Table(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION
						| SWT.CHECK));

		createColumns(tv, rc);
		tv.getTable().setHeaderVisible(true);
		tv.getTable().setLinesVisible(true);
		tv.setContentProvider(new CoilTableContentProvider());
		return tv;
	}

	private static void createColumns(TableViewer tv, final ReglerConfig rc) {
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
				int hrnr = ((CoilDesc) element).getClnr();
				return String.valueOf(hrnr);
			}
		});

		c = addTVColumn(tv, 150, "Bezeichnung");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((CoilDesc) element).getBezeichnung();
			}
		});

		c = addTVColumn(tv, 90, "Kategorie");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((CoilDesc) element).getAnzKategorie();
			}
		});

		c = addTVColumn(tv, 300, "Kommentar");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((CoilDesc) element).getKommentar();
			}
		});

		c = addTVColumn(tv, 30, "hist data");
		CheckBoxEditingSupport cbes = new CheckBoxEditingSupport(tv, rc);
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				CoilDesc desc = (CoilDesc) element;
				CoilDataID id = new CoilDataID(rc.getnId(), desc.getClnr());
				CoilData cd = (CoilData) DatabaseService.findEntityByID(
						CoilData.class, id);
				if (cd != null) {
					if (cd.getDataSource() != null)
						return CHECKED;
				}
				return UNCHECKED;
			}
		});
		c.setEditingSupport(cbes);
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

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(View.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}
}
