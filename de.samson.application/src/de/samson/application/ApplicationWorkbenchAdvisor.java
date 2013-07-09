package de.samson.application;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import de.samson.application.shared.PartID;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		ApplicationWorkbenchWindowAdvisor awbwa = new ApplicationWorkbenchWindowAdvisor(
				configurer);

		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().getPerspectives();

		System.err.println(perspectives);

		return awbwa;
	}

	public String getInitialWindowPerspectiveId() {
		return PartID.Perspective_Config_ID;
	}

}
