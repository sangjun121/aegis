package me.sangjun.aegis.core.bootstrap;

import me.sangjun.aegis.core.config.AegisConfig;

public class AegisBootStrap {
    private final AegisConfig config;

    private AegisBootStrap(AegisConfig config) {
        this.config = config;
    }

    public static void run(Class rootClass) {
        /**
         * TODO:
         * 1. 관리 대상 도메인 등록
         * 2. 관리 대상에 대응하는 Validator 구현 여부 검사
         * 3. 관리 대상 도메인의 필드에 대응하는 validate 메소드 구현 여부 검사
         */
    }
}
