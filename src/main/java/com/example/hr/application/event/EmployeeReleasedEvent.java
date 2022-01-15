package com.example.hr.application.event;

import com.example.hr.dto.response.DetailedEmployeeResponse;

public class EmployeeReleasedEvent {
	private DetailedEmployeeResponse detailedEmployeeResponse;

	public EmployeeReleasedEvent(DetailedEmployeeResponse detailedEmployeeResponse) {
		this.detailedEmployeeResponse = detailedEmployeeResponse;
	}

	public DetailedEmployeeResponse getDetailedEmployeeResponse() {
		return detailedEmployeeResponse;
	}

	public void setDetailedEmployeeResponse(DetailedEmployeeResponse detailedEmployeeResponse) {
		this.detailedEmployeeResponse = detailedEmployeeResponse;
	}
	
}
