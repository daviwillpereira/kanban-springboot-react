package io.agileintelligence.kanbanspringbootreact.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileintelligence.kanbanspringbootreact.domain.Project;
import io.agileintelligence.kanbanspringbootreact.exceptions.ProjectIdException;
import io.agileintelligence.kanbanspringbootreact.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID: '" + project.getProjectIdentifier().toUpperCase() + "' already exists!");
		}

	}

	public Project findByProjectIdentifier(String projectIdentifier) {

		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exists!");
		}

		return project;
	}

	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId);

		if (project == null) {
			throw new ProjectIdException(
					"Cannot delete Project ID '" + projectId.toUpperCase() + "'. This Project does not exists!");
		}

		projectRepository.delete(project);
	}
}
