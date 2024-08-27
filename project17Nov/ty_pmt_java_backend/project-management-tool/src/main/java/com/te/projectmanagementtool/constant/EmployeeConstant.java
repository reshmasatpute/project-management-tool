package com.te.projectmanagementtool.constant;

public class EmployeeConstant {

	private EmployeeConstant() {

	}

	/**
	 * Constant related to addEmployee() API Of EmployeeDetailsController
	 */
	public static final String INSIDE_ADD_EMPLOYEE_API_OF_EMPLOYEE_CONTROLLER = "Inside addEmployee API Of Employee Controller.";
	public static final String EMPLOYEE_ADD_SUCCESS = "Employee Details Are Added Successfully.";
	public static final String EMPLOYEE_ADD_FAILURE = "Unable To Add Employee Details.";
	public static final String ADD_EMPLOYEE_API_OF_EMPLOYEE_SERVICE = "addEmployee() method from EmployeeService";
	public static final String EMPLOYEE_UPDATE_SUCCESS = "Employee Details Are Updated Successfully";

	/**
	 * Constant related to get-all() API OF EmployeeController
	 */
	public static final String INSIDE_GET_ALL_EMPLOYEE_API_OF_EMPLOYEE_CONTROLLER = "Inside getAllEmployee API Of Employee Controller.";
	public static final String EMPLOYEE_RECORD_NOT_FOUND = "Employee Record Not Found.";
	public static final String EMPLOYEE_FETCHED_SUCCESS = "Employee Details Are Fetched Successfully.";
	public static final String EMPLOYEE_FETCHED_FAILURE = "Unable To Fetch Employee Details.";
	public static final String GET_ALL_EMPLOYEE_API_OF_EMPLOYEE_SERVICE = "getAllEmployee() method from Employee Service.";
	public static final String FETCH_EMPLOYEE_LIST_SUCCESS = "Fetch Employee List Successfully.";

	/**
	 * Constants related to getAllProjectManagers() API
	 */
	public static final String PROJECT_MANAGER_GET_SUCCESS = "Project Mangers Are Fetched Successfully.";
	public static final String PROJECT_MANAGER_GET_FAILURE = "No Project Mangers Are Found.";
	public static final String INSIDE_GET_ALL_PROJECT_MANAGERS_API_0F_EMPLOYEE_DETAILS_CONTROLLER = "Inside getAllProjectManagers() API Of EmployeeDetailsController.";
	public static final String INSIDE_GET_ALL_PROJECT_MANAGERS_0F_EMPLOYEE_DETAILS_SERVICE = "Inside getAllProjectManagers() Method Of EmployeeDetailsService.";

	/**
	 * Constants related to getAllProjectLeads() API
	 */
	public static final String PROJECT_LEAD_GET_SUCCESS = "Project Leads Are Fetched Successfully.";
	public static final String PROJECT_LEAD_GET_FAILURE = "No Project Leads Are Found.";
	public static final String INSIDE_GET_ALL_PROJECT_LEADS_API_0F_EMPLOYEE_DETAILS_CONTROLLER = "Inside getAllProjectLeads() API Of EmployeeDetailsController.";
	public static final String INSIDE_GET_ALL_PROJECT_LEADS_0F_EMPLOYEE_DETAILS_SERVICE = "Inside getAllProjectLeads() Method Of EmployeeDetailsService.";

	/**
	 * Constants related to Hierarchy API
	 */
	public static final String NO_PROJECT_MEMBERS = "No Project Members Found";
	public static final String FETCHED_PROJECT_HISTORY = "Project History Of An Employee Is Fetched Successfully.";
	public static final String INSIDE_GET_PROJECT_HIERARCHY_MAIN_METHOD_OF_EMPLPYEE_SERVICE = "Inside getHierarchyMain() Method Of EmployeeDetailsService.";
	public static final String INSIDE_GET_PROJECT_HIERARCHY_HELPER_METHOD_OF_EMPLPYEE_SERVICE = "Inside getHierarchyHelper() Method Of EmployeeDetailsService.";
	public static final String FETCHED_PROJECT_OF_PROJECT_LEAD = "Projects Of Projects Lead Are Fetched Successfully.";
	public static final String INSIDE_SET_BACKEND_TEAM_MEMBERS = "Inside setBackendTeamMembers() Method Of EmployeeDetailsService";
	public static final String INSIDE_SET_FRONTEND_TEAM_MEMBERS = " Inside setFrontendTeamMembers() Method Of EmployeeDetailsService";
	public static final String INSIDE_HIERARCHY_API_OF_EMPLOYEE_DETAILS_CONTROLLER = " Inside GetHierArchy API Of EmployeeDetailsController";

	/**
	 * Constants related to deleteEmployee() API
	 */
	public static final String EMPLOYEE_DELETE_SUCCESS = "Employee Deleted Successfully";
	public static final String EMPLOYEE_DELETE_FAILURE = "Employee Delete Failure";
	public static final String INSIDE_DELETE_EMPLOYEE_API_OF_EMPLOYEE_DETAILS_SERVICE = "Inside deleteEmployee() API of EmployeeDetailsService";
	public static final String INSIDE_DELETE_EMPLOYEE_API_OF_EMPLOYEE_DETAILS_CONTROLLER = "Inside deleteEmployee API of EmployeeDetailsController";

	public static final String EMPLOYEE_LOG_IN_SUCCESS = "Employee Logged In Successfully.";
	public static final String EMPLOYEE_LOG_IN_FAILURE = "Employee Data Does Not Exist.";

	/**
	 * Constants related to getAllTeamtLeads() API
	 */
	public static final String TEAM_LEAD_GET_SUCCESS = "Tteam Leads Are Fetched Successfully.";
	public static final String TEAM_LEAD_GET_FAILURE = "No Team Leads Are Found.";
	public static final String INSIDE_GET_ALL_TEAM_LEADS_API_0F_EMPLOYEE_DETAILS_CONTROLLER = "Inside getAllTeamLeads() API Of EmployeeDetailsController.";
	public static final String INSIDE_GET_ALL_TEAM_LEADS_0F_EMPLOYEE_DETAILS_SERVICE = "Inside getAllTeamLeads() Method Of EmployeeDetailsService.";
}
