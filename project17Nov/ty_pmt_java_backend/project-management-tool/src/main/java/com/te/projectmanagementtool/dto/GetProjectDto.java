package com.te.projectmanagementtool.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectDto {
	
	private String projectId;

	private String projectName;
	
	private List<EmployeeDetailsDTO> leadList;
	
}
