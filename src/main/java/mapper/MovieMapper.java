package mapper;

import dto.MovieDto;
import entity.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface MovieMapper {

    MovieDto movieEntityToDto(Movie entity);

    Movie movieDtoToEntity(MovieDto dto);
}
