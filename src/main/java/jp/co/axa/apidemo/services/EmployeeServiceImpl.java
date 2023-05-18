package jp.co.axa.apidemo.services;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.axa.apidemo.dto.EmployeeRequest;
import jp.co.axa.apidemo.dto.EmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Retrieves a paginated list of employees.
     *
     * @param pageable the pagination parameters
     * @return a page of EmployeeResponse objects
     */
    public Page<EmployeeResponse> retrieveEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(this::mapToEmployeeResponse);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the EmployeeResponse object if found, or null if not found
     *
     * @Cacheable: Caches the returned EmployeeResponse with "employees" as the
     *             cache name.
     *             The cache key is generated based on the employeeId.
     */
    @Cacheable(value = "employees", key = "#employeeId", unless = "#result == null")
    public EmployeeResponse getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return mapToEmployeeResponse(optEmp.orElse(null));
    }

    /**
     * Saves a new employee.
     *
     * @param req the EmployeeRequest object containing the employee data
     * @return the EmployeeResponse object of the saved employee
     *
     * @CachePut: Updates the cache entry for the saved employee with "employees" as
     *            the cache name.
     *            The cache key is generated based on the ID of the returned
     *            EmployeeResponse object.
     */
    @CachePut(value = "employees", key = "#result.id")
    public EmployeeResponse saveEmployee(EmployeeRequest req) {
        Employee employee = Employee.builder()
                .name(req.getName())
                .salary(req.getSalary())
                .department(req.getDepartment())
                .build();
        return mapToEmployeeResponse(employeeRepository.save(employee));
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     *
     * @CacheEvict: Removes the cache entry for the deleted employee with
     *              "employees" as the cache name.
     *              The cache key is generated based on the employeeId.
     */
    @CacheEvict(value = "employees", key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    /**
     * Updates an existing employee by their ID.
     *
     * @param employeeId the ID of the employee to update
     * @param req        the EmployeeRequest object containing the updated employee
     *                   data
     * @return the EmployeeResponse object of the updated employee
     *
     * @CachePut: Updates the cache entry for the updated employee with "employees"
     *            as the cache name.
     *            The cache key is generated based on the employeeId.
     */
    @CachePut(value = "employees", key = "#employeeId")
    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest req) {
        Employee employee = Employee.builder()
                .id(employeeId)
                .name(req.getName())
                .salary(req.getSalary())
                .department(req.getDepartment())
                .build();
        employeeRepository.save(employee);
        return mapToEmployeeResponse(employeeRepository.save(employee));
    }

    /**
     * Maps an Employee object to an EmployeeResponse object.
     *
     * @param employee the Employee object to map
     * @return the mapped EmployeeResponse object
     */
    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        if (null == employee) {
            return null;
        }
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .salary(employee.getSalary())
                .department(employee.getDepartment())
                .build();
    }
}
