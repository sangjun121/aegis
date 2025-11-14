package me.sangjun.aegis.core.exception;

public enum ValidatorErrorMessage {
    VALIDATOR_TYPE_NULL("validator의 제네럴 타입 변수가 null일 수 없습니다."),
    VALIDATOR_DUPLICATED("domain과 매칭되는 validator는 중복일 수 없습니다."),
    INVALID_VALIDATOR_DOMAIN("관리 대상으로 등록된 Domain의 Validator가 아닙니다."),
    INVALID_VALIDATOR_TYPE("validator의 제네럴 타입 변수가 올바르지 않습니다.");

    private final String message;

    ValidatorErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
