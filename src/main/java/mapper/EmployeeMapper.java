package mapper;

import dto.AddressDto;
import dto.EmailDto;
import dto.EmployeeDto;
import entity.Address;
import entity.Email;
import entity.Employee;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.Set;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        //nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class EmployeeMapper {

    private EmailMapper emailMapper = Mappers.getMapper( EmailMapper.class );
    private AddressMapper addressMapper = Mappers.getMapper( AddressMapper.class );

    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    public abstract Employee employeeDtoToEntity(EmployeeDto dto, @MappingTarget Employee target);

    @Mapping (target = "emailsDto", ignore = true)
    @Mapping (target = "addressesDto", ignore = true)
    public abstract EmployeeDto employeeToDto(Employee employee, @MappingTarget EmployeeDto target);

    @AfterMapping
    protected void addEmails(EmployeeDto dto, @MappingTarget Employee target) {
        Set<Email> emailsSet = emailMapper.emailDtoSetToEntitySet (dto.getEmailsDto ());
        target.setEmails (emailsSet);
        emailsSet.stream ().forEach (email -> email.setEmployee (target));

        Set<Address> addressesSet = addressMapper.addressDtoSetToEntitySet (dto.getAddressesDto ());
        target.setAddresses (addressesSet);
        addressesSet.stream ().forEach (address -> address.setEmployee  (target));
    }

    @AfterMapping
    protected void addEmailsDto(Employee employee, @MappingTarget EmployeeDto target) {
        Set<EmailDto> emailDtoSet = emailMapper.emailSetToDtoSet (employee.getEmails ());
        target.setEmailsDto (emailDtoSet);
        emailDtoSet.stream ().forEach (emailDto -> emailDto.setEmployeeDto (target));

        Set<AddressDto> addressDtoSet = addressMapper.addressSetToDtoSet (employee.getAddresses ());
        target.setAddressesDto (addressDtoSet);
        addressDtoSet.stream ().forEach (addressDto -> addressDto.setEmployeeDto (target));
    }
}
