package mapper;

import dto.EmployeeDto;
import entity.Employee;
import org.mapstruct.*;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class EmployeeMapper {

//    //@Mapping(target = "emails", source = "emailsDto")
//    @Mapping(source = "emailsDto", ignore = true)
//    Employee employeeDtoToEntity(EmployeeDto dto, @MappingTarget Employee target);
//
//    @AfterMapping
//    protected void addReview(EmployeeDto dto, @MappingTarget Employee target) {
//        //dto.getEmailsDto ();
//
//        target.setEmails ();
//    }


}
