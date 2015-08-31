package net.hexagon.sun.eclipse.bb.handlers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;

public class FullBuild extends BuildHandler {
	
	protected List<IJavaProject> getSelectedProjects() {
		List<IJavaProject> javaProjects= new LinkedList<IJavaProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			addJavaProjects(javaProjects, project);
		}
		return javaProjects;
	}

}
