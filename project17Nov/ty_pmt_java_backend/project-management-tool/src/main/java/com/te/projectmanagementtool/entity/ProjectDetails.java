package com.te.projectmanagementtool.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pmt_project_details")
public class ProjectDetails {
	
	@Transient
	public static final String SEQUENCE_NAME = "project_sequence";

	
	@Id
	private String projectId;

	private String projectName;

	private String projectDomain;

	private String clientName;

	private LocalDate projectStartDate;

	private LocalDate projectEndDate;

	private String projectStatus;

	private String projectManager;

	private String projectLead;

	private Map<String, String> projectMembers = new HashMap<>();

	private List<MilestoneDetails> milestoneDetails = new ArrayList<>();

}
