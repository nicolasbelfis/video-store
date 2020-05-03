package rental;

import java.math.BigDecimal;

public enum FilmTypeEnum {
    NEW_RELEASE((days, price) -> BigDecimal.valueOf(days.getNumberOfDays()).multiply(price)),
    REGULAR((days, price) -> BigDecimal.valueOf(days.minus(3).orZero().getNumberOfDays() + 1).multiply(price)),
    OLD((days, price) -> BigDecimal.valueOf(days.minus(5).orZero().getNumberOfDays() + 1).multiply(price));

    private final FilmTypePricing filmTypePricing;

    FilmTypeEnum(FilmTypePricing filmTypePricing) {

        this.filmTypePricing = filmTypePricing;
    }

    public FilmTypePricing getFilmTypePricing() {
        return filmTypePricing;
    }
}
