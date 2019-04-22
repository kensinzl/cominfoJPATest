package mapper;

import dto.EmailDto;
import entity.Email;
import org.mapstruct.Mapper;

@Mapper
public interface EmailMapper {
    Email emailDtoToEntity(EmailDto dto);

    EmailDto emailToDto(Email entity);
}
