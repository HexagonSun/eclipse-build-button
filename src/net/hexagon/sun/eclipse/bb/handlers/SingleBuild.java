package net.hexagon.sun.eclipse.bb.handlers;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class SingleBuild extends BuildHandler {
	
	protected List<IJavaProject> getSelectedProjects() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return Collections.emptyList();
		}
		ISelection selection = window.getSelectionService().getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return Collections.emptyList();
		}
		List<IJavaProject> projects = getProjects((IStructuredSelection) selection);
		return projects;
	}

	private List<IJavaProject> getProjects(IStructuredSelection selection) {
		List<IJavaProject> projects= new LinkedList<IJavaProject>();
		for (@SuppressWarnings("rawtypes") Iterator it = selection.iterator(); it.hasNext();) {
			Object element = it.next();
			addJavaProjects(projects, element);
		}
		return Collections.unmodifiableList(projects);
	}

}
