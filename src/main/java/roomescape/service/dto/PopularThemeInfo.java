package roomescape.service.dto;

import roomescape.domain.Theme;

public record PopularThemeInfo(
        Theme theme,
        int reservationCount
) {
}
