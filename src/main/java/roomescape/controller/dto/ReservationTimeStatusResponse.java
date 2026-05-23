package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeStatus;

public record ReservationTimeStatusResponse(
        Long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt,

        boolean available) {

    public static ReservationTimeStatusResponse from(ReservationTimeStatus status) {

        return new ReservationTimeStatusResponse(
                status.reservationTime().getId(),
                status.reservationTime().getStartAt(),
                status.available()
        );
    }
}
