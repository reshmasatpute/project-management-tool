/*
 * package com.te.projectmanagementtool.entity;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals;
 * 
 * import org.junit.Test;
 * 
 * import com.fasterxml.jackson.core.JsonProcessingException; import
 * com.fasterxml.jackson.databind.JsonMappingException; import
 * com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * public class ProjectDetailsTest {
 * 
 * ObjectMapper mapper = new ObjectMapper(); String json =
 * "{\"projectId\":\"PI001\",\"projectName\":\"LMS\",\"projectDomain\":null,\"clientName\":\"Google\",\"projectStartDate\":null,\"projectEndDate\":null,\"projectStatus\":null,\"projectManager\":null,\"projectLead\":null,\"projectMembers\":null,\"milestoneDetails\":[]}";
 * 
 * @Test public void serializationTest() throws JsonProcessingException {
 * ProjectDetails details = new ProjectDetails(); details.setProjectId("PI001");
 * details.setProjectName("LMS"); details.setClientName("Google");
 * System.out.println(mapper.writeValueAsString(details)); ProjectDetails
 * readValue = mapper.readValue(json, ProjectDetails.class);
 * assertEquals(mapper.writeValueAsString(details),
 * mapper.writeValueAsString(readValue)); }
 * 
 * @Test public void deSerializeTest() throws JsonMappingException,
 * JsonProcessingException { ProjectDetails readValue = mapper.readValue(json,
 * ProjectDetails.class); assertEquals("PI001", readValue.getProjectId()); }
 * 
 * }
 */