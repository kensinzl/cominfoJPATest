import callBack.Boss;
import callBack.CallBackInterface;
import callBack.Slaver;
import common.Gender;
import dto.AddressDto;
import dto.EmailDto;
import dto.EmployeeDto;
import dto.MovieDto;
import entity.Employee;
import entity.Movie;
import service.EmployeeService;
import service.MovieService;

import java.util.Arrays;


public class Application {
    public static void main(String[] args) {

        /**
         * Just a small test case for the callBack
         * https://www.zhihu.com/question/25504849
         */
        // 1. traditional way to pass class as param
        CallBackInterface boss = new Boss ();
        Slaver slaver = new Slaver (boss);
        slaver.dailyWork ();

        // 2. anonymous class
        slaver = new Slaver (new CallBackInterface () {
            @Override
            public void execute() {
                System.out.println (">>>>>>> Rest a while. ");
            }
        });
        slaver.dailyWork ();

        // 3. Lambda Java8
        CallBackInterface agent = () -> System.out.println (">>>>>>> I will give you a new job. ");
        slaver = new Slaver (agent);
        slaver.dailyWork ();

        /**
         *
         */
        Thread thread = new Thread (new Runnable () {
            @Override
            public void run() {
                System.out.println (">>>>>>> This is a thread. ");
            }
        });

        thread.start ();

        thread = new Thread (() -> System.out.println (">>>>>>> use lambda to print. "));
        thread.start ();



        // Mock the UI
        MovieService movieService = new MovieService();
        EmployeeService employeeService = new EmployeeService();

//        /**
//         * Persist Action(new instance)
//         *
//         * 1. before persist, new movie instance is not contained by persistence context
//         * 2. after persist, new movie instance is contained by persistence context
//         */
//        MovieDto movieDto = createMovieDto ("Three Kings", 2019, "JP");
//        movieService.saveMovie (movieDto);
//        System.out.println ("------ persist one movie instance into persistence context ------ ");
//        System.out.println ("------ insert one movie into database ------ ");
//
//
//        // detach the instance from persistence context
//        movieService.detachMovieById (1L);
//        System.out.println ("------ detach this instance from persistence context ------ ");
//
//        System.out.println ("------ switch into SQL query to fetch because of detach ------ ");
//        Movie movieEntityFromDB = movieService.findMovieById (1L);
//        System.out.println ("------ fetch SQL End ------ ");
//        System.out.println("------ After find, movieEntityFromDB, em "
//                + (movieService.isContain (movieEntityFromDB) == true ? "contains " : "not contain ")
//                + movieEntityFromDB);
//
//
//        Movie movieEntityFromCache = movieService.findMovieById (1L);
//        System.out.println ("------ find again, movieEntityFromCache is the cache of movieEntityFromDB among the persistence context, "
//                        + movieEntityFromCache + "No SQL Start ------ ");
//
//        movieService.detachMovieById (1L);
//        System.out.println ("------ detach again ------ ");
//
//        System.out.println("------ After find and then detach, movieEntityFromCache, em "
//                + (movieService.isContain (movieEntityFromCache) == true ? "contains " : "not contain ")
//                + movieEntityFromCache);
//        System.out.println("------ After find and then detach, movieEntityFromDB, em "
//                + (movieService.isContain (movieEntityFromDB) == true ? "contains " : "not contain ")
//                + movieEntityFromDB);
//
//
//        /**
//         * Merge Action(return value)
//         *
//         * 1. merge(new non attach instance), insert it for database and return new attach instance
//         * 2. merge(detach instance), update the changes and return new attach instance
//         * 3. merge(attach instance), upate the changes and return the cache instance
//         * 4. merge(new instance but attach and exist in persistence context), update and return the cache instance
//         *
//         */
//        MovieDto dto = createMovieDto ("new non attach instance", 2019, "EN");
//        Movie newAttachInstance = movieService.mergeNewNonAttachIns (dto);
//
//        Movie detachToAttachByMerge = movieService.mergeDetachIns (1L);
//
//        Movie attachAgainByMerge = movieService.mergeAttachIns (1L);
//
//        dto.setId (1L);
//        dto.setLanguage (dto.getLanguage () + "_new ins but exist");
//        Movie newInsAttachExistInPC = movieService.mergeNewInsButExistedInPC (dto);
//
//
//        System.out.println("************ ");
//
//        // use find or merge to update.
//        /**
//         * Update Action
//         * 1. find
//         * 2. merge
//         * 3. find, then change the attribute and merge(better)
//         */
//        MovieDto updatingMovieDtoByFind = createMovieDto ("updating movie dto by find", 2019, "CN");
//        movieService.mergeNewNonAttachIns (updatingMovieDtoByFind);
//        updatingMovieDtoByFind.setId (3L);
//        movieService.updateMovieByFind (updatingMovieDtoByFind);
//
//        MovieDto updatingMovieDtoMerge = createMovieDto ("updating movie dto by merge", 2019, "CS");
//        movieService.mergeNewNonAttachIns (updatingMovieDtoMerge);
//        updatingMovieDtoMerge.setId (4L);
//        movieService.updateMovieByMerge (updatingMovieDtoMerge);
//
//        movieService.updateByFindMerge(4L);


        /**
         * Cascade Action(Persist)
         * bidirectional Dto(current case)
         * Employee <=> Email, Employee can get the relative Email, and vice versa
         *
         * unidirectional Dto
         * Employee => Email, Employee can get the relative Email, but Email can not back to Employee
         *
         *
         * The following method: Updating the associations on both entities is an error-prone task.
         * https://thoughts-on-java.org/hibernate-tips-map-bidirectional-many-one-association/
         *
         * EmployeeDto employeeDto = createUniEmployeeDtoAndEmailDto();
         *
         * public static EmployeeDto createUniEmployeeDtoAndEmailDto() {
         *		EmployeeDto employeeDto = new EmployeeDto ();
         *		employeeDto.setGender (Gender.MAILE.toString ());
         *		employeeDto.setEmployeeName ("Kensin");
         *
         *		EmailDto gmailEmailDto = new EmailDto ();
         *		gmailEmailDto.setEmailAddress ("zlchldjyy@gmail.com");
         *		gmailEmailDto.setEmployeeDto (employeeDto);
         *
         *		EmailDto qqEmailDto = new EmailDto ();
         *		qqEmailDto.setEmailAddress ("851561339@qq.com");
         *		qqEmailDto.setEmployeeDto (employeeDto);
         *
         *		EmailDto yahooEmailDto = new EmailDto ();
         *		yahooEmailDto.setEmailAddress ("zldwdgm@yahoo.co.jp");
         *		yahooEmailDto.setEmployeeDto (employeeDto);
         *
         *		Set emailDtoSet = new HashSet ();
         *		emailDtoSet.addAll (Arrays.asList (gmailEmailDto, qqEmailDto, yahooEmailDto));
         *
         *		employeeDto.setEmailsDto (emailDtoSet);
         *
         *		return employeeDto;
         *		}
         */
        EmployeeDto employeeDto = createBiAssociationBetweenEmployeeDtoAndEmailDto ();
        employeeService.saveEmployee (employeeDto);

        /**
         * Employee Address Eager & Email Lazy
         */
        employeeService.detachEmployeeById (1L);
//        Employee employeeFetchedFromDB = employeeService.findEmployeeById (1L);
//        employeeFetchedFromDB.getEmails ().stream ()
//                .forEach (email -> System.out.println ("emails: " + email.getEmailAddress ()));

        Employee employeeFetchedFromDB = employeeService.findEmployeeByIdWithJPQL(1L);
        employeeFetchedFromDB.getEmails ().stream ()
                .forEach (email -> System.out.println ("emails: " + email.getEmailAddress ()));

//        /**
//         * Employee FetchType.LAZY.
//         */
//        Employee employeeFromPSWithEmail = employeeService.detachEmployeeByIdThenReturnDetachedEmployee (1L);
//        System.out.println ("  >>>>>>>>> this employee from persistence context which was saved by employeeService.saveEmployee method, so with email. ");
//        employeeFromPSWithEmail.getEmails ().stream ()
//                .forEach (email -> System.out.println ("    emails: " + email.getEmailAddress ()));
//
//        System.out.println ("  >>>>>>>>> this employee is already not in the persistence context which has been detached. ");
//        System.out.println ("    >>>>>>>>> if using em.find and detach again, the lazy proxy will be cutted from DB. " +
//                "Proxy session close issue when fetching email. ");
//        Employee employeeFromDBWithoutEmail = employeeService.detachEmployeeByIdThenReturnDetachedEmployee (1L);
//        try {
//            employeeFromDBWithoutEmail.getEmails ().stream ()
//                    .forEach (email -> System.out.println ("emails: " + email.getEmailAddress ()));
//        }catch (Exception ex) {
//            System.out.println ("    ---- Exception: " + ex.getMessage ());
//        }
//
//        System.out.println ("    >>>>>>>>> if using em.find, it will fetch from DB invoking the lazy mode. Only fetch email when needed. ");
//        Employee employeeFromDB = employeeService.findEmployeeById (1L);
//        employeeFromDB.getEmails ().stream ()
//                .forEach (email -> System.out.println ("emails: " + email.getEmailAddress ()));
//
//        System.out.println ("    >>>>>>>>> if use JPQL left join fetch, directly get the emails  ");
//        Employee employeeWithEmailByFetch = employeeService.findEmployeeByIdWithJPQL (1L);
//        employeeWithEmailByFetch.getEmails ().stream ()
//                .forEach (email -> System.out.println ("emails: " + email.getEmailAddress ()));
//
//        /**
//         * Cascade Action(Update Owning Table)
//         */
//        EmployeeDto savedEmployeeDto = employeeService.saveEmployeeReturnAttachedEmployeeDto (employeeDto);
//        savedEmployeeDto.setEmployeeName ("Naruto");
//        savedEmployeeDto.getEmailsDto ().stream ().forEach (emailDto ->
//            emailDto.setEmailAddress (emailDto.getEmailAddress () + "_*")
//        );
//        employeeService.updateEmployee (savedEmployeeDto);
//
//
//        /**
//         * Cascade Action(Delete Inverse Table)
//         *
//         *  1. em.remove
//         *  2. JPQL
//         *
//         *  ->  Three common method to delete
//         *          https://www.codejava.net/frameworks/hibernate/hibernate-basics-3-ways-to-delete-an-entity-from-the-datastore
//         *  ->  @ManyToMany should take care to use em.remove
//         *          https://thoughts-on-java.org/avoid-cascadetype-delete-many-assocations/
//         *
//         *      Query query1 = em.createQuery("delete Email e where e.id > :id");
//         *      query1.setParameter("id", 5L);
//         *      query1.executeUpdate();
//         *
//         *      Query query2 = em.createQuery("delete Employee e where e.id=:id");
//         *      query2.setParameter("id", id);
//         *      query2.executeUpdate();
//         */
//        //employeeService.deleteEmployee (5L);












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

    public static EmployeeDto createBiAssociationBetweenEmployeeDtoAndEmailDto() {
        EmployeeDto employeeDto = new EmployeeDto ();
        employeeDto.setGender (Gender.MAILE.toString ());
        employeeDto.setEmployeeName ("Kensin");

        EmailDto gmailEmailDto = new EmailDto ();
        gmailEmailDto.setEmailAddress ("zlchldjyy@gmail.com");

        EmailDto qqEmailDto = new EmailDto ();
        qqEmailDto.setEmailAddress ("851561339@qq.com");

        EmailDto yahooEmailDto = new EmailDto ();
        yahooEmailDto.setEmailAddress ("zldwdgm@yahoo.co.jp");

        AddressDto northShore = new AddressDto ();
        northShore.setAddress ("North Shore, Auckland");

        AddressDto edenTerrace = new AddressDto ();
        edenTerrace.setAddress ("Eden Terrace, Auckland");

        Arrays.asList (gmailEmailDto, qqEmailDto, yahooEmailDto)
                .stream ().forEach (emailDto -> employeeDto.addEmailDto (emailDto));

        Arrays.asList (northShore, edenTerrace)
                .stream ().forEach (addressDto -> employeeDto.addAddressDto (addressDto));

        return employeeDto;
    }
}
