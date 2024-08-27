package com.te.projectmanagementtool.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectHistoryTest {
	
	ObjectMapper mapper = new ObjectMapper();
	String json="{\"projectId\":null,\"projectHistoryId\":\"PH001\",\"priority\":\"Medium\",\"status\":\"Pending\",\"role\":null,\"projectJoiningDate\":null,\"projectRelievingDate\":null,\"reportingTo\":null}";

	@Test
	public void serializationTest() throws JsonProcessingException {
		ProjectHistory history = new ProjectHistory();
		history.setProjectHistoryId("PH001");
		history.setStatus("Pending");
		history.setPriority("Medium");
		//System.out.println(mapper.writeValueAsString(history));
		ProjectHistory readValue = mapper.readValue(json, ProjectHistory.class);
		assertEquals(mapper.writeValueAsString(history), mapper.writeValueAsString(readValue));
	}

	@Test
	public void deSerializeTest() throws JsonMappingException, JsonProcessingException {
		ProjectHistory readValue = mapper.readValue(json, ProjectHistory.class);
		assertEquals("PH001", readValue.getProjectHistoryId());
	}


}
