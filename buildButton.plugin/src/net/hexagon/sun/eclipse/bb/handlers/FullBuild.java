package net.hexagon.sun.eclipse.bb.handlers;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

public class FullBuild extends BuildHandler {
	
	protected Set<IProject> getSelectedProjects() {
		Set<IProject> allProjects= new HashSet<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			addProject(allProjects, project);
		}
		return allProjects;
	}

}
