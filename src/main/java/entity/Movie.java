package entity;

import javax.persistence.*;

@Entity
@Table(name = "MOVIE")
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "movie_id", nullable = false, unique = true)
    private Long Id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "language")
    private String language;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

//    @Override
//    public String toString() {
//        return "ID: " + this.id + ", Movie_Name: " + this.movieName +
//                ", Release_Year: " + this.releaseYear + ", Language: " + this.language;
//    }

}
