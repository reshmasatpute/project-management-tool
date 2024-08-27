package com.te.projectmanagementtool.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectHierarchyDTO {
	
	private String projectManagerEmployeeNo;
	
	private String projectManagerName;

	private List<ProjectLeadListDTO> projectLeadList = new ArrayList<>();
}
