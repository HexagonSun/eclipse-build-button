package net.hexagon.sun.eclipse.bb.handlers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

public class FullBuild extends BuildHandler {
	
	protected List<IProject> getSelectedProjects() {
		List<IProject> allProjects= new LinkedList<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			addProject(allProjects, project);
		}
		return allProjects;
	}

}
