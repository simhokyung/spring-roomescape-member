package roomescape.service.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeStatus(
        ReservationTime reservationTime,
        boolean available
) {
}
