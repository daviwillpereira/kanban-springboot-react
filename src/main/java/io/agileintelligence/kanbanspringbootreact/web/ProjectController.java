package io.agileintelligence.kanbanspringbootreact.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileintelligence.kanbanspringbootreact.domain.Project;
import io.agileintelligence.kanbanspringbootreact.services.MapValidationErrorService;
import io.agileintelligence.kanbanspringbootreact.services.ProjectService;

@RestController
@RequestMapping("/api/project")

public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.handleValidationExceptionsService(result);
		if (errorMap != null)
			return errorMap;

		Project proj = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(proj, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjectById(@PathVariable String projectId) {
		Project project = projectService.findByProjectIdentifier(projectId);
		return new ResponseEntity<>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects() {
		return projectService.findAllProjects();
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
		projectService.deleteProjectByIdentifier(projectId.toUpperCase());
		return new ResponseEntity<String>("project with ID '" + projectId.toUpperCase() + "' was deleted!",
				HttpStatus.OK);
	}
}
