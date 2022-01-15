package com.example.hr.application.business;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.hr.application.HrApplication;
import com.example.hr.application.business.exception.EmployeeAlreadyExistException;
import com.example.hr.application.business.exception.EmployeeNotFoundException;
import com.example.hr.application.event.EmployeeReleasedEvent;
import com.example.hr.document.EmployeeDocument;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.request.UpdateEmployeeRequest;
import com.example.hr.dto.response.DetailedEmployeeResponse;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.FireEmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;
import com.example.hr.repository.EmployeeMongoRepository;

@Service
public class StandardHrApplication implements HrApplication {
	private EmployeeMongoRepository employeeMongoRepository;
	private ModelMapper modelMapper;
	private ApplicationEventPublisher applicationEvent;
	
	
	public StandardHrApplication(EmployeeMongoRepository employeeMongoRepository, ModelMapper modelMapper,
			ApplicationEventPublisher applicationEvent) {
		this.employeeMongoRepository = employeeMongoRepository;
		this.modelMapper = modelMapper;
		this.applicationEvent = applicationEvent;
	}

	@Override
	public HireEmployeeResponse hireEmployee(HireEmployeeRequest request) {
		var identity = request.getIdentity();
		if (employeeMongoRepository.existsById(identity)) {
			throw new EmployeeAlreadyExistException();
		}
		var employee = modelMapper.map(request, EmployeeDocument.class);
		return modelMapper.map(employeeMongoRepository.save(employee), HireEmployeeResponse.class);
		
	}

	@Override
	public EmployeeResponse updateEmployee(String identity, UpdateEmployeeRequest request) {
		var employee = employeeMongoRepository.findById(identity).orElseThrow(() -> new EmployeeNotFoundException());
		modelMapper.map(request, employee);
		employee.setIdentity(identity);
		return modelMapper.map(employeeMongoRepository.save(employee), EmployeeResponse.class);
	}

	
	@Override
	public FireEmployeeResponse fireEmployee(String identity) {
		var employee = employeeMongoRepository.findById(identity).orElseThrow(() -> new EmployeeNotFoundException());
		var employeeReleasedEvent = new EmployeeReleasedEvent(
				modelMapper.map(employee, DetailedEmployeeResponse.class));
		applicationEvent.publishEvent(employeeReleasedEvent);
		employeeMongoRepository.delete(employee);
		return modelMapper.map(employee, FireEmployeeResponse.class);
	}
	

	@Override
	public List<EmployeeResponse> findEmployees(int page, int size) {
		var employee = PageRequest.of(page, size);
		return employeeMongoRepository.findAll(employee).stream().map(cust -> modelMapper.map(cust, EmployeeResponse.class))
				.toList();
	}

	

	@Override
	public EmployeeResponse findEmployeeByIdentity(String identity) {
		var employee = employeeMongoRepository.findById(identity).orElseThrow( () -> new EmployeeNotFoundException());
		return modelMapper.map(
				(employee), EmployeeResponse.class);
	}

	
}
																																											