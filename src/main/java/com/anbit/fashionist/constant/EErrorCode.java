package com.anbit.fashionist.constant;

public enum EErrorCode {
    MISSING_HEADERS(1001),
    MISSING_PARAM(1002);

    private int code;

    EErrorCode(int code)
    {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EErrorCode fromCode(int code) {
        switch (code) {
            case 1001: {
                return MISSING_HEADERS;
            }

            case 1002: {
                return MISSING_PARAM;
            }

            default: {
                throw new UnsupportedOperationException(
                    String.format("Unkhown status: '%s'", code)
                );
            }
        }
    }
}
