package mapper;

import dto.EmailDto;
import entity.Email;
import org.mapstruct.*;

import java.util.Set;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmailMapper {

    @Mapping (target = "employee", ignore = true)
    @Named (value = "emailDtoToEntity")
    Email emailDtoToEntity(EmailDto dto);

    @IterableMapping(qualifiedByName = "emailDtoToEntity")
    Set<Email> emailDtoSetToEntitySet(Set<EmailDto> dtoSet);

    @Mapping (target = "employeeDto", ignore = true)
    @Named (value = "emailToDto")
    EmailDto emailToDto(Email email);

    @IterableMapping(qualifiedByName = "emailToDto")
    Set<EmailDto> emailSetToDtoSet(Set<Email> emailsSet);
}
