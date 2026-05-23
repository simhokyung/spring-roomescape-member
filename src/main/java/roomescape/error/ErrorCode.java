package roomescape.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력 값이 올바르지 않습니다."),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, " 값의 형식이 올바르지 않습니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "요청 본문을 읽을 수 없습니다. JSON 형식과 각 필드의 타입을 확인해주세요."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
    DUPLICATE_RESERVATION(HttpStatus.CONFLICT, "이미 존재하는 예약입니다."),
    DUPLICATE_THEME(HttpStatus.CONFLICT, "이미 존재하는 테마 이름입니다."),
    DUPLICATE_RESERVATION_TIME(HttpStatus.CONFLICT, "이미 존재하는 예약시간입니다."),
    RESOURCE_IN_USE(HttpStatus.CONFLICT, "사용 중인 리소스입니다."),
    PAST_RESERVATION(HttpStatus.UNPROCESSABLE_ENTITY, "지난 예약은 처리할 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상하지 못한 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
