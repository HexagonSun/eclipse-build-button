package net.hexagon.sun.eclipse.bb.handlers;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;

public class SelectionBuild extends BuildHandler {
	
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
			projects.addAll(asProject(element));
		}
		return Collections.unmodifiableList(projects);
	}

	private List<IProject> asProject(Object element) {
		List<IProject> projects= new LinkedList<IProject>();
		if (element == null) {
			return projects;
		}
		
		if (element instanceof IProject) {
			return asProject((IProject) element);
		}
		
		if (element instanceof IWorkingSet) {
			return asProject((IWorkingSet) element);
		}
		if (element instanceof IJavaProject) {
			return asProject((IJavaProject) element);
		}
		return projects;
	}
	
	private List<IProject> asProject(IProject project) {
		return Collections.singletonList(project);
	}
	
	private List<IProject> asProject(IJavaProject javaProject) {
		return asProject(javaProject.getProject());
	}

	private List<IProject> asProject(IWorkingSet workingSet) {
		List<IProject> projects= new LinkedList<IProject>();
		IAdaptable[] wsElements = workingSet.getElements();
		for (IAdaptable a : wsElements) {
			projects.addAll(asProject(a));
		}
		return projects;
	}
			
}
