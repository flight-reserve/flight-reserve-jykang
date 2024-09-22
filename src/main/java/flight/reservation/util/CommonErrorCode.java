package flight.reservation.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    LOCK_NOT_AVAILABLE(HttpStatus.LOCKED, "Resource is locked"),
    LOCK_INTERRUPTED_ERROR(HttpStatus.CONFLICT,"Resource is conflict"),

    FLIGHT_FULL(HttpStatus.BAD_REQUEST,"Flight fully booked"),

    UNLOCKING_A_LOCK_WHICH_IS_NOT_LOCKED(HttpStatus.INTERNAL_SERVER_ERROR,"Lock release failed");

    private final HttpStatus httpStatus;
    private final String message;
}
