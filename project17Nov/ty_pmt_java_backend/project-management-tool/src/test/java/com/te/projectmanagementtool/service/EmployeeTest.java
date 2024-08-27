package com.te.projectmanagementtool.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.te.projectmanagementtool.constant.EmployeeConstant;
import com.te.projectmanagementtool.dto.EmployeeDetailsDTO;
import com.te.projectmanagementtool.dto.ProjectHistoryDTO;
import com.te.projectmanagementtool.dto.UserDTO;
import com.te.projectmanagementtool.entity.EmployeeDetails;
import com.te.projectmanagementtool.entity.ProjectDetails;
import com.te.projectmanagementtool.entity.ProjectHistory;
import com.te.projectmanagementtool.repository.EmployeeDetailsRepository;
import com.te.projectmanagementtool.repository.ProjectDetailsRepository;

@SpringBootTest
public class EmployeeTest {

	@Autowired
	private EmployeeDetailsService employeeDetailsService;
	
	@MockBean
	private EmployeeDetailsRepository employeeDetailsRepository;
	
	@MockBean
	private ProjectDetailsRepository projectDetailsRepository;
	
	@MockBean
	private SequenceGeneratorService sequenceGeneratorService;
	
//	@Test
	public void addEmployeeTest() {
		ProjectHistoryDTO projectHistoryDTO=new ProjectHistoryDTO();
		projectHistoryDTO.setProjectId("PI003");
		projectHistoryDTO.setPriority("High");
		projectHistoryDTO.setRole("Project Lead");
		
		EmployeeDetailsDTO detailsDTO=new EmployeeDetailsDTO();
		detailsDTO.setEmployeeFirstName("Ashish");
		detailsDTO.setEmployeeLastName("Kumar");
		detailsDTO.setRole("Senior Developer");
		detailsDTO.setEmployeeProjectHistory(projectHistoryDTO);
		
		ProjectHistory projectHistory=new ProjectHistory();
		projectHistory.setProjectId("PI003");
		projectHistory.setPriority("High");
		projectHistory.setRole("Project Lead");
		
		List<ProjectHistory> projectHistories=new ArrayList<>();
		projectHistories.add(projectHistory);
		
		EmployeeDetails details=new EmployeeDetails();
		details.setEmployeeFirstName("Ashish");
		details.setEmployeeLastName("Kumar");
		details.setRole("Senior Developer");
		details.setEmployeeProjectHistory(projectHistories);
		
		Optional<EmployeeDetails> empdetails = Optional.of(details);
		Mockito.when(employeeDetailsRepository.findById(detailsDTO.getEmployeeId())).thenReturn(empdetails);
		assertThat(empdetails.isPresent());
		assertThat(empdetails.get().getEmployeeProjectHistory().get(0).getProjectId().isBlank());
		
		when(employeeDetailsRepository.save(details)).thenReturn(details);
		assertEquals(detailsDTO, employeeDetailsService.addEmployee(detailsDTO));
		
		ProjectHistoryDTO projectHistoryDTO4=new ProjectHistoryDTO();
		projectHistoryDTO4.setProjectId("PI004");
		projectHistoryDTO4.setPriority("High");
		projectHistoryDTO4.setRole("Project Lead");
		
		EmployeeDetailsDTO detailsDTO4=new EmployeeDetailsDTO();
		detailsDTO4.setEmployeeFirstName("Ashish");
		detailsDTO4.setEmployeeLastName("Kumar");
		detailsDTO4.setRole("Senior Developer");
		detailsDTO4.setEmployeeProjectHistory(projectHistoryDTO4);
		
		Optional<EmployeeDetails> empdetails4 = Optional.of(details);
		Mockito.when(employeeDetailsRepository.findById(detailsDTO4.getEmployeeId())).thenReturn(empdetails4);
		when(employeeDetailsRepository.save(details)).thenReturn(details);
		ProjectHistoryDTO projectHistoryDTO2 = new ProjectHistoryDTO();
		projectHistoryDTO2.setProjectId("123");
		
		ProjectHistory projectHistory2 = new ProjectHistory();
		projectHistory2.setProjectName("HRMS");
		
		assertThat(empdetails4.isPresent());
		assertThat(empdetails.get().getEmployeeProjectHistory().get(0).getProjectId().isBlank());
		EmployeeDetails employeeDetails = new EmployeeDetails();
		employeeDetails.setEmployeePersonalEmailId("reshu66@gmail.com");
		EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
		employeeDetailsDTO.setEmployeeProjectHistory(projectHistoryDTO2);
		employeeDetails.setEmployeeProjectHistory(projectHistories);
		employeeDetails.setEmployeeFirstName("Reshma");
	
		employeeDetailsDTO.setEmployeeId("Ty100");
		assertEquals(detailsDTO, employeeDetailsService.addEmployee(employeeDetailsDTO));
		
	}
	
	@Test
	public void addEmployeeTestNew() {
		ProjectHistoryDTO projectHistoryDTO2=new ProjectHistoryDTO();
		projectHistoryDTO2.setProjectId("PI004");
		projectHistoryDTO2.setPriority("Medium");
		projectHistoryDTO2.setRole("Project Lead");
		
		EmployeeDetailsDTO detailsDTO2=new EmployeeDetailsDTO();
		detailsDTO2.setEmployeeFirstName("Vinay");
		detailsDTO2.setEmployeeLastName("Kumar");
		detailsDTO2.setRole("Senior Developer");
		detailsDTO2.setEmployeeProjectHistory(projectHistoryDTO2);
		
		ProjectHistory projectHistory2=new ProjectHistory();
		projectHistory2.setProjectId("PI004");
		projectHistory2.setPriority("Medium");
		projectHistory2.setRole("Project Lead");
		
		List<ProjectHistory> projectHistories2=new ArrayList<>();
		projectHistories2.add(projectHistory2);
		
		EmployeeDetails details2=new EmployeeDetails();
		details2.setEmployeeFirstName("Vinay");
		details2.setEmployeeLastName("Kumar");
		details2.setRole("Senior Developer");
		details2.setEmployeeProjectHistory(projectHistories2);
		
		Optional<EmployeeDetails> empdetails2 = Optional.empty();
		Mockito.when(employeeDetailsRepository.findById(detailsDTO2.getEmployeeId())).thenReturn(empdetails2);
		assertThat(empdetails2.isEmpty());
		when(sequenceGeneratorService.getSequenceNumber(EmployeeDetails.SEQUENCE_NAME)).thenReturn(2);
		
		EmployeeDetails details3=new EmployeeDetails();
		details3.setEmployeeId(String.valueOf(2));
		details3.setEmployeeFirstName("Vinay");
		details3.setEmployeeLastName("Kumar");
		details3.setRole("Senior Developer");
		details3.setEmployeeProjectHistory(projectHistories2);
		
		when(employeeDetailsRepository.save(details3)).thenReturn(details3);
		
		EmployeeDetailsDTO detailsDTO3=new EmployeeDetailsDTO();
		detailsDTO3.setEmployeeId(String.valueOf(2));
		detailsDTO3.setEmployeeFirstName("Vinay");
		detailsDTO3.setEmployeeLastName("Kumar");
		detailsDTO3.setRole("Senior Developer");
		detailsDTO3.setEmployeeProjectHistory(projectHistoryDTO2);
		assertEquals(detailsDTO3, employeeDetailsService.addEmployee(detailsDTO2));
	
	}
	
	@Test
	public void getAllEmployeeTest() {
		ProjectHistory projectHistory=new ProjectHistory();
		projectHistory.setProjectId("TY04");
		projectHistory.setProjectHistoryId("PH12");
		
		List<ProjectHistory> histories=new ArrayList();
		histories.add(projectHistory);
		
		EmployeeDetails details = new EmployeeDetails();
		details.setEmployeeFirstName("Rohit");
		details.setEmployeeLastName("Sharma");
		details.setRole("Developer");
		details.setEmployeeProjectHistory(histories);
		
		List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
		employeeDetailsList.add(details);
		
		when(employeeDetailsRepository.findAll()).thenReturn(employeeDetailsList);
		ProjectDetails projectDetails= new ProjectDetails();
		projectDetails.setProjectId("TY04");
		projectDetails.setClientName("IBM");
		projectDetails.setProjectManager("Sailesh");
		projectDetails.setProjectName("HRMS");
		
		when(projectDetailsRepository.findByProjectId(details.getEmployeeProjectHistory().get(0).getProjectId())).thenReturn(projectDetails);
		assertNotNull(projectDetails);
		assertEquals(employeeDetailsList.get(0).getEmployeeFirstName(),employeeDetailsService.getAllEmployee().get(0).getEmployeeFirstName());

	}
	
	@Test
	public void getAllEmployeeTestNull() {
		ProjectHistory projectHistory=new ProjectHistory();
		projectHistory.setProjectId("TY04");
		projectHistory.setProjectHistoryId("PH12");
		
		List<ProjectHistory> histories=new ArrayList();
		histories.add(projectHistory);
		
		EmployeeDetails details = new EmployeeDetails();
		details.setEmployeeFirstName("Rohit");
		details.setEmployeeLastName("Sharma");
		details.setRole("Developer");
		details.setEmployeeProjectHistory(histories);
		
		List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
		employeeDetailsList.add(details);
		
		when(employeeDetailsRepository.findAll()).thenReturn(employeeDetailsList);
		
		when(projectDetailsRepository.findByProjectId(details.getEmployeeProjectHistory().get(0).getProjectId())).thenReturn(null);

		assertEquals(employeeDetailsList.get(0).getEmployeeFirstName(),employeeDetailsService.getAllEmployee().get(0).getEmployeeFirstName());
	}
	
	@Test
	public void deleteEmployeeTest() {
		ProjectHistory projectHistory=new ProjectHistory();
		projectHistory.setProjectId("TY04");
		projectHistory.setProjectHistoryId("PH12");
		
		List<ProjectHistory> histories=new ArrayList();
		histories.add(projectHistory);
		
		EmployeeDetails details = new EmployeeDetails();
		details.setEmployeeNo("TY001");
		details.setEmployeeFirstName("Rohit");
		details.setEmployeeLastName("Sharma");
		details.setRole("Developer");
		details.setEmployeeProjectHistory(histories);
		
		ProjectDetails projectDetails= new ProjectDetails();
		projectDetails.setProjectId("TY04");
		projectDetails.setClientName("IBM");
		projectDetails.setProjectManager("Sailesh");
		
		Mockito.when(employeeDetailsRepository.findByEmployeeNo(details.getEmployeeId())).thenReturn(details);
		when(projectDetailsRepository.findByProjectId(histories.get(0).getProjectId())).thenReturn(projectDetails);
		assertTrue(EmployeeConstant.EMPLOYEE_DELETE_SUCCESS.equals(employeeDetailsService.deleteEmployee(details.getEmployeeId())));
	}
	
	/*
	 * @Test public void deleteEmployeeNullTest() { ProjectHistory
	 * projectHistory=new ProjectHistory(); projectHistory.setProjectId("TY04");
	 * projectHistory.setProjectHistoryId("PH12");
	 * 
	 * List<ProjectHistory> histories=new ArrayList();
	 * histories.add(projectHistory);
	 * 
	 * EmployeeDetails details = new EmployeeDetails();
	 * details.setEmployeeNo("TY001"); details.setEmployeeProjectHistory(histories);
	 * 
	 * ProjectDetails projectDetails= new ProjectDetails();
	 * projectDetails.setProjectId("TY04"); projectDetails.setClientName("IBM");
	 * projectDetails.setProjectManager("Sailesh");
	 * 
	 * Mockito.when(employeeDetailsRepository.findByEmployeeNo(details.getEmployeeId
	 * ())).thenReturn(null);
	 * assertTrue(EmployeeConstant.EMPLOYEE_RECORD_NOT_FOUND.equals(
	 * employeeDetailsService.deleteEmployee(details.getEmployeeId()))); }
	 */
	
	@Test
	public void loginTest() {
		EmployeeDetails details = new EmployeeDetails();
		details.setEmployeeNo("TY002");
		details.setEmployeeFirstName("Santhosh");
		details.setEmployeeLastName("Kumar");
		
		UserDTO userDTO=new UserDTO();
		userDTO.setUserId("TY002");
		
		when(employeeDetailsRepository.findByEmployeeNo(userDTO.getUserId())).thenReturn(details);
		assertTrue(EmployeeConstant.EMPLOYEE_LOG_IN_SUCCESS.equals(employeeDetailsService.login(userDTO)));
	}
}
