package service;

import dto.EmployeeDto;
import entity.Employee;
import mapper.EmployeeMapper;
import org.mapstruct.factory.Mappers;
import provider.EmployeeProvider;

public class EmployeeService {

    // FIXME: later change into Spring DI
    private EmployeeMapper employeeMapper = Mappers.getMapper( EmployeeMapper.class );

    // FIXME: later change into Spring DI
    private EmployeeProvider employeeProvider = new EmployeeProvider ();


    public void saveEmployee(EmployeeDto dto) {
        Employee employee = employeeMapper.employeeDtoToEntity (dto, new Employee ());
        employeeProvider.saveEmployee (employee);
    }

    public EmployeeDto saveEmployeeReturnAttachedEmployeeDto(EmployeeDto dto) {
        Employee employee = employeeMapper.employeeDtoToEntity (dto, new Employee ());
        return employeeMapper.employeeToDto (employeeProvider.saveEmployeeReturnAttachedEmployee (employee), new EmployeeDto ());
    }

    public void deleteEmployee(Long id) {
        employeeProvider.deleteEmployee (id);
    }

    public void updateEmployee(EmployeeDto newDto) {
        Employee employee = employeeMapper.employeeDtoToEntity (newDto, new Employee ());
        employeeProvider.updateEmployee (employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeProvider.findEmployeeById (id);
    }

    public Employee findEmployeeByIdWithJPQL(Long id) {
        return employeeProvider.findEmployeeByIdWithJPQL (id);
    }

    public void detachEmployeeById(Long id) {
        employeeProvider.detachEmployeeById (id);
    }

    public Employee detachEmployeeByIdThenReturnDetachedEmployee(Long id) {
        return employeeProvider.detachEmployeeByIdThenReturnDetachedEmployee (id);
    }
}
