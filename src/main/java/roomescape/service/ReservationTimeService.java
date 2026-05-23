package roomescape.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeStatus;
import roomescape.service.exception.DuplicateResourceException;
import roomescape.error.ErrorCode;
import roomescape.service.exception.ResourceInUseException;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;
    private final Clock clock;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao, Clock clock) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
        this.clock = clock;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime save(ReservationTime reservationTime) {
        if (reservationTimeDao.existsByStartAt(reservationTime.getStartAt())) {
            throw new DuplicateResourceException(ErrorCode.DUPLICATE_RESERVATION_TIME);
        }
        return reservationTimeDao.save(reservationTime);
    }

    public void deleteById(Long id) {
        validateHasTime(id);
        reservationTimeDao.deleteById(id);
    }

    public List<ReservationTimeStatus> findReservationTimeByDateAndThemeId(LocalDate date, Long themeId) {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        List<Long> timeIds = reservationDao.findReservedTimeIdsByDateAndThemeId(date, themeId);

        LocalDateTime now = LocalDateTime.now(clock);
        return reservationTimes.stream()
                .map(reservationTime -> {
                    boolean available = !timeIds.contains(reservationTime.getId())
                            && !reservationTime.isPast(date, now);
                    return new ReservationTimeStatus(reservationTime, available);
                })
                .toList();
    }

    private void validateHasTime(Long id) {
        if (reservationDao.existByTimeId(id)) {
            throw new ResourceInUseException("예약이 존재해 삭제할 수 없습니다");
        }
    }
}
