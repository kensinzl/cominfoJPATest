package service;

import dto.EmployeeDto;
import entity.Employee;
import mapper.MovieMapper;
import org.mapstruct.factory.Mappers;

public class EmployeeService {

    // FIXME: later change into Spring DI
    private MovieMapper movieMapper = Mappers.getMapper( MovieMapper.class );

    public void saveEmployee(EmployeeDto dto) {


    }
}
