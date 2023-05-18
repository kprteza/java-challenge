package jp.co.axa.apidemo.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.axa.apidemo.dto.EmployeeRequest;
import jp.co.axa.apidemo.dto.EmployeeResponse;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;

/**
 * The EmployeeController class provides REST API endpoints for managing
 * employees.
 */
@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    /**
     * Retrieves a paginated list of employees.
     *
     * @param pageable the pagination parameters
     * @return a ResponseEntity containing the paginated list of employees
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(Pageable pageable) {
        Page<EmployeeResponse> response = employeeService.retrieveEmployees(pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return a ResponseEntity containing the employee if found, or a 404 Not Found
     *         response otherwise
     */
    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable(name = "employeeId") @Min(1) Long employeeId) {
        EmployeeResponse employee = employeeService.getEmployee(employeeId);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    /**
     * Creates a new employee.
     *
     * @param req the request body containing the employee data
     * @return a ResponseEntity with a 201 Created status if successful
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> saveEmployee(@Valid @RequestBody EmployeeRequest req) {
        employeeService.saveEmployee(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     * @return a ResponseEntity with a 200 OK status if successful
     */
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an employee by their ID.
     *
     * @param req        the request body containing the updated employee data
     * @param employeeId the ID of the employee to update
     * @return a ResponseEntity with a 200 OK status if successful, or a 404 Not
     *         Found response if the employee is not found
     */
    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateEmployee(
            @Valid @RequestBody EmployeeRequest req,
            @PathVariable(name = "employeeId") Long employeeId) {
        EmployeeResponse existingEmployee = employeeService.getEmployee(employeeId);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        employeeService.updateEmployee(employeeId, req);
        return ResponseEntity.ok().build();
    }

}
