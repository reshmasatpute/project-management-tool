package com.te.projectmanagementtool.dto;

import java.util.ArrayList;
import java.util.List;

import com.te.projectmanagementtool.entity.ProjectHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsDTO {

	private String employeeId;

	private String employeeNo;

	private String employeeFirstName;

	private String employeeMiddleName;

	private String employeeLastName;

	private String employeeOfficialEmailId;

	private String employeePersonalEmailId;

	private String employeeOfficialContactNumber;
	
	private String type;
	
	private String role;

	private ProjectHistoryDTO employeeProjectHistory;
	
	private List<ProjectHistory> employeeProjectHistoryList;

	private List<String> employeeSkills = new ArrayList<>();

	public EmployeeDetailsDTO(String employeeNo, ProjectHistoryDTO employeeProjectHistory) {
		super();
		this.employeeNo = employeeNo;
		this.employeeProjectHistory = employeeProjectHistory;
	}
	
	


}
