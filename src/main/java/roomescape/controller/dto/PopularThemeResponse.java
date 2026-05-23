package roomescape.controller.dto;

import roomescape.service.dto.PopularThemeInfo;

public record PopularThemeResponse(
        Long id,
        String name,
        String description,
        String thumbnail,
        int reservationCount
) {
    public static PopularThemeResponse from(PopularThemeInfo popularTheme) {

        return new PopularThemeResponse(
                popularTheme.theme().getId(),
                popularTheme.theme().getName(),
                popularTheme.theme().getDescription(),
                popularTheme.theme().getThumbnail(),
                popularTheme.reservationCount()
        );
    }
}
