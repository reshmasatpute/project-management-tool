/*
 * package com.te.projectmanagementtool.service;
 * 
 * import static org.assertj.core.api.Assertions.assertThat; import static
 * org.junit.Assert.assertNull; import static
 * org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.mockito.Mockito.verify; import static org.mockito.Mockito.when;
 * 
 * import java.util.ArrayList; import java.util.List; import java.util.Optional;
 * import java.util.stream.Collectors; import java.util.stream.Stream;
 * 
 * import org.junit.jupiter.api.Test; import org.junit.runner.RunWith; import
 * org.mockito.Mockito; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.test.context.junit4.SpringRunner;
 * 
 * import com.te.projectmanagementtool.constant.EmployeeConstant; import
 * com.te.projectmanagementtool.dto.EmployeeDetailsDTO; import
 * com.te.projectmanagementtool.dto.ProjectHistoryDTO; import
 * com.te.projectmanagementtool.entity.EmployeeDetails; import
 * com.te.projectmanagementtool.entity.ProjectHistory; import
 * com.te.projectmanagementtool.repository.EmployeeDetailsRepository;
 * 
 * @RunWith(SpringRunner.class)
 * 
 * @SpringBootTest public class EmployeeDetailsServiceTest {
 * 
 * @Autowired private EmployeeDetailsService employeeDetailsService;
 * 
 * @MockBean private EmployeeDetailsRepository employeeDetailsRepository;
 * 
 * @Test public void addEmployeeTest() { ProjectHistoryDTO projectHistoryDTO=new
 * ProjectHistoryDTO(); projectHistoryDTO.setProjectId("PI003");
 * projectHistoryDTO.setPriority("High");
 * projectHistoryDTO.setRole("Project Lead");
 * 
 * EmployeeDetailsDTO detailsDTO=new EmployeeDetailsDTO();
 * detailsDTO.setEmployeeFirstName("Ashish");
 * detailsDTO.setEmployeeLastName("Kumar");
 * detailsDTO.setRole("Senior Developer");
 * detailsDTO.setEmployeeProjectHistory(projectHistoryDTO);
 * 
 * ProjectHistory projectHistory=new ProjectHistory();
 * projectHistory.setProjectId("PI003"); projectHistory.setPriority("High");
 * projectHistory.setRole("Project Lead");
 * 
 * List<ProjectHistory> projectHistories=new ArrayList<>();
 * projectHistories.add(projectHistory);
 * 
 * EmployeeDetails details=new EmployeeDetails();
 * details.setEmployeeFirstName("Ashish"); details.setEmployeeLastName("Kumar");
 * details.setRole("Senior Developer");
 * details.setEmployeeProjectHistory(projectHistories);
 * 
 * Optional<EmployeeDetails> empdetails = Optional.of(details);
 * Mockito.when(employeeDetailsRepository.findById(detailsDTO.getEmployeeId())).
 * thenReturn(empdetails); assertThat(empdetails.isPresent());
 * 
 * when(employeeDetailsRepository.save(details)).thenReturn(details);
 * assertEquals(detailsDTO, employeeDetailsService.addEmployee(detailsDTO));
 * 
 * ProjectHistoryDTO projectHistoryDTO2=new ProjectHistoryDTO();
 * projectHistoryDTO2.setProjectId("PI004");
 * projectHistoryDTO2.setPriority("Medium");
 * projectHistoryDTO2.setRole("Project Lead");
 * 
 * EmployeeDetailsDTO detailsDTO2=new EmployeeDetailsDTO();
 * detailsDTO2.setEmployeeFirstName("Vinay");
 * detailsDTO2.setEmployeeLastName("Kumar");
 * detailsDTO2.setRole("Senior Developer");
 * detailsDTO2.setEmployeeProjectHistory(projectHistoryDTO2);
 * 
 * ProjectHistory projectHistory2=new ProjectHistory();
 * projectHistory2.setProjectId("PI004"); projectHistory2.setPriority("Medium");
 * projectHistory2.setRole("Project Lead");
 * 
 * List<ProjectHistory> projectHistories2=new ArrayList<>();
 * projectHistories2.add(projectHistory2);
 * 
 * EmployeeDetails details2=new EmployeeDetails();
 * details2.setEmployeeFirstName("Vinay");
 * details2.setEmployeeLastName("Kumar"); details2.setRole("Senior Developer");
 * details2.setEmployeeProjectHistory(projectHistories2);
 * 
 * Optional<EmployeeDetails> empdetails2 = Optional.empty();
 * Mockito.when(employeeDetailsRepository.findById(detailsDTO2.getEmployeeId()))
 * .thenReturn(empdetails2); assertThat(empdetails2.isEmpty());
 * 
 * when(employeeDetailsRepository.save(details2)).thenReturn(details2);
 * assertEquals(detailsDTO2, employeeDetailsService.addEmployee(detailsDTO2)); }
 * 
 * @Test public void getAllEmployeeTest() { EmployeeDetails details = new
 * EmployeeDetails(); details.setEmployeeFirstName("Rohit");
 * details.setEmployeeLastName("Sharma"); details.setRole("Developer");
 * EmployeeDetails details2 = new EmployeeDetails();
 * details2.setEmployeeFirstName("Virat");
 * details2.setEmployeeLastName("Kohli"); details2.setRole("Tester");
 * when(employeeDetailsRepository.findAll()).thenReturn(Stream.of(details,
 * details2).collect(Collectors.toList())); List<EmployeeDetails>
 * employeeDetailsList = new ArrayList<>(); employeeDetailsList.add(details);
 * employeeDetailsList.add(details2);
 * assertEquals(employeeDetailsList.get(0).getEmployeeFirstName(),
 * employeeDetailsService.getAllEmployee().get(0).getEmployeeFirstName()); }
 * 
 * @Test public void getAllProjectManagersTest() { EmployeeDetailsDTO
 * detailsDTO=new EmployeeDetailsDTO(); detailsDTO.setEmployeeFirstName("Ram");
 * detailsDTO.setEmployeeLastName("Kapoor");
 * detailsDTO.setRole("Project Manager"); EmployeeDetailsDTO detailsDTO2=new
 * EmployeeDetailsDTO(); detailsDTO2.setEmployeeFirstName("Virat");
 * detailsDTO2.setEmployeeLastName("Kohli"); detailsDTO2.setRole("Tester");
 * List<EmployeeDetailsDTO> detailsDTOs=new ArrayList<>();
 * detailsDTOs.add(detailsDTO); detailsDTOs.add(detailsDTO2);
 * when(employeeDetailsService.getAllEmployee()).thenReturn(Stream.of(detailsDTO
 * ,detailsDTO2).collect(Collectors.toList()));
 * //System.out.println(employeeDetailsService.getAllProjectManagers());
 * //assertNull(employeeDetailsService.getAllProjectManagers().get(1));
 * //employeeDetailsService.getAllProjectManagers().get(0).getRole().
 * equalsIgnoreCase("Project Manager"); //
 * System.out.println("dto :"+detailsDTO); // List<EmployeeDetailsDTO>
 * allEmployee = employeeDetailsService.getAllEmployee();
 * //allEmployee.contains(detailsDTO.getRole().equals("Project Manager"));
 * //assertEquals(,
 * allEmployee.contains(detailsDTO.getRole().equals("Project Manager")));
 * 
 * }
 * 
 * // @Test // public void getAllProjectLeadsTest() { //
 * List<EmployeeDetailsDTO> allEmployee =
 * employeeDetailsService.getAllEmployee(); // // } // // @Test // public void
 * getHierarchyMainTest() { // // }
 * 
 * @Test public void deleteEmployeeTest() { EmployeeDetails details = new
 * EmployeeDetails(); details.setEmployeeId("TY001");
 * details.setEmployeeFirstName("Rohit"); details.setEmployeeLastName("Sharma");
 * details.setRole("Developer");
 * 
 * Optional<EmployeeDetails> empdetails = Optional.of(details);
 * Mockito.when(employeeDetailsRepository.findById(details.getEmployeeId())).
 * thenReturn(empdetails); assertThat(empdetails.isPresent());
 * verify(employeeDetailsRepository).deleteById(details.getEmployeeId());
 * assertEquals(EmployeeConstant.EMPLOYEE_DELETE_SUCCESS,
 * employeeDetailsService.deleteEmployee(details.getEmployeeId())); }
 * 
 * }
 */