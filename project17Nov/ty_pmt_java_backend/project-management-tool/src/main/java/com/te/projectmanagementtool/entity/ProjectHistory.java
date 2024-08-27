package com.te.projectmanagementtool.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectHistory {

	private String projectId;
	private String projectName;
	private String projectHistoryId;
	private String priority;
	private String status;
	private String role;
	private LocalDate projectJoiningDate;
	private LocalDate projectRelievingDate;
	private String reportingTo;

}
