package net.hexagon.sun.eclipse.bb.handlers;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Handler executed when the toolbar's button is clicked. Gathers all selected projects & clean/builds them.
 */
public class BuildHandler extends AbstractHandler {

	/**
	 * Entry point called by Eclipse's framework.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IJavaProject> projects = getSelectedProjects();
		buildProjects(projects);
		return null;
	}

	private void buildProjects(List<IJavaProject> allProjects) {
		for (IJavaProject project : allProjects) {
			buildProject(project);
		}
	}

	private void buildProject(IJavaProject project) {
		try {
			System.out.println("Building project \"" + project.getElementName() + "\"");
			project.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		} catch (CoreException e) {
			System.err.println("Failed to clean/build project \"" + project.getElementName() + "\"");
			e.printStackTrace();
		}
	}
	
	private List<IJavaProject> getSelectedProjects() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return Collections.emptyList();
		}
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		List<IJavaProject> projects = getProjects(selection);
		return projects;
	}

	private List<IJavaProject> getProjects(IStructuredSelection selection) {
		List<IJavaProject> projects= new LinkedList<IJavaProject>();
		for (@SuppressWarnings("rawtypes") Iterator it = selection.iterator(); it.hasNext();) {
			Object element = it.next();
			IJavaProject project= processElement(element);
			if (project != null) {
				projects.add(project);
			}
		}
		return Collections.unmodifiableList(projects);
	}

	private IJavaProject processElement(Object element) {
		if (element instanceof IJavaProject) {
			IJavaProject project= (IJavaProject) element;
			return project;
		}
		return null;
	}
}
