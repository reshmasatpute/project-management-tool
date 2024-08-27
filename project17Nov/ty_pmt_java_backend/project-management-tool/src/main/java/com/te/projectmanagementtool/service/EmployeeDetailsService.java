package com.te.projectmanagementtool.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.projectmanagementtool.constant.EmployeeConstant;
import com.te.projectmanagementtool.constant.RolesConstant;
import com.te.projectmanagementtool.dto.BackendTeamDTO;
import com.te.projectmanagementtool.dto.EmployeeDetailsDTO;
import com.te.projectmanagementtool.dto.FrontendTeamDTO;
import com.te.projectmanagementtool.dto.GetProjectDetailsDTO;
import com.te.projectmanagementtool.dto.GetProjectHierarchyDTO;
import com.te.projectmanagementtool.dto.ProjectHistoryDTO;
import com.te.projectmanagementtool.dto.ProjectLeadListDTO;
import com.te.projectmanagementtool.dto.UserDTO;
import com.te.projectmanagementtool.entity.EmployeeDetails;
import com.te.projectmanagementtool.entity.ProjectDetails;
import com.te.projectmanagementtool.entity.ProjectHistory;
import com.te.projectmanagementtool.exception.RecordNotFoundException;
import com.te.projectmanagementtool.repository.EmployeeDetailsRepository;
import com.te.projectmanagementtool.repository.ProjectDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeDetailsService {

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;

	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	/**
	 * This method is used to add or update the employee details
	 * 
	 * @param employeeDetailsDTO
	 * @return EmployeeDetailsDTO
	 */
	public EmployeeDetailsDTO addEmployee(EmployeeDetailsDTO employeeDetailsDTO) {
		log.info(EmployeeConstant.ADD_EMPLOYEE_API_OF_EMPLOYEE_SERVICE);

		Optional<EmployeeDetails> employeeOptional = employeeDetailsRepository
				.findById(employeeDetailsDTO.getEmployeeId());
		if (employeeOptional.isPresent()) {
			log.info(EmployeeConstant.EMPLOYEE_UPDATE_SUCCESS);
			EmployeeDetails existedEmployee = employeeOptional.get();
			BeanUtils.copyProperties(employeeDetailsDTO, existedEmployee);
			ProjectHistory projectHistory;
			if (existedEmployee.getEmployeeProjectHistory().get(0).getProjectId().isBlank()
					|| existedEmployee.getEmployeeProjectHistory().get(0).getProjectId()
							.equals(employeeDetailsDTO.getEmployeeProjectHistory().getProjectId())) {
				projectHistory = new ProjectHistory();
				BeanUtils.copyProperties(employeeDetailsDTO.getEmployeeProjectHistory(), projectHistory);
				existedEmployee.setEmployeeProjectHistory(Arrays.asList(projectHistory));
			} else {
				projectHistory = new ProjectHistory();
				BeanUtils.copyProperties(employeeDetailsDTO.getEmployeeProjectHistory(), projectHistory);
				existedEmployee.getEmployeeProjectHistory().add(projectHistory);
			}
			employeeDetailsRepository.save(existedEmployee);
			return employeeDetailsDTO;

		} else {
			log.info(EmployeeConstant.EMPLOYEE_ADD_SUCCESS);
			EmployeeDetails newEmployee = new EmployeeDetails();
			BeanUtils.copyProperties(employeeDetailsDTO, newEmployee);
			newEmployee.setEmployeeId(
					String.valueOf(sequenceGeneratorService.getSequenceNumber(EmployeeDetails.SEQUENCE_NAME)));
			ProjectHistory projectHistory = new ProjectHistory();
			BeanUtils.copyProperties(employeeDetailsDTO.getEmployeeProjectHistory(), projectHistory);
			newEmployee.getEmployeeProjectHistory().add(projectHistory);
			EmployeeDetails savedEmployee = employeeDetailsRepository.save(newEmployee);
			BeanUtils.copyProperties(savedEmployee, employeeDetailsDTO);
			return employeeDetailsDTO;
		}

	}

	/**
	 * This method is used to fetch all employee details
	 * 
	 * @return List<EmployeeDetailsDTO>
	 */
	public List<EmployeeDetailsDTO> getAllEmployee() {
		log.info(EmployeeConstant.GET_ALL_EMPLOYEE_API_OF_EMPLOYEE_SERVICE);

		List<EmployeeDetails> getEmployeeList = employeeDetailsRepository.findAll();
		log.info(EmployeeConstant.FETCH_EMPLOYEE_LIST_SUCCESS);
		return getEmployeeList.stream().map(employee -> {
			EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
			BeanUtils.copyProperties(employee, employeeDetailsDTO);
			List<ProjectHistory> projectHistoryDTOList = employee.getEmployeeProjectHistory().stream().map(a -> {
				ProjectHistory projectHistory = new ProjectHistory();
				ProjectDetails projectDetails = projectDetailsRepository.findByProjectId(a.getProjectId());
				BeanUtils.copyProperties(a, projectHistory);
				if(projectDetails!=null) {
					projectHistory.setProjectName(projectDetails.getProjectName());
				}
				else
					projectHistory.setProjectName("NA");
				return projectHistory;
			}).collect(Collectors.toList());
			employeeDetailsDTO.setEmployeeProjectHistoryList(projectHistoryDTOList);
			return employeeDetailsDTO;
		}).collect(Collectors.toList());
	}

	/**
	 * This method is used to fetch all project manager details
	 * 
	 * @return List<EmployeeDetailsDTO>
	 */
	public List<EmployeeDetailsDTO> getAllProjectManagers() {
		log.info(EmployeeConstant.INSIDE_GET_ALL_PROJECT_MANAGERS_0F_EMPLOYEE_DETAILS_SERVICE);
		return getAllEmployee().stream().filter(a -> a.getRole().equalsIgnoreCase("Project Manager"))
				.collect(Collectors.toList());
	}

	/**
	 * This method is used to fetch all project leads
	 * 
	 * @return List<EmployeeDetailsDTO>
	 */
	public List<EmployeeDetailsDTO> getAllProjectLeads() {
		log.info(EmployeeConstant.INSIDE_GET_ALL_PROJECT_LEADS_0F_EMPLOYEE_DETAILS_SERVICE);
		return getAllEmployee().stream().filter(a -> a.getRole().equalsIgnoreCase("Project Lead"))
				.collect(Collectors.toList());
	}

	/**
	 * This method is used to fetch the hierarchy of Project Lead and Project
	 * Manager
	 * 
	 * @param employeeNo
	 * @return ProjectHierarchyDTO
	 */
	public GetProjectHierarchyDTO getHierarchyMain(String employeeNo) {
		log.info(EmployeeConstant.INSIDE_GET_PROJECT_HIERARCHY_MAIN_METHOD_OF_EMPLPYEE_SERVICE);

		GetProjectHierarchyDTO getProjectHierarchyDTO = new GetProjectHierarchyDTO();
		List<ProjectLeadListDTO> projectLeadListDTOs = new ArrayList<>();

		EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeNo(employeeNo);
		if (employeeDetails != null) {
			log.info(EmployeeConstant.EMPLOYEE_FETCHED_SUCCESS);

			if (employeeDetails.getRole().equalsIgnoreCase(RolesConstant.PROJECT_LEAD)) {
				ProjectLeadListDTO hierarchyHelper = getHierarchyHelper(employeeDetails);
				projectLeadListDTOs.add(hierarchyHelper);
				getProjectHierarchyDTO.setProjectLeadList(projectLeadListDTOs);
				return getProjectHierarchyDTO;
			} else if (employeeDetails.getRole().equalsIgnoreCase(RolesConstant.PROJECT_MANAGER)) {
				getProjectHierarchyDTO.setProjectManagerEmployeeNo(employeeNo);
				getProjectHierarchyDTO.setProjectManagerName(employeeDetails.getEmployeeFirstName() + " "
						+ employeeDetails.getEmployeeMiddleName() + " " + employeeDetails.getEmployeeLastName());

				List<EmployeeDetails> employeeList = employeeDetailsRepository
						.findAllByRole(RolesConstant.PROJECT_LEAD);
				log.info(EmployeeConstant.EMPLOYEE_FETCHED_SUCCESS);

				for (EmployeeDetails employee : employeeList) {
					log.info(EmployeeConstant.FETCHED_PROJECT_HISTORY);
					ProjectLeadListDTO hierarchyHelper = getHierarchyHelper(employee);
					projectLeadListDTOs.add(hierarchyHelper);
				}
				getProjectHierarchyDTO.setProjectLeadList(projectLeadListDTOs);
				return getProjectHierarchyDTO;
			}
		}
		return getProjectHierarchyDTO;
	}

	/**
	 * This method acts as helper to fetch the hierarchy of Project Lead and Project
	 * Manager
	 * 
	 * @param employeeDetails
	 * @return ProjectHierarchyDTO
	 */
	public ProjectLeadListDTO getHierarchyHelper(EmployeeDetails employeeDetails) {
		log.info(EmployeeConstant.INSIDE_GET_PROJECT_HIERARCHY_HELPER_METHOD_OF_EMPLPYEE_SERVICE);

		List<ProjectDetails> projectList = projectDetailsRepository
				.findAllByProjectLead(employeeDetails.getEmployeeNo());
		log.info(EmployeeConstant.FETCHED_PROJECT_OF_PROJECT_LEAD);
		ProjectLeadListDTO projectLeadListDTO = new ProjectLeadListDTO();
		projectLeadListDTO.setEmployeeId(employeeDetails.getEmployeeNo());
		projectLeadListDTO
				.setEmployeeName(employeeDetails.getEmployeeFirstName() + " " + employeeDetails.getEmployeeLastName());
		projectLeadListDTO.setRole(employeeDetails.getRole());

		List<GetProjectDetailsDTO> leadOrManagerDTOList = new ArrayList<>();

		for (ProjectDetails projectDetails : projectList) {
			GetProjectDetailsDTO leadOrManagerDTO = new GetProjectDetailsDTO();
			BeanUtils.copyProperties(projectDetails, leadOrManagerDTO);

			Map<String, String> projectMembers = projectDetails.getProjectMembers();

			if (!projectMembers.isEmpty()) {
				List<BackendTeamDTO> backendTeamDTOList = new ArrayList<>();
				List<FrontendTeamDTO> frontendTeamDTOList = new ArrayList<>();

				// Logic for fetching the back end team members of the project.
				Map<String, String> backendTeamMembers = projectMembers.entrySet().stream()
						.filter(i -> i.getValue().contains(RolesConstant.BACKEND))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
				Collection<String> keysOfBackend = backendTeamMembers.keySet();
				List<EmployeeDetails> backendEmployeeList = new ArrayList<>();
				setBackendTeamMembers(keysOfBackend, backendEmployeeList, projectDetails, employeeDetails,
						backendTeamDTOList, leadOrManagerDTO);

				// Logic for fetching the front end team members of the project.
				Map<String, String> frontEndTeamMembers = projectMembers.entrySet().stream()
						.filter(i -> i.getValue().contains(RolesConstant.FRONTEND))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
				Collection<String> keysOfFrontend = frontEndTeamMembers.keySet();
				List<EmployeeDetails> frontendEmployeeList = new ArrayList<>();
				setFrontendTeamMembers(keysOfFrontend, frontendEmployeeList, projectDetails, employeeDetails,
						frontendTeamDTOList, leadOrManagerDTO);
			} else {
				throw new RecordNotFoundException(EmployeeConstant.NO_PROJECT_MEMBERS);
			}

			leadOrManagerDTOList.add(leadOrManagerDTO);
			projectLeadListDTO.setProjectList(leadOrManagerDTOList);
		}
		return projectLeadListDTO;
	}

	/**
	 * This method is used to fetch the employees by passing list of employee ids
	 * 
	 * @param employeeIds
	 * @return List<EmployeeDetails>
	 */
	private List<EmployeeDetails> findEmployeeList(List<String> employeeIds) {
		return employeeDetailsRepository.findByEmployeeNoIn(employeeIds);
	}

	/**
	 * This method is used to check the reportingTo of an employee
	 * 
	 * @param reportingTo
	 * @param reportedBy
	 * @param projectDetails
	 * @return boolean
	 */
	private boolean checkReportingTo(EmployeeDetails reportingTo, EmployeeDetails reportedBy,
			ProjectDetails projectDetails) {
		return reportingTo.getEmployeeNo()
				.equalsIgnoreCase(getEmployeeProject(reportedBy, projectDetails).get(0).getReportingTo());

	}

	/**
	 * This method is used to get project history of an employee
	 * 
	 * @param reportedBy
	 * @param projectDetails
	 * @return List<ProjectHistory>
	 */
	private List<ProjectHistory> getEmployeeProject(EmployeeDetails reportedBy, ProjectDetails projectDetails) {
		return reportedBy.getEmployeeProjectHistory().stream()
				.filter(a -> a.getProjectId().equalsIgnoreCase(projectDetails.getProjectId()))
				.collect(Collectors.toList());
	}

	/**
	 * This method is used to set front end team members
	 * 
	 * @param keysOfFrontend
	 * @param frontendEmployeeList
	 * @param projectDetails
	 * @param employeeDetails
	 * @param frontendTeamDTOList
	 * @param leadOrManagerDTO
	 */
	private void setFrontendTeamMembers(Collection<String> keysOfFrontend, List<EmployeeDetails> frontendEmployeeList,
			ProjectDetails projectDetails, EmployeeDetails employeeDetails, List<FrontendTeamDTO> frontendTeamDTOList,
			GetProjectDetailsDTO leadOrManagerDTO) {
		log.info(EmployeeConstant.INSIDE_SET_FRONTEND_TEAM_MEMBERS);

		List<String> employeeIds = new ArrayList<>();
		keysOfFrontend.forEach(employeeIds::add);
		frontendEmployeeList.addAll(findEmployeeList(employeeIds));
		if (employeeIds.size() == 1) {
			FrontendTeamDTO teamLead = new FrontendTeamDTO();
			EmployeeDetails v = frontendEmployeeList.get(0);
			List<ProjectHistory> employeeProjectHistory = v.getEmployeeProjectHistory();
			teamLead.setEmployeeId(v.getEmployeeNo());
			teamLead.setEmployeeName(
					v.getEmployeeFirstName() + " " + v.getEmployeeMiddleName() + " " + v.getEmployeeLastName());
			teamLead.setPriority("p1");
			teamLead.setRole(employeeProjectHistory.get(0).getRole());

			frontendTeamDTOList.add(teamLead);
			leadOrManagerDTO.setFrontendTeamDTOList(frontendTeamDTOList);
		} else {
			for (EmployeeDetails v : frontendEmployeeList) {
				FrontendTeamDTO teamLead = null;
				List<FrontendTeamDTO> subTeamMemberList = new ArrayList<>();
				for (EmployeeDetails e : frontendEmployeeList) {

					List<ProjectHistory> employeeProjectHistory = getEmployeeProject(e, projectDetails);
					if ((v.getEmployeeNo().equalsIgnoreCase(employeeProjectHistory.get(0).getReportingTo()))
							&& checkReportingTo(employeeDetails, v, projectDetails)) {
						teamLead = new FrontendTeamDTO();
						teamLead.setEmployeeId(v.getEmployeeNo());
						teamLead.setEmployeeName(v.getEmployeeFirstName() + " " + v.getEmployeeMiddleName() + " "
								+ v.getEmployeeLastName());
						teamLead.setPriority("p1");
						teamLead.setRole(employeeProjectHistory.get(0).getRole());

						if (v.getEmployeeNo().equalsIgnoreCase(employeeProjectHistory.get(0).getReportingTo())) {
							FrontendTeamDTO subTeamMember = new FrontendTeamDTO();
							subTeamMember.setEmployeeId(e.getEmployeeNo());
							subTeamMember.setEmployeeName(e.getEmployeeFirstName() + " " + e.getEmployeeMiddleName()
									+ " " + e.getEmployeeLastName());
							subTeamMember.setPriority(employeeProjectHistory.get(0).getPriority());
							subTeamMember.setRole(employeeProjectHistory.get(0).getRole());
							subTeamMemberList.add(subTeamMember);

						}

					}
				}
				if (teamLead != null) {
					teamLead.setSubTeamDTOList(subTeamMemberList);
					frontendTeamDTOList.add(teamLead);

				}
			}
			leadOrManagerDTO.setFrontendTeamDTOList(frontendTeamDTOList);
		}

	}

	/**
	 * This method is used to set backend team members
	 * 
	 * @param keysOfBackend
	 * @param backendEmployeeList
	 * @param projectDetails
	 * @param employeeDetails
	 * @param backendTeamDTOList
	 * @param leadOrManagerDTO
	 */
	private void setBackendTeamMembers(Collection<String> keysOfBackend, List<EmployeeDetails> backendEmployeeList,
			ProjectDetails projectDetails, EmployeeDetails employeeDetails, List<BackendTeamDTO> backendTeamDTOList,
			GetProjectDetailsDTO leadOrManagerDTO) {
		log.info(EmployeeConstant.INSIDE_SET_BACKEND_TEAM_MEMBERS);

		List<String> employeeIds = new ArrayList<>();
		keysOfBackend.forEach(employeeIds::add);
		backendEmployeeList.addAll(findEmployeeList(employeeIds));
		if (employeeIds.size() == 1) {
			BackendTeamDTO teamLead = new BackendTeamDTO();
			EmployeeDetails v = backendEmployeeList.get(0);
			List<ProjectHistory> employeeProjectHistory = v.getEmployeeProjectHistory();
			teamLead.setEmployeeId(v.getEmployeeNo());
			teamLead.setEmployeeName(
					v.getEmployeeFirstName() + " " + v.getEmployeeMiddleName() + " " + v.getEmployeeLastName());
			teamLead.setPriority("p1");
			teamLead.setRole(employeeProjectHistory.get(0).getRole());

			backendTeamDTOList.add(teamLead);
			leadOrManagerDTO.setBackendTeamDTOList(backendTeamDTOList);
		} else {

			for (EmployeeDetails v : backendEmployeeList) {
				BackendTeamDTO teamLead = null;
				List<BackendTeamDTO> subTeamMemberList = new ArrayList<>();
				for (EmployeeDetails e : backendEmployeeList) {
					List<ProjectHistory> employeeProjectHistory = getEmployeeProject(e, projectDetails);
					if ((v.getEmployeeNo().equalsIgnoreCase(employeeProjectHistory.get(0).getReportingTo()))
							&& checkReportingTo(employeeDetails, v, projectDetails)) {
						teamLead = new BackendTeamDTO();
						teamLead.setEmployeeId(v.getEmployeeNo());
						teamLead.setEmployeeName(v.getEmployeeFirstName() + " " + v.getEmployeeMiddleName() + " "
								+ v.getEmployeeLastName());
						teamLead.setPriority("p1");
						teamLead.setRole(employeeProjectHistory.get(0).getRole());

						if (v.getEmployeeNo().equalsIgnoreCase(employeeProjectHistory.get(0).getReportingTo())) {
							BackendTeamDTO subTeamMember = new BackendTeamDTO();
							subTeamMember.setEmployeeId(e.getEmployeeNo());
							subTeamMember.setEmployeeName(e.getEmployeeFirstName() + " " + e.getEmployeeMiddleName()
									+ " " + e.getEmployeeLastName());
							subTeamMember.setPriority(employeeProjectHistory.get(0).getPriority());
							subTeamMember.setRole(employeeProjectHistory.get(0).getRole());
							subTeamMemberList.add(subTeamMember);
						}

					}
				}
				if (teamLead != null) {
					teamLead.setSubTeamDTOList(subTeamMemberList);
					backendTeamDTOList.add(teamLead);

				}
			}
			leadOrManagerDTO.setBackendTeamDTOList(backendTeamDTOList);
		}

	}

	/**
	 * This method is used to delete the employee details
	 * 
	 * @param employeeId
	 * @return String
	 */
	public String deleteEmployee(String employeeId) {
		log.info(EmployeeConstant.INSIDE_DELETE_EMPLOYEE_API_OF_EMPLOYEE_DETAILS_SERVICE);
		EmployeeDetails findById = employeeDetailsRepository.findByEmployeeNo(employeeId);
		if (findById != null) {
			log.info(EmployeeConstant.EMPLOYEE_DELETE_SUCCESS);
			List<ProjectDetails> projectList = new ArrayList<>();
			findById.getEmployeeProjectHistory().stream().filter(i -> !i.getProjectId().equalsIgnoreCase(""))
					.forEach(j -> {
						ProjectDetails projectDetails = projectDetailsRepository.findByProjectId(j.getProjectId());
						if (projectDetails != null) {
							projectDetails.getProjectMembers().entrySet()
									.removeIf(k -> k.getKey().equalsIgnoreCase(employeeId));
							projectList.add(projectDetails);
						}
					});
			
			projectDetailsRepository.saveAll(projectList);
			employeeDetailsRepository.deleteByEmployeeNo(employeeId);
			return EmployeeConstant.EMPLOYEE_DELETE_SUCCESS;

		} else {
			log.error(EmployeeConstant.EMPLOYEE_DELETE_FAILURE);
			throw new RecordNotFoundException(EmployeeConstant.EMPLOYEE_RECORD_NOT_FOUND);
		}

	}

	/**
	 * This method is used for login
	 * 
	 * @param userDTO
	 * @return String
	 */
	public String login(UserDTO userDTO) {
		EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeNo(userDTO.getUserId());
		if (employeeDetails != null)
			return EmployeeConstant.EMPLOYEE_LOG_IN_SUCCESS;
		else
			return null;

	}

	/**
	 * This method is used to fetch all team leads
	 * 
	 * @return List<EmployeeDetailsDTO>
	 */
	public List<EmployeeDetailsDTO> getAllTeamLeads() {
		log.info(EmployeeConstant.INSIDE_GET_ALL_TEAM_LEADS_0F_EMPLOYEE_DETAILS_SERVICE);
		return getAllEmployee().stream().filter(a -> a.getRole().contains(RolesConstant.BACKEND_TEAM_LEAD)
				|| a.getRole().contains(RolesConstant.FRONTEND_TEAM_LEAD)).collect(Collectors.toList());
	}

}
