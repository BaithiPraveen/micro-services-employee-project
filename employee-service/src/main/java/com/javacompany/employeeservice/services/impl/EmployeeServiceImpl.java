package com.javacompany.employeeservice.services.impl;

import com.javacompany.employeeservice.dto.DepartmentDTO;
import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.OrganizationDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import com.javacompany.employeeservice.entities.Employee;
import com.javacompany.employeeservice.repositories.EmployeeRepository;
import com.javacompany.employeeservice.services.APIClient;
import com.javacompany.employeeservice.services.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    @Autowired
    APIClient apiClient;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Employee savaEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public ResponseDTO getEmployeeById(Long employeeId) {
        LOGGER.info("hit the getEmployeeById()...!");
        Employee employee = employeeRepository.findById(employeeId).get();
        DepartmentDTO departmentDTO = apiClient.getEmployeeByDepartmentCode(employee.getDepartmentId());
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        OrganizationDTO organizationDTO = webClient.get().uri("http://localhost:8083/organization/"+employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDTO.class)
                .block();
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setEmployee(employeeDTO);
        responseDto.setDepartment(departmentDTO);
        responseDto.setOrganization(organizationDTO);
        return responseDto;
    }

    public ResponseDTO getDefaultDepartment(Long employeeId, Exception exception) {

        //exception is class org.springframework.web.reactive.function.client.WebClientRequestException

        LOGGER.info("hit the getDefaultDepartment()...!");

        Employee employee = employeeRepository.findById(employeeId).get();
        DepartmentDTO departmentDTO = new DepartmentDTO(
                0L, "default dept", "default addresses", "001"
        );
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setEmployee(employeeDTO);
        responseDto.setDepartment(departmentDTO);
        return responseDto;
    }

    @Override
    public List<EmployeeDTO> findByDepartmentId(String departmentId) {

        return employeeRepository.findByDepartmentId(departmentId).stream().map(emp -> modelMapper.map(emp, EmployeeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ResponseDTO createEmployee(ResponseDTO responseDTO) {
        String departmentCode;
        DepartmentDTO resultdepartmentDTO;
        EmployeeDTO employeeDTO = responseDTO.getEmployee();
        DepartmentDTO departmentDTO = responseDTO.getDepartment();
        Boolean exsitDepartmentResult = apiClient.existsByDepartmentName(departmentDTO.getDepartmentName());
        if (exsitDepartmentResult) {
            resultdepartmentDTO = apiClient.searchByDepartmentName(departmentDTO.getDepartmentName());
            departmentCode = resultdepartmentDTO.getDepartmentCode();
        } else {
            resultdepartmentDTO = apiClient.saveDepartment(departmentDTO);
            departmentCode = resultdepartmentDTO.getDepartmentCode();
        }
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setDepartmentId(departmentCode);
        Employee saveEmployee = this.savaEmployee(employee);
        EmployeeDTO resultEmployeeDTO = modelMapper.map(saveEmployee, EmployeeDTO.class);
        responseDTO.setEmployee(resultEmployeeDTO);
        responseDTO.setDepartment(resultdepartmentDTO);

        return responseDTO;
    }

    @Override
    public ResponseDTO updateEmployee(Long employeeId, Employee employee) {
        String baseDeptURL = "http://localhost:8080/dept/;http://localhost:8082/dept/";
        Employee resultEmployee = employeeRepository.findById(employeeId).get();
        resultEmployee.setDepartmentId(employee.getDepartmentId());
        resultEmployee.setEmail(employee.getEmail());
        resultEmployee.setFirstName(employee.getFirstName());
        resultEmployee.setLastName(employee.getLastName());
        EmployeeDTO resultEmployeeDTO = modelMapper.map(employeeRepository.save(resultEmployee), EmployeeDTO.class);
        DepartmentDTO departmentDTO = webClient.get()
                .uri(baseDeptURL + employee.getDepartmentId())
                .retrieve()
                .bodyToMono(DepartmentDTO.class)
                .block();
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setEmployee(resultEmployeeDTO);
        responseDto.setDepartment(departmentDTO);
        return responseDto;
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        employeeRepository.delete(employee);
    }

    @Override
    public List<ResponseDTO> getEmployeeListWithDepartments() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<ResponseDTO> responseDTOS = new ArrayList<>();
        for (Employee emp : employeeList) {
            ResponseDTO responseDTOData = new ResponseDTO();
            responseDTOData.setEmployee(modelMapper.map(emp, EmployeeDTO.class));
            responseDTOData.setDepartment(apiClient.getEmployeeByDepartmentCode(emp.getDepartmentId()));
            responseDTOS.add(responseDTOData);
        }
        return responseDTOS;
    }

    @Override
    public EmployeeDTO getEmployeeByIdWithCheck(Long employeeId) {
        return modelMapper.map(employeeRepository.findById(employeeId), EmployeeDTO.class);
    }


//    @Override
//    public ResponseDTO getEmployeeById(Long employeeId) {
//        String baseDeptURL = "http://localhost:8080/dept/";
//        Employee employee = employeeRepository.findById(employeeId).get();
//        ResponseEntity<DepartmentDTO> departmentDTOResponseEntity = restTemplate
//                .getForEntity(baseDeptURL+employee.getDepartmentId(),DepartmentDTO.class);
//        DepartmentDTO departmentDTO = departmentDTOResponseEntity.getBody();
//        EmployeeDTO employeeDTO = modelMapper.map(employee,EmployeeDTO.class);
//        ResponseDTO responseDto = new ResponseDTO();
//        responseDto.setEmployee(employeeDTO);
//        responseDto.setDepartment(departmentDTO);
//        return responseDto;
//    }

//    @Override
//    public ResponseDTO getEmployeeById(Long employeeId) {
//        String baseDeptURL = "http://localhost:8080/dept/";
//        Employee employee = employeeRepository.findById(employeeId).get();
//
//        DepartmentDTO departmentDTO = webClient.get()
//                .uri(baseDeptURL + employee.getDepartmentId())
//                .retrieve()
//                .bodyToMono(DepartmentDTO.class)
//                .block();
//        EmployeeDTO employeeDTO =modelMapper.map(employee,EmployeeDTO.class);
//        ResponseDTO responseDto = new ResponseDTO();
//        responseDto.setEmployee(employeeDTO);
//        responseDto.setDepartment(departmentDTO);
//        return responseDto;
//    }


}
