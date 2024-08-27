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
import com.te.projectmanagementtool.dto.GetProjectHierarchyDTO;
import com.te.projectmanagementtool.dto.ProjectLeadListDTO;
import com.te.projectmanagementtool.service.EmployeeDetailsService;
import com.te.projectmanagementtool.util.APIResponse;

@SpringBootTest
public class EmployeeDetailsControllerTest {

	@Autowired
	WebApplicationContext applicationContext;

	@MockBean
	EmployeeDetailsService employeeService;

	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
	}

	@Test
	void addEmployeeTest_Ok() throws UnsupportedEncodingException, Exception {
		EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
		employeeDetailsDTO.setEmployeeId("TY200");
		employeeDetailsDTO.setEmployeeFirstName("Sushma");

		when(employeeService.addEmployee(employeeDetailsDTO)).thenReturn(employeeDetailsDTO);
		String contentAsString = mockMvc
				.perform(post("/employee/add").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsBytes(employeeDetailsDTO)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, readValue.isError());
	}

	@Test
	void addEmployeeTest_NotFound() throws UnsupportedEncodingException, Exception {
		EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
		employeeDetailsDTO.setEmployeeId(null);
		employeeDetailsDTO.setEmployeeFirstName(null);

		when(employeeService.addEmployee(employeeDetailsDTO)).thenReturn(null);
		String contentAsString = mockMvc
				.perform(post("/employee/add").accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsBytes(employeeDetailsDTO)))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, readValue.isError());
	}

	@Test
	void getAllEmplyeeListTest_Ok() throws UnsupportedEncodingException, Exception {
		EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
		employeeDetailsDTO.setEmployeeId("Tyss100");
		employeeDetailsDTO.setEmployeeFirstName("Reshma");

		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		employeeDetailsList.add(employeeDetailsDTO);
		when(employeeService.getAllEmployee()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all").accept(MediaType.ALL).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());
	}

	@Test
	void getAllEmplyeeListTest_NotFound() throws UnsupportedEncodingException, Exception {
		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		when(employeeService.getAllEmployee()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all").accept(MediaType.ALL).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, response.isError());
	}

	@Test
	void getAllProjectManagersTest_Ok() throws UnsupportedEncodingException, Exception {
		EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
		employeeDetailsDTO.setEmployeeId("Ty2000");
		employeeDetailsDTO.setEmployeeFirstName("Sushma");
		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		employeeDetailsList.add(employeeDetailsDTO);
		when(employeeService.getAllProjectManagers()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all/project-manager").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());
	}

	@Test
	void getAllProjectManagersTest_NotFound() throws UnsupportedEncodingException, Exception {
		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		when(employeeService.getAllEmployee()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all/project-manager").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, response.isError());
	}

	@Test
	void getAllProjectLeadsTest_Ok() throws UnsupportedEncodingException, Exception {
		EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
		employeeDetailsDTO.setEmployeeId("Ty2000");
		employeeDetailsDTO.setEmployeeFirstName("Sushma");
		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		employeeDetailsList.add(employeeDetailsDTO);
		when(employeeService.getAllProjectLeads()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all/project-lead").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());
	}

	@Test
	void getAllProjectLeadsTest_NotFound() throws UnsupportedEncodingException, Exception {
		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		when(employeeService.getAllProjectLeads()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all/project-lead").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, response.isError());
	}

	@Test
	void getHierarchyTest_Ok() throws UnsupportedEncodingException, Exception {
		GetProjectHierarchyDTO getProjectHierarchyDTO = new GetProjectHierarchyDTO();
		getProjectHierarchyDTO.setProjectManagerEmployeeNo("Ty500");
		getProjectHierarchyDTO.setProjectManagerName("Veeru");
		List<ProjectLeadListDTO> projectLeadList = new ArrayList<>();
		getProjectHierarchyDTO.setProjectLeadList(projectLeadList); 
		ProjectLeadListDTO projectLead = new ProjectLeadListDTO();
		projectLeadList.add(projectLead);
		when(employeeService.getHierarchyMain(Mockito.anyString())).thenReturn(getProjectHierarchyDTO);
		String contentAsString = mockMvc
				.perform(get("/employee/get/hierarchy/ty122").accept(MediaType.ALL)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, readValue.isError());
	}

	@Test
	void getHierarchyTest_NotFound() throws UnsupportedEncodingException, Exception {
		GetProjectHierarchyDTO getProjectHierarchyDTO = null;
		List<ProjectLeadListDTO> projectLeadList = new ArrayList<>();
		ProjectLeadListDTO projectLead = null;
		projectLeadList.add(projectLead);
		when(employeeService.getHierarchyMain(Mockito.anyString())).thenReturn(getProjectHierarchyDTO);
		String contentAsString = mockMvc
				.perform(get("/employee/get/hierarchy/ty122").accept(MediaType.ALL)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse readValue = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, readValue.isError());
	}
	
	@Test
	void deleteEmployeeTest() throws UnsupportedEncodingException, Exception {
		when(employeeService.deleteEmployee("Ty100")).thenReturn("delete");
		String contentAsString = mockMvc
				.perform(delete("/employee/delete/{employeeId}", "100").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());
	}
	
	@Test
	void getAllTeamLeadsTest_Ok() throws UnsupportedEncodingException, Exception {
		EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
		employeeDetailsDTO.setEmployeeId("Ty2000");
		employeeDetailsDTO.setEmployeeFirstName("Sushma");
		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		employeeDetailsList.add(employeeDetailsDTO);
		when(employeeService.getAllTeamLeads()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all/team-lead").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(false, response.isError());
	}
	
	@Test
	void  getAllTeamLeadsTest_NotFound() throws UnsupportedEncodingException, Exception {
		ArrayList<EmployeeDetailsDTO> employeeDetailsList = new ArrayList<>();
		when(employeeService.getAllTeamLeads()).thenReturn(employeeDetailsList);
		String contentAsString = mockMvc
				.perform(get("/employee/get-all/project-lead").accept(MediaType.ALL)
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		APIResponse response = objectMapper.readValue(contentAsString, APIResponse.class);
		assertEquals(true, response.isError());
	}
	
	
	
	
	
	
	
	
}
