package com.te.projectmanagementtool.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.te.projectmanagementtool.constant.ProjectConstant;
import com.te.projectmanagementtool.dto.GetAllEmployeeProjectDto;
import com.te.projectmanagementtool.dto.GetEmployeeDto;
import com.te.projectmanagementtool.dto.GetProjectDto;
import com.te.projectmanagementtool.dto.ProjectDetailsDTO;
import com.te.projectmanagementtool.entity.EmployeeDetails;
import com.te.projectmanagementtool.entity.MilestoneDetails;
import com.te.projectmanagementtool.entity.ProjectDetails;
import com.te.projectmanagementtool.entity.ProjectHistory;
import com.te.projectmanagementtool.exception.RecordNotFoundException;
import com.te.projectmanagementtool.repository.EmployeeDetailsRepository;
import com.te.projectmanagementtool.repository.ProjectDetailsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectDetailsServiceTest {

	@Autowired
	private ProjectDetailsService projectDetailsService;

	@MockBean
	private ProjectDetailsRepository projectDetailsRepository;

	@MockBean
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Test
	public void addProjectTest_EmployeeDetails_shouldNotNull_ProjectManager() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("1", "Project Manager");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		milestoneList.add(milestoneDetails);

		List<GetEmployeeDto> getEmployeeList = new ArrayList<>();
		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeId("Ty30");
		getEmployeeDto.setEmployeeFirstName("Sushma");
		getEmployeeList.add(getEmployeeDto);

		projectDetailsDTO.setProjectId("12");
		projectDetailsDTO.setProjectName("PMT");
		projectDetailsDTO.setClientName("Test Yantra");
		projectDetailsDTO.setProjectMembers(hashMap);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectTeamMembers(getEmployeeList);
		projectDetailsDTO.setProjectDomain("");
		projectDetailsDTO.setProjectEndDate(LocalDate.now());
		projectDetailsDTO.setProjectLead("Rakesh");
		projectDetailsDTO.setProjectManager("Veeru");
		projectDetailsDTO.setProjectStartDate(LocalDate.now());
		projectDetailsDTO.setProjectStatus("Ongoing");

		ProjectHistory projectHistoryDTO = new ProjectHistory();
		projectHistoryDTO.setProjectId("100");
		projectHistoryDTO.setPriority("Highest");
		projectHistoryDTO.setRole("Backend Developer");

		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistoryDTO);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeNo("1");
		employeeDetails.setRole("Project Manager");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);

		ProjectDetails projectDetails = new ProjectDetails();
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);
		projectDetails.setProjectMembers(hashMap);
		projectDetails.setMilestoneDetails(milestoneList);

		Mockito.when(employeeDetailsRepository.findByEmployeeNo("1")).thenReturn(employeeDetails);
		when(employeeDetailsRepository.save(employeeDetails)).thenReturn(employeeDetails);
		when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);

		ProjectDetailsDTO projectDetailsDTO2 = projectDetailsService.addProject(projectDetailsDTO);

		assertEquals(projectDetailsDTO.getProjectId(), projectDetailsDTO2.getProjectId());
	}

	@Test
	public void addProjectTest_EmployeeDetails_shouldNotNull_ProjectLead() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "Project Lead");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		milestoneList.add(milestoneDetails);

		List<GetEmployeeDto> getEmployeeList = new ArrayList<>();
		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeId("Ty30");
		getEmployeeDto.setEmployeeFirstName("Sushma");
		getEmployeeList.add(getEmployeeDto);

		projectDetailsDTO.setProjectId("12");
		projectDetailsDTO.setProjectName("PMT");
		projectDetailsDTO.setClientName("Test Yantra");
		projectDetailsDTO.setProjectMembers(hashMap);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectTeamMembers(getEmployeeList);
		projectDetailsDTO.setProjectDomain("");
		projectDetailsDTO.setProjectEndDate(LocalDate.now());
		projectDetailsDTO.setProjectLead("Rakesh");
		projectDetailsDTO.setProjectManager("Veeru");
		projectDetailsDTO.setProjectStartDate(LocalDate.now());
		projectDetailsDTO.setProjectStatus("Ongoing");

		ProjectHistory projectHistoryDTO = new ProjectHistory();
		projectHistoryDTO.setProjectId("100");
		projectHistoryDTO.setPriority("Highest");
		projectHistoryDTO.setRole("Backend Developer");

		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistoryDTO);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeNo("2");
		employeeDetails.setRole("Project Lead");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);

		ProjectDetails projectDetails = new ProjectDetails();
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);
		projectDetails.setProjectMembers(hashMap);
		projectDetails.setMilestoneDetails(milestoneList);

		Mockito.when(employeeDetailsRepository.findByEmployeeNo("2")).thenReturn(employeeDetails);
		when(employeeDetailsRepository.save(employeeDetails)).thenReturn(employeeDetails);
		when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);

		ProjectDetailsDTO projectDetailsDTO2 = projectDetailsService.addProject(projectDetailsDTO);

		assertEquals(projectDetailsDTO.getProjectId(), projectDetailsDTO2.getProjectId());
	}

	@Test
	public void addProjectTest_EmployeeDetails_shouldNotNull_ProjectHistoryNull() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "Developer");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		milestoneList.add(milestoneDetails);

		List<GetEmployeeDto> getEmployeeList = new ArrayList<>();
		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeId("Ty30");
		getEmployeeDto.setEmployeeFirstName("Sushma");
		getEmployeeList.add(getEmployeeDto);

		projectDetailsDTO.setProjectId("12");
		projectDetailsDTO.setProjectName("PMT");
		projectDetailsDTO.setClientName("Test Yantra");
		projectDetailsDTO.setProjectMembers(hashMap);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectTeamMembers(getEmployeeList);
		projectDetailsDTO.setProjectDomain("");
		projectDetailsDTO.setProjectEndDate(LocalDate.now());
		projectDetailsDTO.setProjectLead("Rakesh");
		projectDetailsDTO.setProjectManager("Veeru");
		projectDetailsDTO.setProjectStartDate(LocalDate.now());
		projectDetailsDTO.setProjectStatus("Ongoing");

		ProjectHistory projectHistoryDTO = new ProjectHistory();
		projectHistoryDTO.setProjectId("");

		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistoryDTO);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeNo("2");
		employeeDetails.setRole("Developer");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);

		ProjectDetails projectDetails = new ProjectDetails();
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);
		projectDetails.setProjectMembers(hashMap);
		projectDetails.setMilestoneDetails(milestoneList);

		Mockito.when(employeeDetailsRepository.findByEmployeeNo("2")).thenReturn(employeeDetails);
		when(employeeDetailsRepository.save(employeeDetails)).thenReturn(employeeDetails);
		when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);

		ProjectDetailsDTO projectDetailsDTO2 = projectDetailsService.addProject(projectDetailsDTO);

		assertEquals(projectDetailsDTO.getProjectId(), projectDetailsDTO2.getProjectId());
	}

	@Test
	public void addProjectTest_EmployeeDetails_shouldNotNull_AnyRole() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "FrantEnd Developer");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		milestoneList.add(milestoneDetails);

		List<GetEmployeeDto> getEmployeeList = new ArrayList<>();
		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeId("Ty30");
		getEmployeeDto.setEmployeeFirstName("Sushma");
		getEmployeeList.add(getEmployeeDto);

		projectDetailsDTO.setProjectId("12");
		projectDetailsDTO.setProjectName("PMT");
		projectDetailsDTO.setClientName("Test Yantra");
		projectDetailsDTO.setProjectMembers(hashMap);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectTeamMembers(getEmployeeList);
		projectDetailsDTO.setProjectDomain("");
		projectDetailsDTO.setProjectEndDate(LocalDate.now());
		projectDetailsDTO.setProjectLead("Rakesh");
		projectDetailsDTO.setProjectManager("Veeru");
		projectDetailsDTO.setProjectStartDate(LocalDate.now());
		projectDetailsDTO.setProjectStatus("Ongoing");

		ProjectHistory projectHistoryDTO = new ProjectHistory();
		projectHistoryDTO.setProjectId("100");
		projectHistoryDTO.setPriority("Highest");
		projectHistoryDTO.setRole("Backend Developer");

		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistoryDTO);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeNo("2");
		employeeDetails.setRole("FrantEnd Developer");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);

		ProjectDetails projectDetails = new ProjectDetails();
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);
		projectDetails.setProjectMembers(hashMap);
		projectDetails.setMilestoneDetails(milestoneList);

		Mockito.when(employeeDetailsRepository.findByEmployeeNo("2")).thenReturn(employeeDetails);
		when(employeeDetailsRepository.save(employeeDetails)).thenReturn(employeeDetails);
		when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);

		ProjectDetailsDTO projectDetailsDTO2 = projectDetailsService.addProject(projectDetailsDTO);

		assertEquals(projectDetailsDTO.getProjectId(), projectDetailsDTO2.getProjectId());
	}

	@Test
	public void getProjectTest() {
		ProjectDetails details = new ProjectDetails();
		details.setProjectId("TY01");
		details.setClientName("Infosys");
		details.setProjectManager("Ramesh");
		ProjectDetails details2 = new ProjectDetails();
		details2.setProjectId("TY02");
		details2.setClientName("TCS");
		details2.setProjectManager("Krishna");
		when(projectDetailsRepository.findAll()).thenReturn(Stream.of(details, details2).collect(Collectors.toList()));
		assertEquals(2, projectDetailsService.getAllProjects().size());
	}

	@Test
	public void getAllEmpsProjectsTest() {
		EmployeeDetails details = new EmployeeDetails();
		details.setEmployeeFirstName("Rohit");
		details.setEmployeeLastName("Sharma");
		details.setRole("Developer");
		EmployeeDetails details2 = new EmployeeDetails();
		details2.setEmployeeFirstName("Virat");
		details2.setEmployeeLastName("Kohli");
		details2.setRole("Tester");
		when(employeeDetailsRepository.findAll()).thenReturn(Stream.of(details, details2).collect(Collectors.toList()));

		ProjectDetails details3 = new ProjectDetails();
		details3.setProjectId("TY03");
		details3.setClientName("Infosys");
		details3.setProjectManager("Rajesh");
		details3.setProjectName("LMS");
		ProjectDetails details4 = new ProjectDetails();
		details4.setProjectId("TY04");
		details4.setClientName("Wipro");
		details4.setProjectManager("Krish");
		details4.setProjectName("PMT");
		when(projectDetailsRepository.findAll()).thenReturn(Stream.of(details3, details4).collect(Collectors.toList()));

		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeFirstName("Rohit");
		getEmployeeDto.setEmployeeLastName("Sharma");
		GetEmployeeDto getEmployeeDto2 = new GetEmployeeDto();
		getEmployeeDto2.setEmployeeFirstName("Virat");
		getEmployeeDto2.setEmployeeLastName("Kohli");

		List<GetEmployeeDto> getEmployeeDtos = new ArrayList<>();
		getEmployeeDtos.add(getEmployeeDto);
		getEmployeeDtos.add(getEmployeeDto2);

		GetProjectDto getProjectDto = new GetProjectDto();
		getProjectDto.setProjectId("TY03");
		getProjectDto.setProjectName("LMS");
		GetProjectDto getProjectDto2 = new GetProjectDto();
		getProjectDto2.setProjectId("TY04");
		getProjectDto2.setProjectName("PMT");

		List<GetProjectDto> getProjectDtos = new ArrayList<>();
		getProjectDtos.add(getProjectDto);
		getProjectDtos.add(getProjectDto2);

		GetAllEmployeeProjectDto allEmployeeProjectDto = new GetAllEmployeeProjectDto();
		allEmployeeProjectDto.setGetEmployeeDtoList(getEmployeeDtos);
		allEmployeeProjectDto.setGetProjectDtoList(getProjectDtos);

		assertEquals(allEmployeeProjectDto.getGetEmployeeDtoList().get(0).getEmployeeFirstName(),
				projectDetailsService.getAllEmpsProjects().getGetEmployeeDtoList().get(0).getEmployeeFirstName());
		assertEquals(allEmployeeProjectDto.getGetProjectDtoList().get(0).getProjectId(),
				projectDetailsService.getAllEmpsProjects().getGetProjectDtoList().get(0).getProjectId());
	}

	@Test
	public void deleteProjectTest_ProjectIdIsPresent_ProjectLead() {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId("TY008");
		projectDetails.setProjectName("HMS");
		projectDetails.setClientName("AllStates");
		Map<String, String> map = new HashMap<>();
		map.put("1", "8");
		projectDetails.setProjectMembers(map);
		EmployeeDetails emp = new EmployeeDetails();
		ProjectHistory projectHistory = new ProjectHistory();
		List<ProjectHistory> arrayList2 = new ArrayList<>();
		projectHistory.setProjectId("eert");
		arrayList2.add(projectHistory);
		projectHistory.setProjectId(projectDetails.getProjectId());
		emp.setEmployeeProjectHistory(arrayList2);

		emp.setEmployeeFirstName("Sushma");
		List<EmployeeDetails> employeeList = new ArrayList<>();
		employeeList.add(emp);
		Set<String> keySet = projectDetails.getProjectMembers().keySet();
		List<String> arrayList3 = new ArrayList<>(keySet);
		Mockito.when(employeeDetailsRepository.findByEmployeeNoIn(arrayList3)).thenReturn(employeeList);
		ArrayList<ProjectDetails> arrayList = new ArrayList<>();
		arrayList.add(projectDetails);

		Mockito.when(projectDetailsRepository.findById(projectDetails.getProjectId()))
				.thenReturn(Optional.of(projectDetails));
		Mockito.when(projectDetailsRepository.saveAll(arrayList)).thenReturn(arrayList);
		Mockito.doNothing().when(projectDetailsRepository).deleteById("TY008");

		assertEquals(ProjectConstant.DELETED_THE_PROJECT_DETAILS_SUCCESS,
				projectDetailsService.deleteProject(projectDetails.getProjectId()));
		verify(projectDetailsRepository).deleteById("TY008");

	}

	@Test
	public void deleteProjectTest_shouldThrowException_WhenProjectIdIsNull() {
		String projectId = "ty50";
		Throwable exception = assertThrows(RecordNotFoundException.class,
				() -> projectDetailsService.deleteProject(projectId));

		assertEquals(ProjectConstant.PROJECT_DETAILS_GET_FAILURE, exception.getMessage());

	}

	@Test
	public void updateProjectTest_ProjectId_ShouldPresent_BackendTeam_Lead() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("2");
		projectDetailsDTO.setProjectName("LMS");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "BackEnd Team Lead");
		milestoneList.add(milestoneDetails);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectMembers(hashMap);

		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeFirstName("Rohit");
		getEmployeeDto.setEmployeeLastName("Sharma");
		getEmployeeDto.setEmployeeNo("2");
		List<GetEmployeeDto> getEmployeeDtoList = new ArrayList<>();
		getEmployeeDtoList.add(getEmployeeDto);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		String string = new String("Java Spring Boot");
		List<String> skillList = new ArrayList<>();
		skillList.add(string);

		ProjectHistory projectHistory = new ProjectHistory();
		projectHistory.setProjectHistoryId("2");
		projectHistory.setProjectId("2");
		projectHistory.setPriority("Highest");
		projectHistory.setRole("BackEnd Team Lead");
		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistory);

		employeeDetails.setEmployeeNo("2");
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);
		employeeDetails.setEmployeeSkills(skillList);

		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId("2");
		projectDetails.setProjectName("LMS");
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);

		Mockito.when(projectDetailsRepository.findById(projectDetails.getProjectId()))
				.thenReturn(Optional.of(projectDetails));
		Mockito.when(employeeDetailsRepository.findByEmployeeNo(employeeDetails.getEmployeeNo()))
				.thenReturn(employeeDetails);
		Mockito.when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);
		ProjectDetailsDTO updateProject = projectDetailsService.updateProject(projectDetailsDTO);
		assertEquals(projectDetailsDTO.getProjectId(), updateProject.getProjectId());

		when(projectDetailsService.updateProject(projectDetailsDTO)).thenReturn(projectDetailsDTO);
		assertEquals(updateProject, projectDetailsDTO);

	}

	@Test
	public void updateProjectTest_ProjectId_ShouldPresent() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("2");
		projectDetailsDTO.setProjectName("LMS");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "Project Lead");
		milestoneList.add(milestoneDetails);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectMembers(hashMap);

		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeFirstName("Rohit");
		getEmployeeDto.setEmployeeLastName("Sharma");
		getEmployeeDto.setEmployeeNo("2");
		List<GetEmployeeDto> getEmployeeDtoList = new ArrayList<>();
		getEmployeeDtoList.add(getEmployeeDto);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		String string = new String("Java Spring Boot");
		List<String> skillList = new ArrayList<>();
		skillList.add(string);

		ProjectHistory projectHistory = new ProjectHistory();
		projectHistory.setProjectHistoryId("2");
		projectHistory.setProjectId("2");
		projectHistory.setPriority("Highest");
		projectHistory.setRole("Project Lead");
		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistory);

		employeeDetails.setEmployeeNo("2");
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);
		employeeDetails.setEmployeeSkills(skillList);

		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId("2");
		projectDetails.setProjectName("LMS");
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);

		Mockito.when(projectDetailsRepository.findById(projectDetails.getProjectId()))
				.thenReturn(Optional.of(projectDetails));
		Mockito.when(employeeDetailsRepository.findByEmployeeNo(employeeDetails.getEmployeeNo()))
				.thenReturn(employeeDetails);
		Mockito.when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);
		ProjectDetailsDTO updateProject = projectDetailsService.updateProject(projectDetailsDTO);
		assertEquals(projectDetailsDTO.getProjectId(), updateProject.getProjectId());

	}

	@Test
	public void updateProjectTest_ProjectId_ShouldPresent_Backend_Developer() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("2");
		projectDetailsDTO.setProjectName("LMS");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "Backend Developer");
		hashMap.put("105", "Backend Team Lead");
		milestoneList.add(milestoneDetails);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectMembers(hashMap);

		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeId("2");
		getEmployeeDto.setEmployeeFirstName("Rohit");
		getEmployeeDto.setEmployeeLastName("Sharma");
		getEmployeeDto.setEmployeeNo("2");
		List<GetEmployeeDto> getEmployeeDtoList = new ArrayList<>();
		getEmployeeDtoList.add(getEmployeeDto);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		String string = new String("Java Spring Boot");
		List<String> skillList = new ArrayList<>();
		skillList.add(string);

		ProjectHistory projectHistory = new ProjectHistory();
		projectHistory.setProjectHistoryId("2");
		projectHistory.setProjectId("2");
		projectHistory.setPriority("Highest");
		projectHistory.setRole("Backend Developer");
		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistory);

		employeeDetails.setEmployeeNo("2");
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);
		employeeDetails.setEmployeeSkills(skillList);

		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId("2");
		projectDetails.setProjectName("LMS");
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);

		Mockito.when(projectDetailsRepository.findById(projectDetails.getProjectId()))
				.thenReturn(Optional.of(projectDetails));
		Mockito.when(employeeDetailsRepository.findByEmployeeNo(Mockito.any())).thenReturn(employeeDetails);
		Mockito.when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);
		ProjectDetailsDTO updateProject = projectDetailsService.updateProject(projectDetailsDTO);
		assertEquals(projectDetailsDTO.getProjectId(), updateProject.getProjectId());

	}

	@Test
	public void updateProjectTest_ProjectId_ShouldPresent_Frantend_Developer() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("2");
		projectDetailsDTO.setProjectName("LMS");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "Frontend Developer");
		hashMap.put("105", "Frontend Team Lead");
		milestoneList.add(milestoneDetails);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectMembers(hashMap);

		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeId("2");
		getEmployeeDto.setEmployeeFirstName("Rohit");
		getEmployeeDto.setEmployeeLastName("Sharma");
		getEmployeeDto.setEmployeeNo("2");
		List<GetEmployeeDto> getEmployeeDtoList = new ArrayList<>();
		getEmployeeDtoList.add(getEmployeeDto);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		String string = new String("Java Spring Boot");
		List<String> skillList = new ArrayList<>();
		skillList.add(string);

		ProjectHistory projectHistory = new ProjectHistory();
		projectHistory.setProjectHistoryId("2");
		projectHistory.setProjectId("2");
		projectHistory.setPriority("Highest");
		projectHistory.setRole("Frontend Developer");
		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistory);

		employeeDetails.setEmployeeNo("2");
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);
		employeeDetails.setEmployeeSkills(skillList);

		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId("2");
		projectDetails.setProjectName("LMS");
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);

		Mockito.when(projectDetailsRepository.findById(projectDetails.getProjectId()))
				.thenReturn(Optional.of(projectDetails));
		Mockito.when(employeeDetailsRepository.findByEmployeeNo(Mockito.any())).thenReturn(employeeDetails);
		Mockito.when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);
		ProjectDetailsDTO updateProject = projectDetailsService.updateProject(projectDetailsDTO);
		assertEquals(projectDetailsDTO.getProjectId(), updateProject.getProjectId());

		when(projectDetailsService.updateProject(projectDetailsDTO)).thenReturn(projectDetailsDTO);
		assertEquals(updateProject.getProjectId(), projectDetailsDTO.getProjectId());
	}

	@Test
	public void updateProjectTest_ProjectId_ShouldPresent_Project_Manager() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("2");
		projectDetailsDTO.setProjectName("LMS");
		List<MilestoneDetails> milestoneList = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneId("100");
		milestoneDetails.setMilestoneName("milestone");

		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("2", "Project Manager");
		milestoneList.add(milestoneDetails);
		projectDetailsDTO.setMilestoneDetails(milestoneList);
		projectDetailsDTO.setProjectMembers(hashMap);

		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeFirstName("Rohit");
		getEmployeeDto.setEmployeeLastName("Sharma");
		getEmployeeDto.setEmployeeNo("2");
		List<GetEmployeeDto> getEmployeeDtoList = new ArrayList<>();
		getEmployeeDtoList.add(getEmployeeDto);

		EmployeeDetails employeeDetails = new EmployeeDetails();
		String string = new String("Java Spring Boot");
		List<String> skillList = new ArrayList<>();
		skillList.add(string);

		ProjectHistory projectHistory = new ProjectHistory();
		projectHistory.setProjectHistoryId("2");
		projectHistory.setProjectId("2");
		projectHistory.setPriority("Highest");
		projectHistory.setRole("Project Manager");
		List<ProjectHistory> projectHistoryList = new ArrayList<>();
		projectHistoryList.add(projectHistory);

		employeeDetails.setEmployeeNo("2");
		employeeDetails.setEmployeeFirstName("Reshma");
		employeeDetails.setEmployeeProjectHistory(projectHistoryList);
		employeeDetails.setEmployeeSkills(skillList);

		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId("2");
		projectDetails.setProjectName("LMS");
		BeanUtils.copyProperties(projectDetailsDTO, projectDetails);

		Mockito.when(projectDetailsRepository.findById(projectDetails.getProjectId()))
				.thenReturn(Optional.of(projectDetails));
		Mockito.when(employeeDetailsRepository.findByEmployeeNo(employeeDetails.getEmployeeNo()))
				.thenReturn(employeeDetails);
		Mockito.when(projectDetailsRepository.save(Mockito.any())).thenReturn(projectDetails);
		ProjectDetailsDTO updateProject = projectDetailsService.updateProject(projectDetailsDTO);
		assertEquals(projectDetailsDTO.getProjectId(), updateProject.getProjectId());

	}

	@Test
	public void updateProjectWhenProjectNull() {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("2");
		projectDetailsDTO.setProjectName("LMS");

		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId("2");
		projectDetails.setProjectName("LMS");

		Mockito.when(projectDetailsRepository.findById(projectDetails.getProjectId())).thenReturn(Optional.empty());

		ProjectDetailsDTO updateProject = projectDetailsService.updateProject(projectDetailsDTO);
		assertEquals(projectDetailsDTO.getProjectId(), updateProject.getProjectId());
	}

}
