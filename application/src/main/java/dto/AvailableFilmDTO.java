package dto;

public class AvailableFilmDTO {

    public AvailableFilmDTO(String filmId, String filmName, String filmCategory, int stock) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.filmCategory = filmCategory;
        this.stock = stock;
    }

    public AvailableFilmDTO() {
    }

    private String filmId;

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmCategory() {
        return filmCategory;
    }

    public void setFilmCategory(String filmCategory) {
        this.filmCategory = filmCategory;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    private String filmName;
    private String filmCategory;
    private int stock;

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }
}
