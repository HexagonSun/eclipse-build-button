package net.hexagon.sun.eclipse.bb.handlers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
	protected Set<IProject> getSelectedProjects() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return Collections.emptySet();
		}
		ISelection selection = window.getSelectionService().getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return Collections.emptySet();
		}
		Set<IProject> projects = getProjects((IStructuredSelection) selection);
		return projects;
	}

	private Set<IProject> getProjects(IStructuredSelection selection) {
		Set<IProject> projects= new HashSet<IProject>();
		for (@SuppressWarnings("rawtypes") Iterator it = selection.iterator(); it.hasNext();) {
			Object element = it.next();
			projects.addAll(asProject(element));
		}
		return Collections.unmodifiableSet(projects);
	}

	private Set<IProject> asProject(Object element) {
		Set<IProject> projects= new HashSet<IProject>();
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
	
	private Set<IProject> asProject(IProject project) {
		return Collections.singleton(project);
	}
	
	private Set<IProject> asProject(IJavaProject javaProject) {
		return asProject(javaProject.getProject());
	}

	private Set<IProject> asProject(IWorkingSet workingSet) {
		Set<IProject> projects= new HashSet<IProject>();
		IAdaptable[] wsElements = workingSet.getElements();
		for (IAdaptable a : wsElements) {
			projects.addAll(asProject(a));
		}
		return projects;
	}
			
}
