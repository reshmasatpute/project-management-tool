package com.te.projectmanagementtool.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeDetailsTest {

	ObjectMapper mapper = new ObjectMapper();
	String json = "{\"employeeId\":\"TY001\",\"employeeNo\":null,\"employeeFirstName\":\"Karthi\",\"employeeMiddleName\":null,\"employeeLastName\":\"Thiyagu\",\"employeeOfficialEmailId\":null,\"employeePersonalEmailId\":null,\"employeeOfficialContactNumber\":null,\"type\":null,\"role\":null,\"employeeProjectHistory\":[],\"employeeSkills\":[]}";

	@Test
	public void serializationTest() throws JsonProcessingException {
		EmployeeDetails details = new EmployeeDetails();
		details.setEmployeeId("TY001");
		details.setEmployeeFirstName("Karthi");
		details.setEmployeeLastName("Thiyagu");
		// System.out.println(mapper.writeValueAsString(details));
		EmployeeDetails readValue = mapper.readValue(json, EmployeeDetails.class);
		assertEquals(mapper.writeValueAsString(details), mapper.writeValueAsString(readValue));
	}

	@Test
	public void deSerializeTest() throws JsonMappingException, JsonProcessingException {
		EmployeeDetails readValue = mapper.readValue(json, EmployeeDetails.class);
		assertEquals("TY001", readValue.getEmployeeId());
	}

}
