package com.te.projectmanagementtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.projectmanagementtool.constant.EmployeeConstant;
import com.te.projectmanagementtool.dto.UserDTO;
import com.te.projectmanagementtool.service.EmployeeDetailsService;
import com.te.projectmanagementtool.util.APIResponse;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

	@Autowired
	private EmployeeDetailsService employeeDetailsService;

	@PostMapping("/login")
	public ResponseEntity<APIResponse> login(@RequestBody UserDTO userDTO) {
		String login = employeeDetailsService.login(userDTO);
		if (login != null)
			return new ResponseEntity<>(APIResponse.builder().data(login)
					.message(EmployeeConstant.EMPLOYEE_LOG_IN_SUCCESS).error(false).build(), HttpStatus.OK);
		else
			return new ResponseEntity<>(APIResponse.builder().data(login)
					.message(EmployeeConstant.EMPLOYEE_LOG_IN_FAILURE).error(true).build(), HttpStatus.NOT_FOUND);

	}

}
