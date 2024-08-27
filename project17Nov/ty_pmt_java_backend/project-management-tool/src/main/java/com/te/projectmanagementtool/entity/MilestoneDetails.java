package com.te.projectmanagementtool.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneDetails {

	private String milestoneId;

	private String milestoneName;

	private String milestoneTask;

	private Double estimatedDevelopmentDays;

	private Double estimatedTestingDays;

	private LocalDate milestoneStartDate;

	private LocalDate milestoneEndDate;

	private LocalDate deliverableDate;

	private String scopeType;

}
