package customer;

import rental.FilmTypeEnum;

import java.math.BigInteger;

public enum FilmTypeBonus {

    NEW_RELEASE(BigInteger.valueOf(2)),
    OTHER(BigInteger.valueOf(1));

    private final BigInteger bonus;

    FilmTypeBonus(BigInteger bonus) {

        this.bonus = bonus;
    }

    public static FilmTypeBonus of(FilmTypeEnum name) {
        if (FilmTypeEnum.NEW_RELEASE.equals(name)) return NEW_RELEASE;
        else return OTHER;
    }

    public BigInteger getBonus() {
        return bonus;
    }
}
