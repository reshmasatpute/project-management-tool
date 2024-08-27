package com.te.projectmanagementtool.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectLeadListDTO {
	
	private String employeeId;
	
	private String employeeName;
	
	private String role;
	
	private List<GetProjectDetailsDTO> projectList = new ArrayList<>();

}
