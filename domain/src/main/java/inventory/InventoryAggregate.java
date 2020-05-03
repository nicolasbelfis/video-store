package inventory;

import inventory.exceptions.InventoryEmptyOperation;

import java.math.BigInteger;

public class InventoryAggregate {

    private final String filmId;

    private BigInteger stock;

    public InventoryAggregate(String filmId, BigInteger stock) {
        if (BigInteger.valueOf(-1).max(stock).equals(BigInteger.valueOf(-1))) throw new IllegalArgumentException();
        this.filmId = filmId;
        this.stock = stock;
    }

    public InventoryAggregate increment() {
        this.stock = this.stock.add(BigInteger.ONE);
        return this;
    }

    public InventoryAggregate decrement() throws InventoryEmptyOperation {
        if (BigInteger.ZERO.equals(this.stock)) throw new InventoryEmptyOperation();
        this.stock = this.stock.subtract(BigInteger.ONE);
        return this;
    }

    public String getFilmId() {
        return filmId;
    }

    public BigInteger getStock() {
        return stock;
    }

}
