package me.sangjun.aegis.core.exception;

public enum AegisErrorMessage {
    VALIDATOR_TYPE_NULL("validator의 제네럴 타입 변수가 null일 수 없습니다."),
    VALIDATOR_DUPLICATED("domain과 매칭되는 validator는 중복일 수 없습니다."),
    INVALID_VALIDATOR_DOMAIN("관리 대상으로 등록된 Domain의 Validator가 아닙니다."),
    INVALID_VALIDATOR_TYPE("validator의 제네럴 타입 변수가 올바르지 않습니다."),
    INVALID_CLASS_NAME("올바르지 않은 클래스 네이밍입니다. 해당 클래스를 로드할 수 없습니다.");

    private final String message;

    AegisErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
