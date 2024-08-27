package com.te.projectmanagementtool.constant;

public class ProjectConstant {

	private ProjectConstant() {

	}

	/**
	 * Constant related to addProject() API
	 */
	public static final String PROJECT_DETAILS_UPDATED_SUCCESS = "Project Details Are Updated Successfully";
	public static final String PROJECT_DETAILS_ADDED_SUCCESS = "Project Details Are Added Successfully";
	public static final String INSIDE_ADD_PROJECT_API_OF_PROJECT_SERVICE = "Inside addProject() API Of ProjectDetailsService";
	public static final String PROJECT_DETAILS_ADD_FAILURE = "Unable To Add Project Details";
	public static final String PROJECT_DETAILS_ADD_SUCCESS = "Project Details Are Added Successfully";
	public static final String INSIDE_ADD_PROJECT_API_OF_PROJECT_CONTROLLER = "Inside addProject() API Of ProjectDetailsController";

	/**
	 * Constant related to getProject() API
	 */
	public static final String INSIDE_GET_ALL_PROJECT_API_OF_PROJECT_SERVICE = "Inside addProject() API Of ProjectDetailsService";
	public static final String PROJECT_DETAILS_GET_SUCCESS = "Project Details Are Fetched Successfully.";
	public static final String PROJECT_DETAILS_GET_FAILURE = "No Project Details Are Found.";
	public static final String INSIDE_GET_ALL_PROJECTS_EMPLOYEES_API_OF_PROJECT_CONTROLLER = "Inside getAllEmpsProjectDetails() API Of ProjectDetailsController";

	/**
	 * Constant related to getAllEmpsProjects() API
	 */
	public static final String INSIDE_GET_ALL_EMPLOYEE_AND_PROJECT_API_OF_PROJECT_SERVICE = "Inside getAllEmpsProjects() API Of ProjectDetailsService";
	public static final String FINDING_ALL_EMPLOYEE_FROM_EMPLOYEE_REPOSITORY = "Finding all Employee from Employee Repository";
	public static final String FINDING_ALL_PROJECT_FROM_PROJECT_REPOSITORY = "Finding all Project from project Repository";
	public static final String PROJECT_LIST_FETCHED_SUCCESS = "All Projects Fetched Successfully from the ProjectDetailsRepository";
	public static final String GET_ALL_EMPS_PROJECT_DETAILS_FETCHED_SUCCESS = "All Employees And Projects AreFetched Successfully";
	public static final String INSIDE_GET_ALL_EMPLOYEES_AND_PROJECTS_API_OF_PROJECT_CONTROLLER = "Inside GetAllEmpsProjects() API of ProjectDetailsController";

	/**
	 * Constant related to deleteProject() API
	 */
	public static final String PROJECT_DELETE_FAILURE = "Project Delete Failure";
	public static final String INSIDE_DELETE_PROJECT_API_OF_PROJECT_CONTROLLER = "Inside deleteProject() API of ProjectDetailsController";
	public static final String INSIDE_DELETE_PROJECT_API_OF_PROJECT_SERVICE = "Inside deleteProject() API of ProjectDetailsService";
	public static final String DELETED_THE_PROJECT_DETAILS_SUCCESS = "Deleted the Project Details Successfully";
	public static final String FETCHED_PROJECT_DETAILS_FROM_PROJECT_REPOSITORY = "Fetched Project Details from Project Repository";

	/**
	 * Constant related to mapProject() API
	 */
	public static final String INSIDE_MAP_PROJECT_EMPLOYEE_API_OF_PROJECT_CONTROLLER = "Inside mapProject() API of ProjectDetailsController";
	public static final String INSIDE_MAP_PROJECT_EMPLOYEE_METHOD_OF_PROJECT_SERVICE = "Inside mapProject() method of ProjectDetailsService";
	public static final String MAP_PROJECT_EMPLOYEE_SUCCESS = "Employees Are Mapped To Project Successfully";
	public static final String MAP_PROJECT_EMPLOYEE_FAILURE = "Unable To Map The Employees To Project";

	/**
	 * Constant related to updateProject() API
	 */
	public static final String INSIDE_UPDATE_PROJECT_API_OF_PROJECT_CONTROLLER = "Inside updateProject() API of ProjectDetailsController";
	public static final String INSIDE_UPDATE_PROJECT_METHOD_OF_PROJECT_SERVICE = "Inside updateProject() method of ProjectDetailsService";
	public static final String UPDATE_PROJECT_SUCCESS = "Project Details Are Updated Successfully";
	public static final String UPDATE_PROJECT_FAILURE = "Unable To Update The Project Details";
	public static final String RECORD_NOT_FOUND = "Record Not Found!";
	
	/**
	 * Constant related to relieveEmployeeFromProject() API
	 */
	public static final String INSIDE_RELIEVE_EMPLOYEE_FROM_PROJECT_API_OF_PROJECT_CONTROLLER = "Inside relieveEmployeeFromProject() API of ProjectDetailsController";
	public static final String INSIDE_RELIEVE_EMPLOYEE_FROM_PROJECT_METHOD_OF_PROJECT_SERVICE = "Inside relieveEmployeeFromProject() method of ProjectDetailsService";
	public static final String RELIEVE_EMPLOYEE_FROM_PROJECT_FAILURE = "Unable To Relieve The Employee From Project";
}
