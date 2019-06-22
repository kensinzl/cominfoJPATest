package mapper;

import dto.AddressDto;
import entity.Address;
import org.mapstruct.*;

import java.util.Set;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AddressMapper {

    @Mapping(target = "employee", ignore = true)
    @Named (value = "addressDtoToEntity")
    Address addressDtoToEntity(AddressDto dto);

    @IterableMapping(qualifiedByName = "addressDtoToEntity")
    Set<Address> addressDtoSetToEntitySet(Set<AddressDto> dtoSet);

    @Mapping (target = "employeeDto", ignore = true)
    @Named (value = "addressToDto")
    AddressDto addressToDto(Address address);

    @IterableMapping(qualifiedByName = "addressToDto")
    Set<AddressDto> addressSetToDtoSet(Set<Address> addressSet);
}
