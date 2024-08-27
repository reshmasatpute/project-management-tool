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

import com.te.projectmanagementtool.constant.EmployeeConstant;
import com.te.projectmanagementtool.dto.EmployeeDetailsDTO;
import com.te.projectmanagementtool.dto.GetProjectHierarchyDTO;
import com.te.projectmanagementtool.service.EmployeeDetailsService;
import com.te.projectmanagementtool.util.APIResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@Slf4j
@CrossOrigin(origins = "*")
public class EmployeeDetailsController {

	@Autowired
	private EmployeeDetailsService employeeDetailsService;

	@PostMapping("/add")
	public ResponseEntity<APIResponse> addEmployee(@RequestBody EmployeeDetailsDTO employeeDetailsDTO) {
		log.info(EmployeeConstant.INSIDE_ADD_EMPLOYEE_API_OF_EMPLOYEE_CONTROLLER);

		EmployeeDetailsDTO createEmployee = employeeDetailsService.addEmployee(employeeDetailsDTO);
		if (createEmployee != null) {
			log.info(EmployeeConstant.EMPLOYEE_ADD_SUCCESS);
			return new ResponseEntity<>(APIResponse.builder().data(createEmployee).error(false)
					.message(EmployeeConstant.EMPLOYEE_ADD_SUCCESS).build(), HttpStatus.OK);
		} else
			log.error(EmployeeConstant.EMPLOYEE_ADD_FAILURE);
		return new ResponseEntity<>(APIResponse.builder().data(createEmployee).error(true)
				.message(EmployeeConstant.EMPLOYEE_ADD_FAILURE).build(), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/get-all")
	public ResponseEntity<APIResponse> getAllEmplyeeList() {
		log.info(EmployeeConstant.INSIDE_GET_ALL_EMPLOYEE_API_OF_EMPLOYEE_CONTROLLER);

		List<EmployeeDetailsDTO> getAllEmplyee = employeeDetailsService.getAllEmployee();
		if (getAllEmplyee.isEmpty()) {
			log.error(EmployeeConstant.EMPLOYEE_RECORD_NOT_FOUND);
			return new ResponseEntity<>(APIResponse.builder().data(getAllEmplyee).error(true)
					.message(EmployeeConstant.EMPLOYEE_RECORD_NOT_FOUND).build(), HttpStatus.NOT_FOUND);
		} else {
			log.info(EmployeeConstant.EMPLOYEE_FETCHED_SUCCESS);
			return new ResponseEntity<>(APIResponse.builder().data(getAllEmplyee).error(false)
					.message(EmployeeConstant.EMPLOYEE_FETCHED_SUCCESS).build(), HttpStatus.OK);
		}

	}

	@GetMapping("/get-all/project-manager")
	public ResponseEntity<APIResponse> getAllProjectManagers() {
		log.info(EmployeeConstant.INSIDE_GET_ALL_PROJECT_MANAGERS_API_0F_EMPLOYEE_DETAILS_CONTROLLER);

		List<EmployeeDetailsDTO> employeeDetailsDTOList = employeeDetailsService.getAllProjectManagers();
		if (employeeDetailsDTOList.isEmpty()) {
			log.error(EmployeeConstant.PROJECT_MANAGER_GET_FAILURE);
			return new ResponseEntity<>(APIResponse.builder().data(employeeDetailsDTOList).error(true)
					.message(EmployeeConstant.PROJECT_MANAGER_GET_FAILURE).build(), HttpStatus.NOT_FOUND);
		} else {
			log.error(EmployeeConstant.EMPLOYEE_RECORD_NOT_FOUND);
			return new ResponseEntity<>(APIResponse.builder().data(employeeDetailsDTOList).error(false)
					.message(EmployeeConstant.PROJECT_MANAGER_GET_SUCCESS).build(), HttpStatus.OK);
		}
	}

	@GetMapping("/get-all/project-lead")
	public ResponseEntity<APIResponse> getAllProjectLeads() {
		log.info(EmployeeConstant.INSIDE_GET_ALL_PROJECT_LEADS_API_0F_EMPLOYEE_DETAILS_CONTROLLER);

		List<EmployeeDetailsDTO> employeeDetailsDTOList = employeeDetailsService.getAllProjectLeads();
		if (employeeDetailsDTOList.isEmpty()) {
			return new ResponseEntity<>(APIResponse.builder().data(employeeDetailsDTOList).error(true)
					.message(EmployeeConstant.PROJECT_LEAD_GET_FAILURE).build(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(APIResponse.builder().data(employeeDetailsDTOList).error(false)
					.message(EmployeeConstant.PROJECT_LEAD_GET_SUCCESS).build(), HttpStatus.OK);
		}

	}

	@GetMapping("/get/hierarchy/{employeeId}")
	public ResponseEntity<APIResponse> getHierarchy(@PathVariable String employeeId) {
		log.info(EmployeeConstant.INSIDE_HIERARCHY_API_OF_EMPLOYEE_DETAILS_CONTROLLER);

		GetProjectHierarchyDTO getProjectHierarchyDTO = employeeDetailsService.getHierarchyMain(employeeId);
		if (getProjectHierarchyDTO == null) {
			return new ResponseEntity<>(APIResponse.builder().data(getProjectHierarchyDTO).error(true)
					.message(EmployeeConstant.PROJECT_LEAD_GET_SUCCESS).build(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(APIResponse.builder().data(getProjectHierarchyDTO).error(false)
					.message(EmployeeConstant.PROJECT_LEAD_GET_SUCCESS).build(), HttpStatus.OK);
		}

	}

	@DeleteMapping("/delete/{employeeId}")
	public ResponseEntity<APIResponse> deleteEmployee(@PathVariable String employeeId) {

		log.info(EmployeeConstant.INSIDE_DELETE_EMPLOYEE_API_OF_EMPLOYEE_DETAILS_CONTROLLER);
		String deleteEmployee = employeeDetailsService.deleteEmployee(employeeId);

		return new ResponseEntity<>(APIResponse.builder().data(employeeId).error(false).message(deleteEmployee).build(),
				HttpStatus.OK);
	}

	@GetMapping("/get-all/team-lead")
	public ResponseEntity<APIResponse> getAllTeamLeads() {
		log.info(EmployeeConstant.INSIDE_GET_ALL_TEAM_LEADS_API_0F_EMPLOYEE_DETAILS_CONTROLLER);

		List<EmployeeDetailsDTO> employeeDetailsDTOList = employeeDetailsService.getAllTeamLeads();
		if (employeeDetailsDTOList.isEmpty()) {
			return new ResponseEntity<>(APIResponse.builder().data(employeeDetailsDTOList).error(true)
					.message(EmployeeConstant.TEAM_LEAD_GET_FAILURE).build(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(APIResponse.builder().data(employeeDetailsDTOList).error(false)
					.message(EmployeeConstant.TEAM_LEAD_GET_SUCCESS).build(), HttpStatus.OK);
		}

		
	}

}
