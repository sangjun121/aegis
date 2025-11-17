# Aegis — 도메인 검증 관리 프레임워크

Aegis는 도메인 객체가 생성되는 시점부터 유효함을 보장하고, 모듈 간 상태 일관성을 유지하기 위한 도메인 검증 관리 프레임워크이다.

## 개요

Aegis는 도메인 계층의 안정성 확보를 목적으로, 각 도메인 객체의 내부 불변식과 외부 도메인 상태 의존성을 함께 검증하도록 어플리케이션 실행 시점에 검사한다.
쉽게 말해, 도메인 객체가 항상 만족해야 하는 진리와 다른 도메인의 현재 상태를 기반으로한 검증이 구현되었는지 부트스트랩시 검사하고, 강제하는 도구이다.

즉, **도메인 내부 검증** 과 **외부 의존 검증** 두 가지를 모두 지원하는 경량 프레임워크이다.

# 주요 기능

## 1. 도메인 스캔 및 등록
- 애플리케이션 실행 시 검증 대상 도메인 클래스를 자동 탐색한다.
  - 사용자는 어플리케이션 최상위 classpath를 AegisConfig로 반드시 초기화한다. 
  - 초기화 하지 않는 경우, 메인 클래스를 기준으로 스캔한다. 이는 defaultBasePackage로 제공한다.
  - 어노테이션 선언: @AegisDomain이 붙은 클래스를 자동 스캔한다.
- 스캔된 도메인은 프레임워크의 관리 대상에 등록된다.

## 2. Validator 구현 강제
- 관리 대상에 등록된 도메인은 반드시 DomainValidator<T> 인터페이스를 구현해야 한다.
- 도메인과 관련된 모든 검증 로직은 Validator 내부에 캡슐화된다.
- Aegis는 애플리케이션 시작 시 Validator 구현 여부를 자동 검사한다.
- Validator 미구현 시 부트스트랩 단계에서 예외가 발생한다.
- 예시: 아래 코드 참고

```java
@AegisDomain
public class Member {
  private final String name;
}
```
```java
public class MemberValidator implements DomainValidator<Member> {
  public void validate() { ... }
}
```

## 3. 의존 도메인 선언 검사
- 외부 도메인의 상태에 의존하는 검증이 필요한 경우, 해당 도메인에서 의존 관계를 명시적으로 선언 가능하다. 
  - 여기서 의존관계는 실의존관계가 아닌 검증 의존 관계를 의미
- @ValidationDependsOn 선언으로 의존 도메인을 표시할 수 있다.

```java
@AegisDomain
@ValidationDependsOn(Member.class)
public class Lecture {
  private final Long id;
  private final String name;
  private final String professorName;
  private final List<String> students;
  private final List<String> blackListStudents;
  
  ...

  /**
   * 객체 생성시 외부 객체에 상태에 의존하는 경우
   *
   * @param student
   * @return
   */
  public Lecture register(Member student) {
    new LectureDependencyValidator().validate(this, student);

    students.add(student.getName());
    return this;
  }
```

```java
public class LectureDependencyValidator implements DependencyValidator<Lecture, Member> {

    @Override
    public void validate(Lecture lecture, Member member) {
        if(lecture.getBlackListStudents().contains(member.getName())) {
            // 예외발생
        }
    }
}
```

- 부트스트랩 단계에서 Aegis는 다음을 검사한다.
  - 선언된 선행 도메인(Member)이 존재하는지 스캔한다.
  - 검증 로직(DependencyValidator<A,B>)이 구현되어 있는지 스캔한다.
  - 미구현시 시 부트스트랩 단계에서 예외가 발생한다.

## 4. 런타임 도중 도메인 상태 검증
- 런타임 중 도메인 객체 생성 시, 도메인의 불변식이 올바른지 내부 독립 검증이 이뤄진다.

## 5. 부가기능 1 — 부트스트랩 성공 결과 로그 제공
- 부트스트랩이 성공한 경우, 모든 도메인과 이에 대응하는 검증 클래스의 쌍을 제공한다.
- 의존 관계가 있는 경우, 의존 관계 화살표로 Aegis에 인식된 모든 검증 의존성을 제공한다.

## 6. 부가기능 2 - 예외 발생 및 로그 처리
- Aegis 부트스트랩의 모든 발생 예외는 AegisException을 발생한다.
- 예외가 발생하는 경우, AegisExceptionEntryPoint에서 모든 예외를 중앙 처리한다.
- 발생된 예외는 콘솔 로그 기반으로 예외가 제공되며, SL4J를 기반으로 제공한다.(이에 대한 정상 출력이 필요한 경우, 사용자 어플리케이션에서 구현체 라이브러리를 의존해야 한다.)

# 라이프 사이클에서의 프레임워크 역할

| 단계        | 시점      | 주요 동작 내용                                         |
|-----------|---------|--------------------------------------------------|
| 부트스트랩 단계  | 앱 시작 시  | 도메인 스캔, Validator 등록 여부 검사, 의존 관계 누락 탐지 |
| 이후 런타임 단계 | 객체 생성 시 | 내부 규칙 검증                             |
