package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import roomescape.exception.InvalidInputException;
import roomescape.exception.PastReservationException;

@Getter
public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final LocalDate date;
    private final ReservationTime time;
    private final Theme theme;

    private Reservation(Long id, ReservationName name, LocalDate date, ReservationTime time, Theme theme) {
        validateRequired(name, date, time, theme);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public static Reservation create(String name, LocalDate date, ReservationTime time, Theme theme, LocalDateTime now) {
        validateNow(now);

        Reservation reservation = new Reservation(null, new ReservationName(name), date, time, theme);
        reservation.validateCreatableDateTime(now);
        return reservation;
    }

    public static Reservation restore(Long id, String name, LocalDate date, ReservationTime time, Theme theme) {
        return new Reservation(id, new ReservationName(name), date, time, theme);
    }

    public Reservation updateDateAndTime(LocalDate date, ReservationTime time, LocalDateTime now) {
        validateNow(now);

        if (isPast(now)) {
            throw new PastReservationException("지난 예약은 변경할 수 없습니다.");
        }

        Reservation updatedReservation = new Reservation(id, name, date, time, theme);
        updatedReservation.validateUpdatableDateTime(now);
        return updatedReservation;
    }

    public String getName() {
        return name.getValue();
    }

    public Long getTimeId() {
        return time.getId();
    }

    public Long getThemeId() {
        return theme.getId();
    }

    public boolean isPast(LocalDateTime now) {
        return time.isPast(date, now);
    }

    private static void validateRequired(ReservationName name, LocalDate date, ReservationTime time, Theme theme) {
        validateName(name);
        validateDate(date);
        validateTime(time);
        validateTheme(theme);
    }

    private void validateCreatableDateTime(LocalDateTime now) {
        if (time.isPast(date, now)) {
            throw new PastReservationException("지난 날짜 또는 시간은 예약할 수 없습니다.");
        }
    }

    private void validateUpdatableDateTime(LocalDateTime now) {
        if (time.isPast(date, now)) {
            throw new PastReservationException("지난 날짜 또는 시간으로 변경할 수 없습니다.");
        }
    }

    private static void validateName(ReservationName name) {
        if (name == null) {
            throw new InvalidInputException("예약자 이름은 필수입니다.");
        }
    }

    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new InvalidInputException("예약 날짜는 필수입니다.");
        }
    }

    private static void validateTime(ReservationTime time) {
        if (time == null) {
            throw new InvalidInputException("예약 시간은 필수입니다.");
        }
    }

    private static void validateTheme(Theme theme) {
        if (theme == null) {
            throw new InvalidInputException("테마는 필수입니다.");
        }
    }

    private static void validateNow(LocalDateTime now) {
        if (now == null) {
            throw new InvalidInputException("현재 시각은 필수입니다.");
        }
    }
}
