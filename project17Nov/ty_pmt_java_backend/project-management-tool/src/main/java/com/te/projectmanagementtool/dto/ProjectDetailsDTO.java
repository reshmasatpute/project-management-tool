package com.te.projectmanagementtool.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.te.projectmanagementtool.entity.MilestoneDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailsDTO {

	private String projectId;

	private String projectName;

	private String projectDomain;

	private String clientName;

	private LocalDate projectStartDate;

	private LocalDate projectEndDate;

	private String projectStatus;

	private String projectManager;

	private String projectLead;
	
	private List<GetEmployeeDto> projectTeamMembers = new ArrayList<>();

	private Map<String, String> projectMembers;

	private List<MilestoneDetails> milestoneDetails = new ArrayList<>();

}
