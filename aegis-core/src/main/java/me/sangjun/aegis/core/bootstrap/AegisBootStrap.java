package me.sangjun.aegis.core.bootstrap;

import me.sangjun.aegis.core.api.AegisConfig;

public class AegisBootStrap {

    /**
     * Config 파일 없이 관리 대상 도메인을 등록하는 경우 사용 가능하다.
     * 따라서, @AegisDomain을 기반으로만 관리 대상 도메인을 등록한다.
     * @param primarySource : 사용자 어플리케이션의 main클래스를 의미한다.
     */
    public static void run(Class<?> primarySource) {
        /**
         * TODO:
         * 1. 관리 대상 도메인 등록
         * 2. 관리 대상에 대응하는 Validator 구현 여부 검사
         * 3. 관리 대상 도메인의 필드에 대응하는 validate 메소드 구현 여부 검사
         */
    }

    /**
     * 도메인 탐색을 Config 파일을 기반으로 관리 대상 도메인을 등록하는 경우 사용한다.
     * Config 파일 외에도, primarySource를 기반으로 하위 @AegisDomain을 탐지하고 관리 대상 도메인을 등록한다.
     * @param primarySource : 사용자 어플리케이션의 main클래스를 의미한다.
     * @param config : AegisConfig의 구현체로 사용자가 선언 방식 혹은 path 탐색 방식으로 도메인을 등록하는 경우, 이에 대한 정보가 작성된 파일이다.
     */
    public static void run(Class<?> primarySource, AegisConfig config) {
        /**
         * TODO:
         * 1. 관리 대상 도메인 등록
         * 2. 관리 대상에 대응하는 Validator 구현 여부 검사
         * 3. 관리 대상 도메인의 필드에 대응하는 validate 메소드 구현 여부 검사
         */
    }
}
