package com.te.projectmanagementtool.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllEmployeeProjectDto {
	
	private List<GetEmployeeDto> getEmployeeDtoList;
	
	private List<GetProjectDto> getProjectDtoList;
}
