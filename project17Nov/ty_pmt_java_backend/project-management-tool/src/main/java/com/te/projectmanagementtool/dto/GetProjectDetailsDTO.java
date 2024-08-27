package com.te.projectmanagementtool.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectDetailsDTO {
	
	private String projectId;
	
	private String projectName;
	
	private List<BackendTeamDTO> backendTeamDTOList = new ArrayList<>();
	
	private List<FrontendTeamDTO> frontendTeamDTOList = new ArrayList<>();

}
