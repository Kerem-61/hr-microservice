package com.example.hr.application;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.request.UpdateEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.FireEmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;

public interface HrApplication {
	HireEmployeeResponse hireEmployee(HireEmployeeRequest request);
	EmployeeResponse updateEmployee(String identity, UpdateEmployeeRequest request);
	FireEmployeeResponse fireEmployee(String identity);
	List<EmployeeResponse> findEmployees(@Min(0) int page, @Max(25) int size);
	EmployeeResponse findEmployeeByIdentity(String identity);
}
