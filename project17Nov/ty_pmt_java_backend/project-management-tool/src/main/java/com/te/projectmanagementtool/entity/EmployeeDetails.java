package com.te.projectmanagementtool.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pmt_employee_details")
public class EmployeeDetails {

	@Transient
	public static final String SEQUENCE_NAME = "employee_sequence";

	@Id
	private String employeeId;

	@Indexed(unique = true)
	private String employeeNo;

	private String employeeFirstName;

	private String employeeMiddleName;

	private String employeeLastName;

	private String employeeOfficialEmailId;

	private String employeePersonalEmailId;

	private String employeeOfficialContactNumber;

	private String type;

	private String role;

	private List<ProjectHistory> employeeProjectHistory = new ArrayList<>();

	private List<String> employeeSkills = new ArrayList<>();

}
