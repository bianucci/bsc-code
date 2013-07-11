package de.samson.application.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import de.samson.application.shared.PartID;
import de.samson.configviewer.editor.regler.ReglerConfEditor;
import de.samson.configviewer.editor.regler.ReglerConfigEditorInput;
import de.samson.dataviewer.editor.ReglerDataEditor;
import de.samson.dataviewer.editor.ReglerDataEditorInput;
import de.samson.service.database.entities.config.ReglerConfig;

public class SwitchPerspective extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IWorkbenchWindow w = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();

			IWorkbenchPage page = w.getActivePage();

			// store selected editor
			IEditorPart activeEditor = page.getActiveEditor();
			ReglerConfig activeRegler = null;
			if (activeEditor instanceof ReglerConfEditor) {
				activeRegler = ((ReglerConfEditor) activeEditor).getRc();
			}
			else if (activeEditor instanceof ReglerDataEditor)
				activeRegler = ((ReglerDataEditor) activeEditor).getRc();

			// Get all ReglerConfigs that are about to get edited
			IEditorReference[] editorReferences = page.getEditorReferences();
			List<ReglerConfig> l = new ArrayList<ReglerConfig>();
			for (int i = 0; i < editorReferences.length; i++) {
				IEditorPart editor = editorReferences[i].getEditor(false);
				if (editor instanceof ReglerConfEditor) {
					l.add(((ReglerConfEditor) editor).getRc());
				}

				if (editor instanceof ReglerDataEditor) {
					l.add(((ReglerDataEditor) editor).getRc());
				}
			}

			// close editors
			page.closeAllEditors(true);

			// OPEN EDITORS
			if (w.getActivePage().getPerspective().getId()
					.equals(PartID.Perspective_Data_ID)) {
				PlatformUI.getWorkbench().showPerspective(
						PartID.Perspective_Config_ID, w);
				for (ReglerConfig rc : l) {
					page.openEditor(new ReglerConfigEditorInput(rc.getnId()),
							PartID.Editor_Regler_Conf_ID);
				}
			} else if (w.getActivePage().getPerspective().getId()
					.equals(PartID.Perspective_Config_ID)) {
				PlatformUI.getWorkbench().showPerspective(
						PartID.Perspective_Data_ID, w);
				for (ReglerConfig rc : l) {
					page.openEditor(new ReglerDataEditorInput(rc.getnId()),
							PartID.Editor_Regler_Data_ID);
				}
			}

			// ACTIVATE EDITOR
			if (activeRegler != null) {
				editorReferences = page.getEditorReferences();
				for (int i = 0; i < editorReferences.length; i++) {
					IEditorPart editor = editorReferences[i].getEditor(false);
					ReglerConfig temp = null;
					if (editor instanceof ReglerConfEditor) {
						temp = ((ReglerConfEditor) editor).getRc();
					} else if (editor instanceof ReglerDataEditor) {
						temp = ((ReglerDataEditor) editor).getRc();
					}
					if (temp==activeRegler) {
						page.activate(editor);
						break;
					}
				}
			}

		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
		return null;
	}
}
