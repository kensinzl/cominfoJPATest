package dto;


public class MovieDto {

    private Long Id;

    private String movieName;

    private Integer releaseYear;

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
//        return "The Movie DTO, " +"ID: " + this.Id + ", Movie_Name: " + this.movieName +
//                ", Release_Year: " + this.releaseYear + ", Language: " + this.language;
//    }

}
