package com.te.projectmanagementtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDto {

	private String employeeId;
	
	private String employeeNo;
	
	private String employeeFirstName;

	private String employeeMiddleName;

	private String employeeLastName;
	
	private String role;
	
	private String projectId;
	
}
