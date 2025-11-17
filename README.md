# Aegis — 도메인 검증 관리 프레임워크

Aegis는 도메인 객체가 생성되는 시점부터 유효함을 보장하고, 모듈 간 상태 일관성을 유지하기 위한 도메인 검증 관리 프레임워크이다.
Aegis는 도메인 계층의 안정성 확보를 목적으로, 각 도메인 객체의 내부 불변식과 외부 도메인 상태 의존성을 함께 검증하도록 어플리케이션 실행 시점에 검사한다.

사용자 어플리케이션에서 도메인 계층의 검증 로직이 누락되지 않았는지, 부트스트랩 단계에서 사전 검사를 지원한다. 
즉, 실행 시점에 아래 조건을 만족하지 않은 경우, 런타임 예외를 발생하며 종료한다.
이를 통해, 개발자의 도메인 검증 로직 작성의 실수나 누락사항을 미리 감지할 수 있다.

Aegis 검사 기능은 2가지로 구분하여 지원하며, 모두 부트스트랩 단계에서 수행된다.
독립 도메인 자체의 불변식을 준수하도록 강제한다. @AegisDomain이 선언된 객체는 Aegis 관리 대상 도메인에 포함되며, 이에 대응하는 DomainValidator<?>를 반드시 구현하여야 한다.
또한 외부 의존 검증 시에 해당 로직이 작성되도록 강제한다. 런타임 도중 @ValidationDependsOn으로 해당 도메인(source)이 외부 도메인(dependency) 상태에 의존한 검증이 필요한 경우, 해당 어노테이션을 통해 Aegis에서 관리되도록 설정할 수 있다.
@ValidationDependsOn에 선언된 source, dependency 도메인을 타입 인자로 받는 DependencyValidator<S,D>를 반드시 구현하여야 한다.
위의 사항을 준수하지 않는 경우 Aegis는 부트스트랩 단계에서 예외를 발생하며 종료하고, 누락 사항을 로그로 제공한다.

즉, **도메인 내부 검증** 과 **외부 의존 검증** 두 가지를 모두 지원하는 경량 프레임워크이다.

## 시작하기
**1. 프로젝트 클론하기**
- 먼저 해당 레포지토리를 로컬에 clone한다.

**2. 프로젝트 root 디렉토리 경로에서 아래 명령어를 실행하여, 로컬 Maven 저장소에 jar파일을 저장한다.**
- maven 로컬 저장
```ini
./gradlew :aegis-core:publishToMavenLocal
```

- 저장 확인하기 (터미널에 아래 명렁어 실행)
```ini
ls ~/.m2/repository/me/sangjun/aegis/aegis-core/0.0.1-SNAPSHOT
```


**3. 사용자 어플리케이션에 Sl4j 구현체 라이브러리 의존성 추가하기**
- Aegis에서 제공하는 모든 로그는 Sl4j를 기반으로 제공한다.
- 따라서, Sl4j 구현체를 사용자 어플리케이션에서 의존성을 추가하여야 한다.
- Aegis-Sample에서는 logback-classic을 사용한다.

**4. AegisConfig 작성하기** 
- Aegis가 탐색을 수행할 가장 classPath를 지정한다. 
- 단일 모듈인 경우, AegisConfig.DefaultBasePackage를 사용하면, 어플리케이션 메인 클래스를 기반으로 하위 탐색을 수행한다.

**5. AegisBootStrap.run()을 실행한다.
- 사용자 어플리케이션 실행과 동시에 Aegis의 부트스트랩을 수행한다.

**예시**
```java
public class SampleApplication {
    public static void main(String[] args) {
        AegisConfig config = new SampleAegisConfig();
        AegisBootStrap.run(SampleApplication.class, config);
    }
}
```

## 기능 바로가기 
자세한 기능 명세는 [docs/FEATURE.md](/docs/FEATURE.md)에 작성되어 있다.


## 예시 프로젝트
해당 [aegis-sample](/aegis-sample)에서 경량화된 사용자 어플리케이션을 통해 Aegis의 동작 원리를 이해할 수 있다.
