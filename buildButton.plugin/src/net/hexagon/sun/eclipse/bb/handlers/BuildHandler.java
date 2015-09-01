package net.hexagon.sun.eclipse.bb.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * Handler executed when the toolbar's button is clicked. Gathers all selected projects & clean/builds them.
 */
public abstract class BuildHandler extends AbstractHandler {
	
	/** Entry point called by Eclipse's framework. */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IProject> projects = getSelectedProjects();
		buildProjects(projects);
		return null;
	}
	
	protected abstract List<IProject> getSelectedProjects();

	private void buildProjects(List<IProject> allProjects) {
		for (IProject p : allProjects) {
			buildProject(p);
		}
	}

	private void buildProject(IProject project) {
		try {
			System.out.println("Building project \"" + project.getName() + "\"");
			project.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		} catch (CoreException e) {
			System.err.println("Failed to clean/build project \"" + project.getName() + "\"");
			e.printStackTrace();
		}
	}

	protected void addProject(List<IProject> allProjects, IProject project) {
		if (project != null) {
			allProjects.add(project);
		}
	}

}
