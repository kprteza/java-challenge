package jp.co.axa.apidemo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jp.co.axa.apidemo.dto.EmployeeRequest;
import jp.co.axa.apidemo.dto.EmployeeResponse;

public interface EmployeeService {

    public Page<EmployeeResponse> retrieveEmployees(Pageable pageable);

    public EmployeeResponse getEmployee(Long employeeId);

    public EmployeeResponse saveEmployee(EmployeeRequest request);

    public void deleteEmployee(Long employeeId);

    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest request);
}