package inventory;

import inventory.exceptions.InventoryEmptyOperation;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryAggregateTest {

    public static final String FILM = "ER3R";

    @Test
    public void when_empty_cannot_decrement() {
        assertThrows(
                InventoryEmptyOperation.class,
                () -> new InventoryAggregate(FILM, BigInteger.ZERO).decrement()
        );
    }

    @Test
    public void when_not_empty_decrement_substract_one() throws InventoryEmptyOperation {
        InventoryAggregate inventoryAggregate = new InventoryAggregate(FILM, BigInteger.ONE).decrement();
        assertEquals(BigInteger.ZERO, inventoryAggregate.getStock());
    }

    @Test
    public void when_increment_add_one() {
        InventoryAggregate inventoryAggregate = new InventoryAggregate(FILM, BigInteger.ONE).increment();
        assertEquals(BigInteger.valueOf(2), inventoryAggregate.getStock());
    }

    @Test
    public void cannot_create_negative_stock() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new InventoryAggregate(FILM, BigInteger.valueOf(-1))
        );
    }

    @Test
    public void test_inventory_info() {
        InventoryAggregate inventoryAggregate = new InventoryAggregate(FILM, BigInteger.ONE);
        assertEquals(BigInteger.ONE, inventoryAggregate.getStock());
        assertEquals(FILM, inventoryAggregate.getFilmId());
    }
}