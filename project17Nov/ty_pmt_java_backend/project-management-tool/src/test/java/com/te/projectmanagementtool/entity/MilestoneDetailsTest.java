package com.te.projectmanagementtool.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MilestoneDetailsTest {
	
	ObjectMapper mapper = new ObjectMapper();
	String json="{\"milestoneId\":\"MI001\",\"milestoneName\":\"M1\",\"milestoneTask\":\"Admin Module\",\"estimatedDevelopmentDays\":null,\"estimatedTestingDays\":null,\"milestoneStartDate\":null,\"milestoneEndDate\":null,\"deliverableDate\":null,\"scopeType\":null}";

	@Test
	public void serializationTest() throws JsonProcessingException {
		MilestoneDetails details = new MilestoneDetails();
		details.setMilestoneId("MI001");
		details.setMilestoneName("M1");
		details.setMilestoneTask("Admin Module");
		System.out.println(mapper.writeValueAsString(details));
		MilestoneDetails readValue = mapper.readValue(json, MilestoneDetails.class);
		assertEquals(mapper.writeValueAsString(details), mapper.writeValueAsString(readValue));
	}

	@Test
	public void deSerializeTest() throws JsonMappingException, JsonProcessingException {
		MilestoneDetails readValue = mapper.readValue(json, MilestoneDetails.class);
		assertEquals("Admin Module", readValue.getMilestoneTask());
	}


}
