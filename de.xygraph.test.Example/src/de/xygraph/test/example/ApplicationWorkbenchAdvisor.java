package de.xygraph.test.example;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		ApplicationWorkbenchWindowAdvisor awbwa = new ApplicationWorkbenchWindowAdvisor(
				configurer);
		return awbwa;
	}

	public String getInitialWindowPerspectiveId() {
		return "de.xygraph.test.Example.perspective";
	}

}
