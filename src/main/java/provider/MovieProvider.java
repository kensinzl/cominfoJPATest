package provider;

import entity.JPAUtil;
import entity.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MovieProvider {

    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction transaction = null;

    /**
     * Persist action no return value
     *
     * 1. before persist, new movie instance is not contained by persistence context
     * 2. after persist, new movie instance is contained by persistence context
     *
     * @param movie
     */
    public void saveMovie(Movie movie) {
        transaction = em.getTransaction();
        transaction.begin();
        em.persist(movie);
        transaction.commit();
    }

    /**
     * Search the instance stored in the persistence context or database
     *
     * 1. If the instance is already stored in the persistence context, directly the cached version is returned.
     * 2. Otherwise, a new instance will be constructed and loaded with state from the database.
     * 3. Finally, if no entity with the given type and identity exists in the database, this method returns null.
     *
     * @param id PK
     * @return Movie instance
     */
    public Movie findMovieById(Long id) {
        return em.find (Movie.class, id);
    }

    /**
     * Detach the instance from persistence context
     *
     * @param id PK
     */
    public void detachMovieById(Long id) {
        Movie detachedTarget = em.find (Movie.class, id);
        em.detach (detachedTarget);
    }

    /**
     *
     * @param entity
     * @return true entity is contained in persistence context, otherwise false
     */
    public boolean isContain(Object entity) {
        return em.contains (entity);
    }


    public Movie updateMovieByFind(Movie updatingMovie) {
        transaction = em.getTransaction();
        transaction.begin();
        Movie existedMovie = em.find (Movie.class, updatingMovie.getId ());
        existedMovie.setMovieName (updatingMovie.getMovieName () + "_find update");
        transaction.commit ();
        return existedMovie;
    }

    public Movie updateMovieByMerge(Movie updatingMovie) {
        transaction = em.getTransaction();
        transaction.begin();
        Movie existedMovie = em.merge (updatingMovie);
        existedMovie.setMovieName (updatingMovie.getMovieName () + "_merge update");
        transaction.commit ();
        return existedMovie;
    }

    /**
     * Using em.find, change the value of attribute and then em.merge to update the database
     *
     * @param id PK of Movie
     * @return updated attach movie instance
     */
    public Movie updateByFindMerge(Long id) {
        transaction = em.getTransaction();
        transaction.begin();
        Movie attachMovie = em.find (Movie.class, id);
        attachMovie.setMovieName (attachMovie.getMovieName () + "_change by find and merge");
        em.merge (attachMovie);
        transaction.commit ();
        return  attachMovie;
    }

    /**
     * Merge Action
     * 1. attach instance = em.merge(new non-attach instance) -> insert action, return new one
     *
     * @param newNonAttachInstance
     * @return
     */
    public Movie mergeNewNonAttachIns(Movie newNonAttachInstance) {
        transaction = em.getTransaction ();
        transaction.begin ();
        Movie attachInstance = em.merge (newNonAttachInstance);
        transaction.commit ();
        return attachInstance;
    }

    /**
     * Merge action
     * 2. attach instance = em.merge(detach instance) -> update the changes, return new one
     *
     * @param detachIns
     * @return
     */
    public Movie mergeDetachIns(Movie detachIns) {
        transaction = em.getTransaction ();
        transaction.begin ();
        Movie attachInstance = em.merge (detachIns);
        transaction.commit ();
        return attachInstance;
    }

    /**
     * Merge action
     * 3. attach instance = em.merge(attach instance) -> state no change,
     * but update if attribute changes happen, return cache one
     *
     * @param attachIns
     * @return
     */
    public Movie mergeAttachIns(Movie attachIns) {
        transaction = em.getTransaction ();
        transaction.begin ();
        Movie attachCacheInstance = em.merge (attachIns);
        transaction.commit ();
        return attachCacheInstance;
    }

    /**
     * Merge action
     * 4. attach instance = em.merge(new instance but existed on the persistence context) -> update, return the cache one
     *
     * @param newInsExPC new instance but existed in the persistence context
     * @return
     */
    public Movie mergeNewInsButExistedInPC(Movie newInsExPC) {
        transaction = em.getTransaction ();
        transaction.begin ();
        Movie attachCacheInstance = em.merge (newInsExPC);
        transaction.commit ();
        return attachCacheInstance;
    }
}
