package roomescape.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ThemeDao;
import roomescape.domain.Theme;
import roomescape.service.dto.PopularThemeInfo;
import roomescape.service.exception.DuplicateResourceException;
import roomescape.error.ErrorCode;
import roomescape.service.exception.ResourceInUseException;

@Service
public class ThemeService {

    private static final int POPULAR_THEME_PERIOD_DAYS = 7;
    private static final int POPULAR_THEME_LIMIT = 10;

    private final ThemeDao themeDao;
    private final ReservationDao reservationDao;
    private final Clock clock;

    public ThemeService(ThemeDao themeDao, ReservationDao reservationDao, Clock clock) {
        this.themeDao = themeDao;
        this.reservationDao = reservationDao;
        this.clock = clock;
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public List<PopularThemeInfo> findPopularThemes() {
        LocalDate today = LocalDate.now(clock);
        return findPopularThemes(
                today.minusDays(POPULAR_THEME_PERIOD_DAYS),
                today.minusDays(1),
                POPULAR_THEME_LIMIT);
    }

    public List<PopularThemeInfo> findPopularThemes(LocalDate startDate, LocalDate endDate, int limit) {
        return themeDao.findPopularThemes(startDate, endDate, limit)
                .stream()
                .map(result -> new PopularThemeInfo(
                        result.theme(),
                        result.reservationCount()
                ))
                .toList();
    }

    public Theme save(Theme theme) {
        if (themeDao.existsByName(theme.getName())) {
            throw new DuplicateResourceException(ErrorCode.DUPLICATE_THEME);
        }
        return themeDao.save(theme);
    }

    public void deleteById(Long id) {
        if (reservationDao.existByThemeId(id)) {
            throw new ResourceInUseException("기존 예약이 존재하는 테마는 삭제할 수 없습니다.");
        }
        themeDao.deleteById(id);
    }
}
