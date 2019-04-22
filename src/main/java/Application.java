import common.Gender;
import dto.EmailDto;
import dto.EmployeeDto;
import dto.MovieDto;
import entity.Email;
import entity.Movie;
import service.EmployeeService;
import service.MovieService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Application {
    public static void main(String[] args) {

        // Mock the UI
        MovieService movieService = new MovieService();
        EmployeeService employeeService = new EmployeeService();

        /**
         * Persist Action(new instance)
         *
         * 1. before persist, new movie instance is not contained by persistence context
         * 2. after persist, new movie instance is contained by persistence context
         */
        MovieDto movieDto = createMovieDto ("Three Kings", 2019, "JP");
        movieService.saveMovie (movieDto);
        System.out.println ("------ persist one movie instance into persistence context ------ ");
        System.out.println ("------ insert one movie into database ------ ");


        // detach the instance from persistence context
        movieService.detachMovieById (1L);
        System.out.println ("------ detach this instance from persistence context ------ ");

        System.out.println ("------ switch into SQL query to fetch because of detach ------ ");
        Movie movieEntityFromDB = movieService.findMovieById (1L);
        System.out.println ("------ fetch SQL End ------ ");
        System.out.println("------ After find, movieEntityFromDB, em "
                + (movieService.isContain (movieEntityFromDB) == true ? "contains " : "not contain ")
                + movieEntityFromDB);


        Movie movieEntityFromCache = movieService.findMovieById (1L);
        System.out.println ("------ find again, movieEntityFromCache is the cache of movieEntityFromDB among the persistence context, "
                        + movieEntityFromCache + "No SQL Start ------ ");

        movieService.detachMovieById (1L);
        System.out.println ("------ detach again ------ ");

        System.out.println("------ After find and then detach, movieEntityFromCache, em "
                + (movieService.isContain (movieEntityFromCache) == true ? "contains " : "not contain ")
                + movieEntityFromCache);
        System.out.println("------ After find and then detach, movieEntityFromDB, em "
                + (movieService.isContain (movieEntityFromDB) == true ? "contains " : "not contain ")
                + movieEntityFromDB);


        /**
         * Merge Action(return value)
         *
         * 1. merge(new non attach instance), insert it for database and return new attach instance
         * 2. merge(detach instance), update the changes and return new attach instance
         * 3. merge(attach instance), upate the changes and return the cache instance
         * 4. merge(new instance but attach and exist in persistence context), update and return the cache instance
         *
         */
        MovieDto dto = createMovieDto ("new non attach instance", 2019, "EN");
        Movie newAttachInstance = movieService.mergeNewNonAttachIns (dto);

        Movie detachToAttachByMerge = movieService.mergeDetachIns (1L);

        Movie attachAgainByMerge = movieService.mergeAttachIns (1L);

        dto.setId (1L);
        dto.setLanguage (dto.getLanguage () + "_new ins but exist");
        Movie newInsAttachExistInPC = movieService.mergeNewInsButExistedInPC (dto);


        System.out.println("************ ");

        // use find or merge to update.
        /**
         * Update Action
         * 1. find
         * 2. merge
         * 3. find, then change the attribute and merge(better)
         */
        MovieDto updatingMovieDtoByFind = createMovieDto ("updating movie dto by find", 2019, "CN");
        movieService.mergeNewNonAttachIns (updatingMovieDtoByFind);
        updatingMovieDtoByFind.setId (3L);
        movieService.updateMovieByFind (updatingMovieDtoByFind);

        MovieDto updatingMovieDtoMerge = createMovieDto ("updating movie dto by merge", 2019, "CS");
        movieService.mergeNewNonAttachIns (updatingMovieDtoMerge);
        updatingMovieDtoMerge.setId (4L);
        movieService.updateMovieByMerge (updatingMovieDtoMerge);

        movieService.updateByFindMerge(4L);


        /**
         *
         */
//        EmployeeDto employeeDto = new EmployeeDto ();
//        employeeDto.setGender (Gender.MAILE.toString ());
//        employeeDto.setEmployeeName ("Kensin");
//
//        EmailDto gmailEmailDto = new EmailDto ();
//        gmailEmailDto.setEmailAddress ("zlchldjyy@gmail.com");
//        gmailEmailDto.setEmployeeDto (employeeDto);
//
//        EmailDto qqEmailDto = new EmailDto ();
//        qqEmailDto.setEmailAddress ("851561339@qq.com");
//        qqEmailDto.setEmployeeDto (employeeDto);
//
//        EmailDto yahooEmailDto = new EmailDto ();
//        yahooEmailDto.setEmailAddress ("zldwdgm@yahoo.co.jp");
//        yahooEmailDto.setEmployeeDto (employeeDto);
//
//        Set emailSet = new HashSet ();
//        emailSet.addAll (Arrays.asList (gmailEmailDto, qqEmailDto, yahooEmailDto));
//        employeeDto.setEmailsDto (emailSet);


        EmployeeDto employeeDto = new EmployeeDto ();
        employeeDto.setGender (Gender.MAILE.toString ());
        employeeDto.setEmployeeName ("Kensin");

        EmailDto gmailEmailDto = new EmailDto ();
        gmailEmailDto.setEmailAddress ("zlchldjyy@gmail.com");
        gmailEmailDto.setEmployeeDto (employeeDto);

        EmailDto qqEmailDto = new EmailDto ();
        qqEmailDto.setEmailAddress ("851561339@qq.com");
        qqEmailDto.setEmployeeDto (employeeDto);

        EmailDto yahooEmailDto = new EmailDto ();
        yahooEmailDto.setEmailAddress ("zldwdgm@yahoo.co.jp");
        yahooEmailDto.setEmployeeDto (employeeDto);

        Set emailDtoSet = new HashSet<EmailDto> ();
        emailDtoSet.addAll (Arrays.asList (gmailEmailDto, qqEmailDto, yahooEmailDto));
        //employeeDto.setEmailsDto (emailSet);


//        emailDtoSet.stream ().map (emailDto -> {
//            employeeDto.addEmailDto (emailDto)
//        });

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers.stream().map( i -> i*i).distinct();







//
//
//        // update the instance
//        transaction = em.getTransaction();
//        transaction.begin();
//        newMovie.setMovieName("Three Kings From 2019");
//        selectedMovie = em.merge(newMovie); // have to reassign the newMovie, otherwise the em.contain not work
//        transaction.commit();
//        //System.out.println("After merge the instance, the em contain the instance: " + em.contains(selectedMovie));
//        System.out.println("After merge newMovie, em "+(em.contains(newMovie)==true ? "contains" : "not contain")+" this instance");
//        System.out.println("After merge newMovie return selectedMovie, em "+ (em.contains(selectedMovie) == true ? "contains" : "not contain")+" this instance");
//        //System.out.println("After merge the instance, the em contain the instance: " + newMovie.equals(selectedMovie));
//
//
//        transaction = em.getTransaction();
//        transaction.begin();
//        Movie movie2 = new Movie();
//        movie2.setLanguage("CN");
//        movie2.setMovieName("Robert");
//        movie2.setReleaseYear(2019);
//        em.persist(movie2);
//        transaction.commit();
//
//        transaction = em.getTransaction();
//        transaction.begin();
//        movie2.setMovieName("Robert Love");
//        em.merge(movie2);
//        transaction.commit();
//
//        transaction = em.getTransaction();
//        transaction.begin();
//        Movie movie3 = new Movie();
//        movie3.setLanguage("JP");
//        movie3.setMovieName("Tokyo Love Story");
//        movie3.setReleaseYear(1990);
//
//        try {
//            em.remove(newMovie); // detached entity will throw exception
//            em.remove(movie3); // new instance just ignore
//        }catch (Exception ex) {
//            System.out.println("error message: " + ex.getMessage());
//        }
//
//        em.persist(movie3);
//        transaction.commit();
//
//        transaction = em.getTransaction();
//        transaction.begin();
//        try {
//            em.remove(movie3);
//        }catch (Exception ex) {
//            System.out.println("Should no error");
//        }
//        transaction.commit();


//
//        Movie existedMovieId1L = em.find(Movie.class, 1L);
//        Movie existedMovieId2L = em.find(Movie.class, 2L);
//
//        System.out.println(existedMovieId1L);
//        System.out.println(existedMovieId2L);
//
//        System.out.println(em.contains(movie1));
//        System.out.println(em.contains(movie2));
//        System.out.println(em.contains(existedMovieId1L));
//        System.out.println(em.contains(existedMovieId2L));
//
////        em.detach(movie2);
////        System.out.println(em.contains(movie2));
////        System.out.println(em.contains(existedMovieId2L));
//
//        System.out.println("------------------------");
//
////        Movie existedMovieId5L = em.find(Movie.class, 5L);
////        System.out.println(em.contains(existedMovieId5L));
////        System.out.println(existedMovieId5L);
////
////        TypedQuery<Movie> query = em.createQuery("select m from Movie m where id = :id", Movie.class);
////        query.setParameter("id", 5L);
////        existedMovieId5L = query.getSingleResult();
////        System.out.println(existedMovieId5L);
//
//        System.out.println("******************");
//        Query queryMaxMin = em.createQuery("select max(m.id), min(m.id) from Movie m");
//        Object[] maxMinId = (Object[]) queryMaxMin.getSingleResult();
//
//        System.out.println(maxMinId[0]);
//        System.out.println(maxMinId[1]);

        System.out.println("-------- End ! -------");
    }


    public static MovieDto createMovieDto(String movieName, Integer releaseYear, String language) {
        MovieDto movieDto = new MovieDto ();
        movieDto.setLanguage(language);
        movieDto.setMovieName(movieName);
        movieDto.setReleaseYear(releaseYear);
        return movieDto;
    }
}
