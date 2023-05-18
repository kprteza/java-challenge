package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.dto.EmployeeRequest;
import jp.co.axa.apidemo.dto.EmployeeResponse;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.NoArgsConstructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Mock
        private EmployeeService employeeService;

        @InjectMocks
        private EmployeeController employeeController;

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        public void getEmployees_ReturnsOk() throws Exception {
                // Mocking the service behavior
                Page<EmployeeResponse> employeePage = new PageImpl<>(Collections.emptyList());
                when(employeeService.retrieveEmployees(any(Pageable.class))).thenReturn(employeePage);

                // Calling the API endpoint
                RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees")
                                .contentType(MediaType.APPLICATION_JSON);
                MvcResult result = mockMvc.perform(requestBuilder).andReturn();

                // Verifying the response
                assertThat(result.getResponse().getStatus()).isEqualTo(200);
        }

        @Test
        public void getEmployee_WithInvalidId_ReturnsNotFound() throws Exception {
                // Mocking the service behavior
                when(employeeService.getEmployee(2L)).thenReturn(null);

                // Calling the API endpoint
                RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees/2")
                        .contentType(MediaType.APPLICATION_JSON);
                MvcResult result = mockMvc.perform(requestBuilder).andReturn();

                // Verifying the response
                assertThat(result.getResponse().getStatus()).isEqualTo(404);
        }

        // @Test
        // public void getEmployee_WithValidId_ReturnsOk() throws Exception {
        // // Mocking the service behavior
        // EmployeeResponse employeeResponse =
        // EmployeeResponse.builder().id(1L).name("John Doe").department("dev")
        // .build();

        // when(employeeService.getEmployee(1L)).thenReturn(employeeResponse);

        // // Calling the API endpoint
        // RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees/1")
        // .contentType(MediaType.APPLICATION_JSON);
        // MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // // Verifying the response
        // assertThat(result.getResponse().getStatus()).isEqualTo(404);
        // verify(employeeService).getEmployee(1L);
        // }

        // @Test
        // public void saveEmployee_ValidRequest_ReturnsCreated() throws Exception {
        // // Mocking the service behavior
        // doNothing().when(employeeService).saveEmployee(any(EmployeeRequest.class));

        // // Calling the API endpoint
        // RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employees")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(objectMapper.writeValueAsString(
        // EmployeeResponse.builder().name("John Doe").department("dev")
        // .build()));
        // MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // // Verifying the response
        // assertThat(result.getResponse().getStatus()).isEqualTo(201);
        // verify(employeeService).saveEmployee(any(EmployeeRequest.class));
        // }

        // @Test
        // public void deleteEmployee_WithValidId_ReturnsOk() throws Exception {
        // // Mocking the service behavior
        // doNothing().when(employeeService).deleteEmployee(1L);

        // // Calling the API endpoint
        // RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/employees/1")
        // .contentType(MediaType.APPLICATION_JSON);
        // MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // // Verifying the response
        // assertThat(result.getResponse().getStatus()).isEqualTo(200);
        // verify(employeeService).deleteEmployee(1L);
        // }

        // @Test
        // public void updateEmployee_WithValidId_ReturnsOk() throws Exception {
        // // Mocking the service behavior
        // EmployeeResponse existingEmployee =
        // EmployeeResponse.builder().id(1L).name("John Doe").department("dev")
        // .build();
        // when(employeeService.getEmployee(1L)).thenReturn(existingEmployee);
        // doNothing().when(employeeService).updateEmployee(1L,
        // EmployeeRequest.builder().name("John Doe").department("dev")
        // .build());

        // // Calling the API endpoint
        // RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/employees/1")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(objectMapper.writeValueAsString(
        // EmployeeRequest.builder().name("John Doe").department("dev")
        // .build()));
        // MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // // Verifying the response
        // assertThat(result.getResponse().getStatus()).isEqualTo(200);
        // verify(employeeService).getEmployee(1L);
        // verify(employeeService).updateEmployee(1L,
        // EmployeeRequest.builder().name("John Doe").department("dev")
        // .build());
        // }
}
