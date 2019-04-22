package service;

import dto.MovieDto;
import entity.Movie;
import mapper.MovieMapper;
import org.mapstruct.factory.Mappers;
import provider.MovieProvider;

public class MovieService {

    // FIXME: later change into Spring DI
    private MovieMapper movieMapper = Mappers.getMapper( MovieMapper.class );

    // FIXME: later change into Spring DI
    private MovieProvider movieProvider = new MovieProvider ();


    public void saveMovie(MovieDto dto) {
        Movie movie = movieMapper.movieDtoToEntity(dto);
        System.out.println("------ Before persist, em "+ (movieProvider.isContain (movie) == true
                ? "contains " : "not contain ")+ movie );
        movieProvider.saveMovie(movie);
        System.out.println("------ After persist, em "+ (movieProvider.isContain (movie) == true
                ? "contains " : "not contain ")+ movie );
    }

    public Movie findMovieById(Long id) {
        return movieProvider.findMovieById (id);
    }

    public void detachMovieById(Long id) {
        movieProvider.detachMovieById (id);
    }

    public boolean isContain(Object entity) {
        return movieProvider.isContain (entity);
    }

    public Movie updateMovieByFind(MovieDto updatingMovieDto) {
        Movie movie = movieMapper.movieDtoToEntity(updatingMovieDto);
        return movieProvider.updateMovieByFind (movie);
    }

    public Movie updateMovieByMerge(MovieDto updatingMovieDto) {
        Movie movie = movieMapper.movieDtoToEntity(updatingMovieDto);
        return movieProvider.updateMovieByMerge (movie);
    }

    public Movie updateByFindMerge(Long movieId) {
        return movieProvider.updateByFindMerge (movieId);
    }

    /**
     * @param dto new movie instance dto
     * @return new attached movie instance
     */
    public Movie mergeNewNonAttachIns(MovieDto dto) {
        Movie newNonAttachIns = movieMapper.movieDtoToEntity(dto);
        System.out.println("------ New non attach instance: " + newNonAttachIns );
        Movie attachIns = movieProvider.mergeNewNonAttachIns (newNonAttachIns);
        System.out.println("------ New non attach instance after merge, em "+ (movieProvider.isContain (attachIns) == true
                ? "contains " : "not contain ")+ attachIns );
        return attachIns;
    }

    /**
     * @param movieId define movie id to detach and then merge
     * @return new attached movie instance
     */
    public Movie mergeDetachIns(Long movieId) {
        Movie detachTargetMovie = movieProvider.findMovieById (movieId);
        System.out.println("------ After find, em "+ (movieProvider.isContain (detachTargetMovie) == true
                ? "contains " : "not contain ")+ detachTargetMovie );
        movieProvider.detachMovieById (movieId);
        System.out.println("------ After detach, em "+ (movieProvider.isContain (detachTargetMovie) == true
                ? "contains " : "not contain ")+ detachTargetMovie );
        detachTargetMovie.setMovieName (detachTargetMovie.getMovieName () + "_become detached then change name by merge");
        Movie attachIns = movieProvider.mergeDetachIns (detachTargetMovie);
        System.out.println("------ "+ detachTargetMovie +" after merge, em "+ (movieProvider.isContain (attachIns) == true
                ? "contains " : "not contain ")+ attachIns );
        return attachIns;
    }

    /**
     * @param movieId define movie id whose entity is attach
     * @return
     */
    public Movie mergeAttachIns(Long movieId) {
        Movie attachMovieWillChangeName = movieProvider.findMovieById (movieId);
        System.out.println("------ Originally attach instance, em "+ (movieProvider.isContain (attachMovieWillChangeName) == true
                ? "contains " : "not contain ")+ attachMovieWillChangeName );
        System.out.println ("------ Before merge: " + attachMovieWillChangeName.getMovieName ());
        attachMovieWillChangeName.setMovieName (
                attachMovieWillChangeName.getMovieName () + "_attached then change by merge"
        );
        Movie attachIns = movieProvider.mergeAttachIns (attachMovieWillChangeName);
        System.out.println("------ After merge the attach instance, em "+ (movieProvider.isContain (attachIns) == true
                ? "contains and should be same" : "not contain ")+ attachIns );
        System.out.println ("------ After merge, the name should be change: " + attachIns.getMovieName ());
        return attachIns;
    }

    /**
     *
     * @param dto
     * @return
     */
    public Movie mergeNewInsButExistedInPC(MovieDto dto) {
        Movie newInsButExist = movieMapper.movieDtoToEntity(dto);
        System.out.println("------ New instance but exist in persistence context, em "
                + (movieProvider.isContain (newInsButExist) == true
                ? "contains " : "not contain ")+ newInsButExist );
        Movie attachIns = movieProvider.findMovieById (dto.getId ());
        System.out.println("------ Cache one with same ID, em "+ (movieProvider.isContain (attachIns) == true
                ? "contains and be same" : "not contain ")+ attachIns );
        newInsButExist.setMovieName (attachIns.getMovieName ());
        Movie cacheAttachIns = movieProvider.mergeNewInsButExistedInPC (newInsButExist);
        System.out.println("------ After merge, cache one, em "+ (movieProvider.isContain (cacheAttachIns) == true
                ? "contains and be same" : "not contain ")+ cacheAttachIns );
        return cacheAttachIns;
    }



}
