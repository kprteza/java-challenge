package jp.co.axa.apidemo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    @NotBlank(message = "Invalid Name: name is blank")
    @NotNull(message = "Invalid Name: name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
    private String name;

    @Min(value = 1, message = "Invalid Salary: Equals to or Less than zero")
    @NotNull(message = "Invalid Salary: salary is NULL")
    private Integer salary;

    @NotBlank(message = "Invalid Department: Department is blank")
    @NotNull(message = "Invalid Department: Department is NULL")
    private String department;
}