package com.anbit.fashionist.constant;

public enum EErrorCode {
    SERVER_ERROR(1000, "App Server Error, please contact the admin"),
    MISSING_HEADERS(1001, "Missing Headers"),
    MISSING_PARAM(1002, "Missing Parameters"),
    MALFORMED_JSON(1003, "Malformed JSON"),
    INVALID_LIMIT(1004, "Invalid offset or limit"),
    INVALID_LOCALE(1005, "Invalid Locale"),
    INVALID_TIMEZONE(1006, "Invalid Timezone"),
    LIMIT_REQUEST(1007, "You exceeded the limit of requests per minute, Please try again after sometime."),
    UNAUTHORIZED(1101, "You are Unauthorized, Please login"),
    AUTH_FAIL(1102, "Authentication Failed"),
    NOT_FOUND(1103, "Not Found"),
    SESSION_EXP(1201, "Your session is expired, please login again"),
    SESSION_INVALID(1202, "Your sessions is invalid"),
    TOKEN_INVALID(1203, "Your sessions token is invalid"),
    INVALID_CRED(1301, "Invalid Credentials"),
    ACCOUNT_DISABLED(1302, "You Account is disabled by the admin"),
    INVALID_PHONE_NUMBER(1303, "Invalid phone number"),
    RESOURCE_EXIST(1304, "Resource already exists"),
    WRONG_OTP(1305, "Wrong OTP value"),
    INVALIND_REQUEST(1306, "Invalid request"),
    FILE_NOT_EXIST(1307, "File not exist"),
    FILE_TOO_LARGE(1308, "File too large");


    private final int code;
    private final String description;

    private EErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}

