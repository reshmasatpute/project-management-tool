package com.te.projectmanagementtool.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.te.projectmanagementtool.entity.EmployeeDetails;

@Repository
public interface EmployeeDetailsRepository extends MongoRepository<EmployeeDetails, String> {

	EmployeeDetails findByEmployeeId(String employeeId);

	List<EmployeeDetails> findByEmployeeIdIn(List<String> employeeIds);

	List<EmployeeDetails> findAllByRole(String projectLead);

	EmployeeDetails findByEmployeeNo(String userId);

	List<EmployeeDetails> findByEmployeeNoIn(List<String> employeeIds);

	void deleteByEmployeeNo(String employeeId);

}
