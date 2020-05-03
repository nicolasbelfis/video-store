package rental;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmPricingTest {


    @Test
    public void calculate_price_newrelease() {
        BigDecimal price = FilmTypeEnum.NEW_RELEASE.getFilmTypePricing()
                .calculatePrice(new NumberOfDays(5), BigDecimal.TEN);
        assertEquals(BigDecimal.valueOf(50), price);
    }

    @Test
    public void calculate_price_old_five_days() {
        BigDecimal price = FilmTypeEnum.OLD.getFilmTypePricing()
                .calculatePrice(new NumberOfDays(5), BigDecimal.ONE);
        assertEquals(BigDecimal.valueOf(1), price);
    }

    @Test
    public void calculate_price_old_less_than_five() {
        BigDecimal price = FilmTypeEnum.OLD.getFilmTypePricing()
                .calculatePrice(new NumberOfDays(4), BigDecimal.ONE);
        assertEquals(BigDecimal.valueOf(1), price);
    }

    @Test
    public void calculate_price_old_more_than_five() {
        BigDecimal price = FilmTypeEnum.OLD.getFilmTypePricing()
                .calculatePrice(new NumberOfDays(6), BigDecimal.ONE);
        assertEquals(BigDecimal.valueOf(2), price);
    }

    @Test
    public void calculate_price_regular_three_days() {
        BigDecimal price = FilmTypeEnum.REGULAR.getFilmTypePricing()
                .calculatePrice(new NumberOfDays(3), BigDecimal.ONE);
        assertEquals(BigDecimal.valueOf(1), price);
    }

    @Test
    public void calculate_price_regular_less_than_three() {
        BigDecimal price = FilmTypeEnum.REGULAR.getFilmTypePricing()
                .calculatePrice(new NumberOfDays(2), BigDecimal.ONE);
        assertEquals(BigDecimal.valueOf(1), price);
    }

    @Test
    public void calculate_price_regular_more_than_three() {
        BigDecimal price = FilmTypeEnum.REGULAR.getFilmTypePricing()
                .calculatePrice(new NumberOfDays(4), BigDecimal.ONE);
        assertEquals(BigDecimal.valueOf(2), price);
    }
}