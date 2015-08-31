package net.hexagon.sun.eclipse.bb.handlers;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class SingleBuild extends BuildHandler {
	
	@Override
	protected List<IProject> getSelectedProjects() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return Collections.emptyList();
		}
		ISelection selection = window.getSelectionService().getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return Collections.emptyList();
		}
		List<IProject> projects = getProjects((IStructuredSelection) selection);
		return projects;
	}

	private List<IProject> getProjects(IStructuredSelection selection) {
		List<IProject> projects= new LinkedList<IProject>();
		for (@SuppressWarnings("rawtypes") Iterator it = selection.iterator(); it.hasNext();) {
			Object element = it.next();
			addProject(projects, asProject(element));
		}
		return Collections.unmodifiableList(projects);
	}

	private IProject asProject(Object element) {
		if (element instanceof IProject) {
			return (IProject) element;
		}
		if (element instanceof IJavaProject) {
			IJavaProject javaProject = (IJavaProject) element;
			if (javaProject != null) {
				return javaProject.getProject();
			}
		}
		return null;
	}

}
