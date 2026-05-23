package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.domain.exception.DomainValidationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ThemeTest {

    @Test
    void 테마_이름은_비어있을_수_없다() {
        assertThatThrownBy(() -> new Theme("", "설명", "썸네일"))
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void 테마_설명은_비어있을_수_없다() {
        assertThatThrownBy(() -> new Theme("공포", "", "썸네일"))
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void 테마_썸네일은_비어있을_수_없다() {
        assertThatThrownBy(() -> new Theme("공포", "설명", ""))
                .isInstanceOf(DomainValidationException.class);
    }
}
