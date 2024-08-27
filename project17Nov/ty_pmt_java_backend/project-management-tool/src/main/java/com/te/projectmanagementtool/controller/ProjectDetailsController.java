package com.te.projectmanagementtool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.projectmanagementtool.constant.ProjectConstant;
import com.te.projectmanagementtool.dto.GetAllEmployeeProjectDto;
import com.te.projectmanagementtool.dto.GetEmployeeDto;
import com.te.projectmanagementtool.dto.GetProjectDto;
import com.te.projectmanagementtool.dto.ProjectDetailsDTO;
import com.te.projectmanagementtool.service.ProjectDetailsService;
import com.te.projectmanagementtool.util.APIResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/project")
@Slf4j
@CrossOrigin(origins = "*")
public class ProjectDetailsController {

	@Autowired
	private ProjectDetailsService projectDetailsService;

	@PostMapping("/add")
	public ResponseEntity<APIResponse> addProject(@RequestBody ProjectDetailsDTO projectDetailsDTO) {
		log.info(ProjectConstant.INSIDE_ADD_PROJECT_API_OF_PROJECT_CONTROLLER);

		ProjectDetailsDTO response = projectDetailsService.addProject(projectDetailsDTO);
		if (response != null) {
			log.info(ProjectConstant.PROJECT_DETAILS_ADD_SUCCESS);
			return new ResponseEntity<>(APIResponse.builder().data(response).error(false)
					.message(ProjectConstant.PROJECT_DETAILS_ADD_SUCCESS).build(), HttpStatus.OK);
		} else
			log.error(ProjectConstant.PROJECT_DETAILS_ADD_FAILURE);
		return new ResponseEntity<>(APIResponse.builder().data(response).error(true)
				.message(ProjectConstant.PROJECT_DETAILS_ADD_FAILURE).build(), HttpStatus.NOT_FOUND);
	}

	@PostMapping("/update")
	public ResponseEntity<APIResponse> updateProject(@RequestBody ProjectDetailsDTO projectDetailsDTO) {
		log.info(ProjectConstant.INSIDE_ADD_PROJECT_API_OF_PROJECT_CONTROLLER);

		ProjectDetailsDTO response = projectDetailsService.updateProject(projectDetailsDTO);
		if (response != null) {
			log.info(ProjectConstant.UPDATE_PROJECT_SUCCESS);
			return new ResponseEntity<>(APIResponse.builder().data(response).error(false)
					.message(ProjectConstant.UPDATE_PROJECT_SUCCESS).build(), HttpStatus.OK);
		} else
			log.error(ProjectConstant.UPDATE_PROJECT_FAILURE);
		return new ResponseEntity<>(APIResponse.builder().data(response).error(true)
				.message(ProjectConstant.UPDATE_PROJECT_FAILURE).build(), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/get-all")
	public ResponseEntity<APIResponse> getAllProjects() {
		log.info(ProjectConstant.INSIDE_GET_ALL_PROJECTS_EMPLOYEES_API_OF_PROJECT_CONTROLLER);

		List<ProjectDetailsDTO> projectDetailsDTOList = projectDetailsService.getAllProjects();
		if (projectDetailsDTOList.isEmpty()) {
			log.error(ProjectConstant.PROJECT_DETAILS_GET_FAILURE);
			return new ResponseEntity<>(APIResponse.builder().data(projectDetailsDTOList).error(true)
					.message(ProjectConstant.PROJECT_DETAILS_GET_FAILURE).build(), HttpStatus.NOT_FOUND);
		} else {
			log.info(ProjectConstant.PROJECT_DETAILS_GET_SUCCESS);
			return new ResponseEntity<>(APIResponse.builder().data(projectDetailsDTOList).error(false)
					.message(ProjectConstant.PROJECT_DETAILS_GET_SUCCESS).build(), HttpStatus.OK);
		}

	}

	@GetMapping("/get-employees-projects")
	public ResponseEntity<APIResponse> getAllEmpsProjectDetails() {
		log.info(ProjectConstant.INSIDE_GET_ALL_PROJECTS_EMPLOYEES_API_OF_PROJECT_CONTROLLER);

		GetAllEmployeeProjectDto allEmpsProjects = projectDetailsService.getAllEmpsProjects();
		log.info(ProjectConstant.GET_ALL_EMPS_PROJECT_DETAILS_FETCHED_SUCCESS);

		return new ResponseEntity<>(APIResponse.builder().data(allEmpsProjects).error(false)
				.message(ProjectConstant.GET_ALL_EMPS_PROJECT_DETAILS_FETCHED_SUCCESS).build(), HttpStatus.OK);

	}

	@DeleteMapping("/delete/{projectId}")
	public ResponseEntity<APIResponse> deleteProject(@PathVariable String projectId) {
		log.info(ProjectConstant.INSIDE_DELETE_PROJECT_API_OF_PROJECT_CONTROLLER);
		String deleteProject = projectDetailsService.deleteProject(projectId);

		return new ResponseEntity<>(APIResponse.builder().data(projectId).error(false).message(deleteProject).build(),
				HttpStatus.OK);

	}

	@PostMapping("/map")
	public ResponseEntity<APIResponse> mapProjectAndEmployees(@RequestBody GetProjectDto getProjectDto) {
		log.info(ProjectConstant.INSIDE_MAP_PROJECT_EMPLOYEE_API_OF_PROJECT_CONTROLLER);

		GetProjectDto response = projectDetailsService.mapProjectAndEmployees(getProjectDto);

		if (response != null)
			return new ResponseEntity<>(
					APIResponse.builder().data(response).error(false)
							.message(ProjectConstant.GET_ALL_EMPS_PROJECT_DETAILS_FETCHED_SUCCESS).build(),
					HttpStatus.OK);
		else
			return new ResponseEntity<>(
					APIResponse.builder().data(response).error(true)
							.message(ProjectConstant.GET_ALL_EMPS_PROJECT_DETAILS_FETCHED_SUCCESS).build(),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping("/relieve-employee")
	public ResponseEntity<APIResponse> relieveEmployeeFromProject(@RequestBody GetEmployeeDto getProjectDto) {
		log.info(ProjectConstant.INSIDE_RELIEVE_EMPLOYEE_FROM_PROJECT_API_OF_PROJECT_CONTROLLER);

		String response = projectDetailsService.relieveEmployeeFromProject(getProjectDto);
		if (response != null)
			return new ResponseEntity<>(APIResponse.builder().data(response).error(false).message(response).build(),
					HttpStatus.OK);
		else
			return new ResponseEntity<>(
					APIResponse.builder().data(response).error(true)
							.message(ProjectConstant.RELIEVE_EMPLOYEE_FROM_PROJECT_FAILURE).build(),
					HttpStatus.NOT_FOUND);
	}

}
