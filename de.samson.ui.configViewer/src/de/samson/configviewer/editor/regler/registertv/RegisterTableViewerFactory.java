package de.samson.configviewer.editor.regler.registertv;

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
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.RegisterDataID;
import de.samson.service.database.entities.data.WmwData;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.entities.description.WmwDesc;
import de.samson.service.database.ientities.histdata.HistDataSource;

public class RegisterTableViewerFactory {

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
			HRegDesc desc = (HRegDesc) element;

			RegisterDataID id = new RegisterDataID(rc.getnId(),
					desc.getHrnr() - 40000);
			RegisterData rd = (RegisterData) DatabaseService.findEntityByID(
					RegisterData.class, id);
			if (rd != null) {
				if (rd.getDataSource() != null)
					return true;
				else if (rd.getWmw() != null)
					if (rd.getWmw().getDataSource() != null)
						return true;
			}
			return false;
		}

		@Override
		protected void setValue(Object element, Object value) {

			HRegDesc desc = (HRegDesc) element;

			RegisterDataID id = new RegisterDataID(rc.getnId(),
					desc.getHrnr() - 40000);

			RegisterData register = (RegisterData) DatabaseService
					.findEntityByID(RegisterData.class, id);

			if (register != null)

				if (register.getDataSource() == null) {

					if (register.getWmw() != null) {

						if (register.getWmw().getDataSource() == null) {
							// add new wmw
							WmwDesc wmwDesc = desc.getLinkedWmwDesc();
							DatabaseService.addNewDataSourceForWMW(register,
									wmwDesc);

						} else {
							// remove wmw
							WmwData wmwData = register.getWmw();
							DatabaseService.removeWmwDataSource(wmwData);
						}
					} else {
						DatabaseService.addNewDataSourceForHoldingReg(register);
					}
				} else {
					HistDataSource dataSource = register.getDataSource();
					register.setDataSource(null);
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
		tv.setContentProvider(new RegTableContentProvider());
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
				int hrnr = ((HRegDesc) element).getHrnr();
				return String.valueOf(hrnr);
			}
		});

		c = addTVColumn(tv, 150, "Bezeichnung");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((HRegDesc) element).getBezeichnung();
			}
		});

		c = addTVColumn(tv, 90, "Kategorie");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((HRegDesc) element).getAnzKategorie();
			}
		});

		c = addTVColumn(tv, 300, "Kommentar");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((HRegDesc) element).getKommentar();
			}
		});

		c = addTVColumn(tv, 50, "RO");
		c.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return String.valueOf(((HRegDesc) element).isRo());
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
				RegisterDataID id = new RegisterDataID(rc.getnId(),
						((HRegDesc) element).getHrnr() - 40000);
				RegisterData rd = (RegisterData) DatabaseService
						.findEntityByID(RegisterData.class, id);
				if (rd != null) {
					if (rd.getDataSource() != null)
						return CHECKED;
					else if (rd.getWmw() != null)
						if (rd.getWmw().getDataSource() != null)
							return CHECKED;
				}
				return UNCHECKED;
			}
		});
		c.setEditingSupport(cbes);
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

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(View.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}

}
