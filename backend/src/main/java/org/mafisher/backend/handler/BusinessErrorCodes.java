package org.mafisher.backend.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCodes{

    NO_CODE(0,"No code", HttpStatus.NOT_IMPLEMENTED),
    INCORRECT_CURRENT_PASSWORD(300, "Current password is incorrect", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH(301, "New password does not match", HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(304, "Login and / or password is incorrect", HttpStatus.FORBIDDEN),
    BAD_COOKIE(305, "No jwt cookie found", HttpStatus.BAD_REQUEST),
    BAD_JWT_TOKEN(306, "Invalid JWT token", HttpStatus.BAD_REQUEST),
    EMAIL_IS_USED(307, "Email is used", HttpStatus.BAD_REQUEST),
    NICKNAME_IS_USED(308, "Nick name is used", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(312, "Token expired", HttpStatus.BAD_REQUEST),
    INCORRECT_USER_ID(313, "User of such ID does not exist", HttpStatus.BAD_REQUEST),
    INCORRECT_USER(315, "User does not exist", HttpStatus.BAD_REQUEST),
    NO_TOKEN(316,"This token does not exit", HttpStatus.BAD_REQUEST),
    BAD_CATEGORY_ID(317, "Bad category id", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(318, "Category not found", HttpStatus.BAD_REQUEST),
    DUPLICATE_CATEGORY(319, "Duplicate category", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;


}
