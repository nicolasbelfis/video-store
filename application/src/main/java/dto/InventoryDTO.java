package dto;

import java.math.BigInteger;

public class InventoryDTO {
    public InventoryDTO(String filmId, BigInteger stock) {
        this.filmId = filmId;
        this.stock = stock;
    }

    public String getFilmId() {
        return filmId;
    }

    public BigInteger getStock() {
        return stock;
    }

    private final String filmId;

    public void setStock(BigInteger stock) {
        this.stock = stock;
    }

    private BigInteger stock;
}
