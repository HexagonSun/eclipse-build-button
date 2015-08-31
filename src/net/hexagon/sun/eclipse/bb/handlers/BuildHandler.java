package net.hexagon.sun.eclipse.bb.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;

/**
 * Handler executed when the toolbar's button is clicked. Gathers all selected projects & clean/builds them.
 */
public abstract class BuildHandler extends AbstractHandler {
	
	/** Entry point called by Eclipse's framework. */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IJavaProject> projects = getSelectedProjects();
		buildProjects(projects);
		return null;
	}
	
	protected abstract List<IJavaProject> getSelectedProjects();

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

	protected void addJavaProjects(List<IJavaProject> projects, Object element) {
		IJavaProject project= processElement(element);
		if (project != null) {
			projects.add(project);
		}
	}

	private IJavaProject processElement(Object element) {
		if (element instanceof IJavaProject) {
			IJavaProject project= (IJavaProject) element;
			return project;
		}
		return null;
	}

}
