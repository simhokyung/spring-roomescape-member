package roomescape.domain;

import roomescape.exception.InvalidInputException;

public class ReservationName {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 10;

    private final String value;

    public ReservationName(String value) {
        validate(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidInputException("이름 형식은 " + MIN_LENGTH + "글자 이상 " + MAX_LENGTH + "글자 이하입니다.");
        }

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidInputException("이름 형식은 " + MIN_LENGTH + "글자 이상 " + MAX_LENGTH + "글자 이하입니다.");
        }
    }
}
