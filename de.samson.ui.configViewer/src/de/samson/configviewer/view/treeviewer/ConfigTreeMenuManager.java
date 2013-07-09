package de.samson.configviewer.view.treeviewer;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;

public class ConfigTreeMenuManager extends MenuManager {

	public ConfigTreeMenuManager(final TreeViewer tv) {
		super();

		final Action reglerLoeschen = new Action("reglerLoeschen") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) tv
						.getSelection();
				ReglerConfig r = (ReglerConfig) selection.getFirstElement();

				DatabaseService.removeEntity(r);
				DatabaseService.refreshEntity(r.getGatewayConfig());

				tv.refresh();
				super.run();
			}

			@Override
			public String getText() {
				return "Regler löschen";
			}
		};

		/**
		 * Gateway Menü
		 */
		final Action reglerHinzufuegen = new Action("reglerHinzufuegen") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) tv
						.getSelection();

				GatewayConfig gwc = (GatewayConfig) selection.getFirstElement();
				DatabaseService.addDefaultRegisterConfigToGateway(gwc);
				DatabaseService.refreshEntity(gwc);

				tv.refresh();
				super.run();
			}

			@Override
			public String getText() {
				return "Regler hinzufügen";
			}
		};

		final Action gatewayLoeschen = new Action("gatewayLoeschen") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) tv
						.getSelection();
				GatewayConfig gwc = (GatewayConfig) selection.getFirstElement();
				DatabaseService.removeEntity(gwc);
				DatabaseService.refreshEntity(gwc.getStandort());

				tv.refresh();
				super.run();
			}

			@Override
			public String getText() {
				return "Gateway löschen";
			}
		};

		/**
		 * Standort Menü
		 */
		final Action gatewayHinzufuegen = new Action("gatewayHinzufuegen") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) tv
						.getSelection();
				Standort s = (Standort) selection.getFirstElement();
				DatabaseService.addDefaultGatewayToStandort(s);
				DatabaseService.refreshEntity(s);
				tv.refresh();
				super.run();
			}

			@Override
			public String getText() {
				return "Gateway hinzufügen";
			}
		};

		final Action standortLoeschen = new Action("standortLoeschen") {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) tv
						.getSelection();
				Standort s = (Standort) selection.getFirstElement();
				DatabaseService.removeEntity(s);

				@SuppressWarnings("unchecked")
				List<Standort> l = (List<Standort>) tv.getInput();

				l.remove(s);

				tv.refresh();
				super.run();
			}

			@Override
			public String getText() {
				return "Standort Löschen";
			}
		};

		/**
		 * Nichts gewählt
		 */
		final Action standortHinzufuegen = new Action("standortHinzufuegen") {
			@Override
			public void run() {
				Standort s = DatabaseService.addDefaultStandort();

				@SuppressWarnings("unchecked")
				List<Standort> l = (List<Standort>) tv.getInput();

				l.add(s);

				tv.refresh();
				super.run();
			}

			@Override
			public String getText() {
				return "Standort hinzufügen";
			}
		};

		setRemoveAllWhenShown(true);

		addMenuListener(new IMenuListener() {

			public void menuAboutToShow(IMenuManager manager) {
				IStructuredSelection selection = (IStructuredSelection) tv
						.getSelection();

				if (selection.getFirstElement() instanceof ReglerConfig) {
					add(reglerLoeschen);
				}
				if (selection.getFirstElement() instanceof GatewayConfig) {
					add(reglerHinzufuegen);
					add(gatewayLoeschen);
				}
				if (selection.getFirstElement() instanceof Standort) {
					add(gatewayHinzufuegen);
					add(standortLoeschen);
				}

				if (selection.isEmpty()) {
					add(standortHinzufuegen);
				}
			}
		});
	}
}
