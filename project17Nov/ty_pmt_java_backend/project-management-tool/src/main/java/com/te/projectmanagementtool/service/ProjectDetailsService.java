package com.te.projectmanagementtool.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.projectmanagementtool.constant.EmployeeConstant;
import com.te.projectmanagementtool.constant.ProjectConstant;
import com.te.projectmanagementtool.constant.RolesConstant;
import com.te.projectmanagementtool.dto.EmployeeDetailsDTO;
import com.te.projectmanagementtool.dto.GetAllEmployeeProjectDto;
import com.te.projectmanagementtool.dto.GetEmployeeDto;
import com.te.projectmanagementtool.dto.GetProjectDto;
import com.te.projectmanagementtool.dto.ProjectDetailsDTO;
import com.te.projectmanagementtool.dto.ProjectHistoryDTO;
import com.te.projectmanagementtool.entity.EmployeeDetails;
import com.te.projectmanagementtool.entity.ProjectDetails;
import com.te.projectmanagementtool.entity.ProjectHistory;
import com.te.projectmanagementtool.exception.RecordNotFoundException;
import com.te.projectmanagementtool.repository.EmployeeDetailsRepository;
import com.te.projectmanagementtool.repository.ProjectDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectDetailsService {

	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	public ProjectDetailsDTO addProject(ProjectDetailsDTO projectDetailsDTO) {
		log.info(ProjectConstant.INSIDE_ADD_PROJECT_API_OF_PROJECT_SERVICE);

		ProjectDetails projectDetails = new ProjectDetails();
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);
		projectDetails
				.setProjectId(String.valueOf(sequenceGeneratorService.getSequenceNumber(ProjectDetails.SEQUENCE_NAME)));
		Map<String, String> projectMembers = projectDetailsDTO.getProjectMembers();
		projectDetails.setProjectMembers(projectMembers);
		projectMembers.entrySet().forEach(a -> {
			EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeNo(a.getKey());
			if (employeeDetails != null && a.getValue().equalsIgnoreCase(RolesConstant.PROJECT_MANAGER)) {
				EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO(employeeDetails.getEmployeeNo(),
						new ProjectHistoryDTO(projectDetails.getProjectId(), "1", "p1", "working", a.getValue(),
								LocalDate.now(), null, a.getKey()));
				setProjectHistoryOfEmployee(employeeDetails, employeeDetailsDTO);

			} else if (a.getValue().equalsIgnoreCase(RolesConstant.PROJECT_LEAD)) {
				EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO(employeeDetails.getEmployeeNo(),
						new ProjectHistoryDTO(projectDetails.getProjectId(), "1", "p1", "working", a.getValue(),
								LocalDate.now(), null, projectDetailsDTO.getProjectManager()));
				setProjectHistoryOfEmployee(employeeDetails, employeeDetailsDTO);
			} else {
				EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO(employeeDetails.getEmployeeNo(),
						new ProjectHistoryDTO(projectDetails.getProjectId(), "1", "p1", "working", a.getValue(),
								LocalDate.now(), null, projectDetailsDTO.getProjectLead()));
				setProjectHistoryOfEmployee(employeeDetails, employeeDetailsDTO);
			}
		});

		ProjectDetails savedProject = projectDetailsRepository.save(projectDetails);
		log.info(ProjectConstant.PROJECT_DETAILS_ADD_SUCCESS);
		BeanUtils.copyProperties(savedProject, projectDetailsDTO);
		return projectDetailsDTO;
	}

	/**
	 * This method is used to update the project
	 * 
	 * @param projectDetailsDTO
	 * @return ProjectDetailsDTO
	 */
	public ProjectDetailsDTO updateProject(ProjectDetailsDTO projectDetailsDTO) {
		Optional<ProjectDetails> projectDetailsOptional = projectDetailsRepository
				.findById(projectDetailsDTO.getProjectId());
		log.info(ProjectConstant.INSIDE_UPDATE_PROJECT_METHOD_OF_PROJECT_SERVICE);
		if (projectDetailsOptional.isPresent()) {
			ProjectDetails projectDetails = projectDetailsOptional.get();
			Map<String, String> projectMembers = projectDetails.getProjectMembers();
			BeanUtils.copyProperties(projectDetailsDTO, projectDetails);
			Map<String, String> newProjectMembers = new HashMap<>();

			List<EmployeeDetails> removedProjectMembers = new ArrayList<>();
			projectMembers.entrySet().forEach(a -> {
				if (a.getValue().equalsIgnoreCase(RolesConstant.PROJECT_MANAGER)
						|| a.getValue().equalsIgnoreCase(RolesConstant.PROJECT_LEAD)
						|| a.getValue().equalsIgnoreCase(RolesConstant.BACKEND_TEAM_LEAD)
						|| a.getValue().equalsIgnoreCase(RolesConstant.FRONTEND_TEAM_LEAD)) {
					EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeNo(a.getKey());
					if (employeeDetails != null) {
						List<ProjectHistory> employeeProjectHistory = employeeDetails.getEmployeeProjectHistory();

						employeeProjectHistory.stream().forEach(x -> {
							if (x.getProjectHistoryId().equals(projectDetailsDTO.getProjectId())) {
								x.setProjectRelievingDate(LocalDate.now());
								x.setStatus("Relieved");
								removedProjectMembers.add(employeeDetails);
								newProjectMembers.remove(a.getKey(), a.getValue());
							}
						});

					}

				}
			});
			employeeDetailsRepository.saveAll(removedProjectMembers);
			newProjectMembers.putAll(projectDetailsDTO.getProjectMembers());
			List<Entry<String, String>> backendTeamLead = newProjectMembers.entrySet().stream()
					.filter(x -> x.getValue().equalsIgnoreCase(RolesConstant.BACKEND_TEAM_LEAD))
					.collect(Collectors.toList());

			List<Entry<String, String>> frontendTeamLead = newProjectMembers.entrySet().stream()
					.filter(x -> x.getValue().equalsIgnoreCase(RolesConstant.FRONTEND_TEAM_LEAD))
					.collect(Collectors.toList());

			newProjectMembers.entrySet().forEach(c -> {
				EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeNo(c.getKey());
				if (employeeDetails != null && c.getValue().equalsIgnoreCase(RolesConstant.PROJECT_MANAGER)) {
					EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO(employeeDetails.getEmployeeNo(),
							new ProjectHistoryDTO(projectDetails.getProjectId(), "1", "p1", "working", c.getValue(),
									LocalDate.now(), null, c.getKey()));
					setProjectHistoryOfEmployee(employeeDetails, employeeDetailsDTO);
				} else if (c.getValue().equalsIgnoreCase(RolesConstant.PROJECT_LEAD)) {
					EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO(employeeDetails.getEmployeeNo(),
							new ProjectHistoryDTO(projectDetails.getProjectId(), "1", "p1", "working", c.getValue(),
									LocalDate.now(), null, projectDetailsDTO.getProjectManager()));
					setProjectHistoryOfEmployee(employeeDetails, employeeDetailsDTO);

				} else if (c.getValue().equalsIgnoreCase(RolesConstant.BACKEND_TEAM_LEAD)
						|| c.getValue().equalsIgnoreCase(RolesConstant.FRONTEND_TEAM_LEAD)) {
					EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO(employeeDetails.getEmployeeNo(),
							new ProjectHistoryDTO(projectDetails.getProjectId(), "1", "p1", "working", c.getValue(),
									LocalDate.now(), null, projectDetailsDTO.getProjectLead()));
					setProjectHistoryOfEmployee(employeeDetails, employeeDetailsDTO);
				} else if (c.getValue().equalsIgnoreCase(RolesConstant.BACKEND_DEVELOPER)) {
					employeeDetails.getEmployeeProjectHistory().forEach(x -> {
						if (x.getProjectId().equalsIgnoreCase(projectDetails.getProjectId())) {
							x.setReportingTo(backendTeamLead.get(0).getKey());
							employeeDetailsRepository.save(employeeDetails);
						}
					});
				} else if (c.getValue().equalsIgnoreCase(RolesConstant.FRONTEND_DEVELOPER)) {
					employeeDetails.getEmployeeProjectHistory().forEach(x -> {
						if (x.getProjectId().equalsIgnoreCase(projectDetails.getProjectId())) {
							x.setReportingTo(frontendTeamLead.get(0).getKey());
							employeeDetailsRepository.save(employeeDetails);
						}
					});
				}
			});

			ProjectDetails savedProjectDetails = projectDetailsRepository.save(projectDetails);
			BeanUtils.copyProperties(savedProjectDetails, projectDetailsDTO);
			return projectDetailsDTO;
		}

	  	return projectDetailsDTO;

	}

	/**
	 * This method is used to fetch all projects
	 * 
	 * @return List<ProjectDetailsDTO>
	 */
	public List<ProjectDetailsDTO> getAllProjects() {
		List<ProjectDetails> getProject = projectDetailsRepository.findAll();

		log.info(ProjectConstant.PROJECT_LIST_FETCHED_SUCCESS);
		return getProject.stream().map(e -> {
			List<String> employeeIds = new ArrayList<>();
			employeeIds.addAll(e.getProjectMembers().keySet());
			List<EmployeeDetails> employeeList = employeeDetailsRepository.findByEmployeeNoIn(employeeIds);
			List<GetEmployeeDto> projectMembers = employeeList.stream().map(a -> {
				GetEmployeeDto employeeDto = new GetEmployeeDto();
				BeanUtils.copyProperties(a, employeeDto);
				return employeeDto;
			}).collect(Collectors.toList());

			ProjectDetailsDTO detailsDTO = new ProjectDetailsDTO();
			BeanUtils.copyProperties(e, detailsDTO);
			detailsDTO.setProjectTeamMembers(projectMembers);
			return detailsDTO;
		}).collect(Collectors.toList());

	}

	/**
	 * This method is used to fetch all employees and projects
	 * 
	 * @return GetAllEmployeeProjectDto
	 */
	public GetAllEmployeeProjectDto getAllEmpsProjects() {

		log.info(ProjectConstant.INSIDE_GET_ALL_EMPLOYEE_AND_PROJECT_API_OF_PROJECT_SERVICE);

		log.info(ProjectConstant.FINDING_ALL_EMPLOYEE_FROM_EMPLOYEE_REPOSITORY);
		List<EmployeeDetails> getAllEmpsDetails = employeeDetailsRepository.findAll();

		log.info(ProjectConstant.FINDING_ALL_PROJECT_FROM_PROJECT_REPOSITORY);
		List<ProjectDetails> getAllProjectsDetails = projectDetailsRepository.findAll();

		List<GetEmployeeDto> getAllEmployeeDtoList = getAllEmpsDetails.stream().map(emplyeeDetails -> {
			GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
			BeanUtils.copyProperties(emplyeeDetails, getEmployeeDto);
			return getEmployeeDto;
		}).collect(Collectors.toList());

		List<GetProjectDto> getAllProjectDtoList = getAllProjectsDetails.stream().map(projectDetails -> {
			Map<String, String> teamLeads = projectDetails.getProjectMembers().entrySet().stream()
					.filter(i -> i.getValue().equalsIgnoreCase(RolesConstant.BACKEND_TEAM_LEAD)
							|| i.getValue().equalsIgnoreCase(RolesConstant.FRONTEND_TEAM_LEAD))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

			List<String> employeeNoList = new ArrayList<>(teamLeads.keySet());

			List<EmployeeDetailsDTO> teamLeadDTOList = employeeDetailsRepository.findByEmployeeNoIn(employeeNoList)
					.stream().map(teamLead -> {
						EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
						BeanUtils.copyProperties(teamLead, employeeDetailsDTO);
						return employeeDetailsDTO;
					}).collect(Collectors.toList());
			GetProjectDto getProjectDto = new GetProjectDto();
			getProjectDto.setLeadList(teamLeadDTOList);
			BeanUtils.copyProperties(projectDetails, getProjectDto);
			return getProjectDto;
		}).collect(Collectors.toList());

		GetAllEmployeeProjectDto getAllEmployeeProjectDto = new GetAllEmployeeProjectDto();
		getAllEmployeeProjectDto.setGetEmployeeDtoList(getAllEmployeeDtoList);
		getAllEmployeeProjectDto.setGetProjectDtoList(getAllProjectDtoList);
		return getAllEmployeeProjectDto;

	}

	/**
	 * This method is used to delete the project details
	 * 
	 * @param projectId
	 * @return String
	 */
	public String deleteProject(String projectId) {
		log.info(ProjectConstant.INSIDE_DELETE_PROJECT_API_OF_PROJECT_SERVICE);
		Optional<ProjectDetails> findByProjectId = projectDetailsRepository.findById(projectId);
		if (findByProjectId.isPresent()) {
			log.info(ProjectConstant.PROJECT_DETAILS_GET_SUCCESS);
			Set<String> keySet = findByProjectId.get().getProjectMembers().keySet();
			List<EmployeeDetails> employeeList = employeeDetailsRepository.findByEmployeeNoIn(new ArrayList<>(keySet));
			employeeList.stream().forEach(i -> {
				i.getEmployeeProjectHistory().stream().filter(j -> j.getProjectId().equalsIgnoreCase(projectId))
						.forEach(k -> {
							k.setStatus("Relieved");
							k.setProjectRelievingDate(LocalDate.now());
						});
			});

			employeeDetailsRepository.saveAll(employeeList);
			projectDetailsRepository.deleteById(projectId);
			return ProjectConstant.DELETED_THE_PROJECT_DETAILS_SUCCESS;
		} else {
			log.error(ProjectConstant.PROJECT_DETAILS_GET_FAILURE);
			throw new RecordNotFoundException(ProjectConstant.PROJECT_DETAILS_GET_FAILURE);
		}

	}

	/**
	 * This method is used to map project and employee
	 * 
	 * @param getProjectDto
	 * @return GetProjectDto
	 */
	public GetProjectDto mapProjectAndEmployees(GetProjectDto getProjectDto) {
		log.info(ProjectConstant.INSIDE_MAP_PROJECT_EMPLOYEE_METHOD_OF_PROJECT_SERVICE);

		Optional<ProjectDetails> projectDetailsOptional = projectDetailsRepository
				.findById(getProjectDto.getProjectId());
		if (projectDetailsOptional.isEmpty())
			throw new RecordNotFoundException(ProjectConstant.PROJECT_DETAILS_GET_FAILURE);
		else {
			log.info(ProjectConstant.PROJECT_DETAILS_GET_SUCCESS);
			ProjectDetails projectDetails = projectDetailsOptional.get();
			Set<Entry<String, String>> entrySet = projectDetails.getProjectMembers().entrySet();
			Map<String, String> newProjectMembers = new HashMap<>();
			List<EmployeeDetailsDTO> projectTeamMembersDTOList = getProjectDto.getLeadList();
			projectTeamMembersDTOList.forEach(a -> {
				EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeNo(a.getEmployeeNo());
				if (employeeDetails == null)
					throw new RecordNotFoundException(
							"Employee Details With Employee No" + a.getEmployeeNo() + " Not Found");
				else {
					log.info(EmployeeConstant.EMPLOYEE_FETCHED_SUCCESS);
					if (projectDetails.getProjectMembers().isEmpty()) {
						newProjectMembers.put(a.getEmployeeNo(), a.getRole());
					} else {
						for (Entry<String, String> entry : entrySet) {
							if (!entry.getKey().equalsIgnoreCase(a.getEmployeeNo())) {
								newProjectMembers.put(a.getEmployeeNo(), a.getRole());
							}
						}
					}
				}
				setProjectHistoryOfEmployee(employeeDetails, a);
			});
			projectDetails.getProjectMembers().putAll(newProjectMembers);
			projectDetailsRepository.save(projectDetails);
		}
		return getProjectDto;
	}

	/**
	 * This method is used to set project history of an employee
	 * 
	 * @param employeeDetails
	 * @param employeeDetailsDTO
	 */
	private void setProjectHistoryOfEmployee(EmployeeDetails employeeDetails, EmployeeDetailsDTO employeeDetailsDTO) {
		ProjectHistory projectHistory;
		if (employeeDetails.getEmployeeProjectHistory().get(0).getProjectId().isBlank()
				|| employeeDetails.getEmployeeProjectHistory().get(0).getProjectId()
						.equals(employeeDetailsDTO.getEmployeeProjectHistory().getProjectId())) {
			projectHistory = new ProjectHistory();
			BeanUtils.copyProperties(employeeDetailsDTO.getEmployeeProjectHistory(), projectHistory);
			employeeDetails.setEmployeeProjectHistory(Arrays.asList(projectHistory));
		} else {
			projectHistory = new ProjectHistory();
			BeanUtils.copyProperties(employeeDetailsDTO.getEmployeeProjectHistory(), projectHistory);
			employeeDetails.getEmployeeProjectHistory().add(projectHistory);
		}
		employeeDetailsRepository.save(employeeDetails);
	}

	/**
	 * This method is used to relieve employee from the project
	 * 
	 * @param employeeDto
	 * @return String
	 */
	public String relieveEmployeeFromProject(GetEmployeeDto employeeDto) {
		log.info(ProjectConstant.INSIDE_RELIEVE_EMPLOYEE_FROM_PROJECT_API_OF_PROJECT_CONTROLLER);
		EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeNo(employeeDto.getEmployeeNo());
		ProjectDetails projectDetails = projectDetailsRepository.findByProjectId(employeeDto.getProjectId());
		if (employeeDetails != null && projectDetails != null) {
			log.info(ProjectConstant.FETCHED_PROJECT_DETAILS_FROM_PROJECT_REPOSITORY);
			log.info(EmployeeConstant.EMPLOYEE_FETCHED_SUCCESS);

			employeeDetails.getEmployeeProjectHistory().stream()
					.filter(i -> i.getProjectId().equalsIgnoreCase(projectDetails.getProjectId())).forEach(i -> {
						i.setProjectRelievingDate(LocalDate.now());
						i.setStatus("Relieved");
						if (i.getRole().equals(RolesConstant.PROJECT_LEAD))
							projectDetails.setProjectLead("");
						else if (i.getRole().equals(RolesConstant.PROJECT_MANAGER))
							projectDetails.setProjectManager("");

					});
			employeeDetailsRepository.save(employeeDetails);

			projectDetails.getProjectMembers().entrySet()
					.removeIf(j -> j.getKey().equalsIgnoreCase(employeeDetails.getEmployeeNo()));
			projectDetailsRepository.save(projectDetails);

			return employeeDetails.getEmployeeFirstName() + " " + employeeDetails.getEmployeeMiddleName() + " "
					+ employeeDetails.getEmployeeLastName() + " is relived from the project "
					+ projectDetails.getProjectName() + " successfully";
		} else
			throw new RecordNotFoundException(ProjectConstant.RECORD_NOT_FOUND);

	}

}
