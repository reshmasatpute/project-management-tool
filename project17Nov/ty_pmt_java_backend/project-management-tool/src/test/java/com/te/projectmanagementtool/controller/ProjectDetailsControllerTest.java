package com.te.projectmanagementtool.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.projectmanagementtool.dto.EmployeeDetailsDTO;
import com.te.projectmanagementtool.dto.GetAllEmployeeProjectDto;
import com.te.projectmanagementtool.dto.GetEmployeeDto;
import com.te.projectmanagementtool.dto.GetProjectDto;
import com.te.projectmanagementtool.dto.ProjectDetailsDTO;
import com.te.projectmanagementtool.entity.MilestoneDetails;
import com.te.projectmanagementtool.service.ProjectDetailsService;
import com.te.projectmanagementtool.util.APIResponse;

@SpringBootTest
public class ProjectDetailsControllerTest {
	@Autowired
	WebApplicationContext applicationContext;

	@MockBean
	ProjectDetailsService projectService;

	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
	}

	@Test
	void addProjectTest_Ok() throws UnsupportedEncodingException, Exception {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();

		ArrayList<GetEmployeeDto> arrayList = new ArrayList<>();
		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeFirstName("Sushma");
		getEmployeeDto.setEmployeeId("TY100");
		arrayList.add(getEmployeeDto);

		ArrayList<MilestoneDetails> arrayList2 = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneName("Amir");
		milestoneDetails.setMilestoneId("10");
		arrayList2.add(milestoneDetails);

		projectDetailsDTO.setProjectId("200");
		projectDetailsDTO.setProjectName("PMT");
		projectDetailsDTO.setMilestoneDetails(arrayList2);
		projectDetailsDTO.setProjectTeamMembers(arrayList);

		when(projectService.addProject(projectDetailsDTO)).thenReturn(projectDetailsDTO);
		String contentAsString = mockMvc
				.perform(post("/project/add").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsBytes(projectDetailsDTO)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, readValue.isError());
	}

	@Test
	void addProjectTest_NotFound() throws UnsupportedEncodingException, Exception {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("200");
		projectDetailsDTO.setProjectName("LMS");
		String writeValueAsString = objectMapper.writeValueAsString(projectDetailsDTO);
		when(projectService.addProject(projectDetailsDTO)).thenReturn(null);
		String contentAsString = mockMvc
				.perform(post("/project/add").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(writeValueAsString))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, readValue.isError());
	}

	@Test
	void updateProject_IsOk() throws Exception {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();

		ArrayList<GetEmployeeDto> arrayList = new ArrayList<>();
		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		getEmployeeDto.setEmployeeFirstName("Sushma");
		getEmployeeDto.setEmployeeId("TY100");
		arrayList.add(getEmployeeDto);

		ArrayList<MilestoneDetails> arrayList2 = new ArrayList<>();
		MilestoneDetails milestoneDetails = new MilestoneDetails();
		milestoneDetails.setMilestoneName("Reshma");
		milestoneDetails.setMilestoneId("10");
		arrayList2.add(milestoneDetails);

		projectDetailsDTO.setProjectId("200");
		projectDetailsDTO.setProjectName("HRMS");
		projectDetailsDTO.setMilestoneDetails(arrayList2);
		projectDetailsDTO.setProjectTeamMembers(arrayList);

		when(projectService.updateProject(projectDetailsDTO)).thenReturn(projectDetailsDTO);
		String contentAsString = mockMvc
				.perform(post("/project/update").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsBytes(projectDetailsDTO)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, readValue.isError());
	}

	@Test
	void updateProject_IsNotFound() throws UnsupportedEncodingException, Exception {
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		projectDetailsDTO.setProjectId("200");
		projectDetailsDTO.setProjectName("StrongerMe");
		String writeValueAsString = objectMapper.writeValueAsString(projectDetailsDTO);
		when(projectService.updateProject(projectDetailsDTO)).thenReturn(null);
		String contentAsString = mockMvc
				.perform(post("/project/update").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(writeValueAsString))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, readValue.isError());
	}

	@Test
	void getAllProjectsTest_NotFound() throws UnsupportedEncodingException, Exception {
		List<ProjectDetailsDTO> dto = new ArrayList<>();
		dto.isEmpty();
		ProjectDetailsDTO dtoDealis = new ProjectDetailsDTO();
		dtoDealis.setProjectId("125l");
		when(projectService.getAllProjects()).thenReturn(dto);
		String contentAsString = mockMvc
				.perform(get("/project/get-all").accept(MediaType.ALL).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, response.isError());
	}

	@Test
	void getAllProjectsTest_Ok() throws UnsupportedEncodingException, Exception {
		List<ProjectDetailsDTO> dto = new ArrayList<>();
		ProjectDetailsDTO dtoDealis = new ProjectDetailsDTO();
		dtoDealis.setProjectId("");
		dto.add(dtoDealis);
		when(projectService.getAllProjects()).thenReturn(dto);
		String contentAsString = mockMvc
				.perform(get("/project/get-all").accept(MediaType.ALL).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());
	}

	@Test
	void getAllEmpsProjectDetailsTest() throws UnsupportedEncodingException, Exception {
		GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
		GetProjectDto getProjectDto = new GetProjectDto();
		ArrayList<GetEmployeeDto> employeeList = new ArrayList<>();
		ArrayList<GetProjectDto> projectList = new ArrayList<>();
		getProjectDto.setProjectName("LMS");
		getEmployeeDto.setEmployeeFirstName("Reshma");
		employeeList.add(getEmployeeDto);
		projectList.add(getProjectDto);

		GetAllEmployeeProjectDto getAllEmployeeProjectDto = new GetAllEmployeeProjectDto();
		getAllEmployeeProjectDto.setGetEmployeeDtoList(employeeList);
		getAllEmployeeProjectDto.setGetProjectDtoList(projectList);

		when(projectService.getAllEmpsProjects()).thenReturn(getAllEmployeeProjectDto);
		String contentAsString = mockMvc
				.perform(get("/project/get-employees-projects").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());

	}

	@Test
	void deleteProjectTest() throws UnsupportedEncodingException, Exception {
		when(projectService.deleteProject("100")).thenReturn("delete");
		String contentAsString = mockMvc
				.perform(delete("/project/delete/{projectId}", "100").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());

	}

	@Test
	void mapProjectAndEmpTest_NotFound() throws Exception {
		GetProjectDto getProject = new GetProjectDto();
		getProject.setProjectId("100");
		getProject.setProjectName("MMCARD ");
		String writeValueAsString = objectMapper.writeValueAsString(getProject);
		when(projectService.mapProjectAndEmployees(getProject)).thenReturn(null);

		String contentAsString = mockMvc
				.perform(post("/project/map").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(writeValueAsString))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, readValue.isError());
	}

	@Test
	void mapProjectAndEmpTest_IsOk() throws Exception {
		GetProjectDto getProject = new GetProjectDto();
		List<EmployeeDetailsDTO> emp = new ArrayList<>();
		EmployeeDetailsDTO n = new EmployeeDetailsDTO();
		n.setEmployeeId("Tyc123");
		n.setEmployeeFirstName("ABC");
		emp.add(n);
		getProject.setProjectId("22");
		getProject.setProjectName("LMS");
		getProject.setLeadList(emp);
		when(projectService.mapProjectAndEmployees(getProject)).thenReturn(getProject);

		String contentAsString = mockMvc
				.perform(post("/project/map").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsBytes(getProject)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, readValue.isError());
	}

	@Test
	void relieveEmployeeFromProjectTestOk() throws UnsupportedEncodingException, Exception {
		GetEmployeeDto getEmployee = new GetEmployeeDto();
		getEmployee.setEmployeeId("101");
		getEmployee.setEmployeeFirstName("Reshma"); 
		when(projectService.relieveEmployeeFromProject(getEmployee)).thenReturn("success");

		String writeValueAsString = objectMapper.writeValueAsString(getEmployee);
		String contentAsString = mockMvc.perform(
				post("/project/relieve-employee").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(writeValueAsString))
				.andExpect(status().isOk() ).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, readValue.isError());

	}
	@Test
	void relieveEmployeeFromProjectTestNoFound() throws UnsupportedEncodingException, Exception {
		GetEmployeeDto getEmployee = new GetEmployeeDto();
		when(projectService.relieveEmployeeFromProject(Mockito.any())).thenReturn(null);

		String writeValueAsString = objectMapper.writeValueAsString(getEmployee);
		String contentAsString = mockMvc.perform(
				post("/project/relieve-employee").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(writeValueAsString))
				.andExpect(status().isNotFound() ).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, readValue.isError());

	} 

}
