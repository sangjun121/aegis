# Aegis — 도메인 검증 관리 프레임워크

Aegis는 도메인 객체가 생성되는 시점부터 유효함을 보장하고, 모듈 간 상태 일관성을 유지하기 위한 도메인 검증 관리 프레임워크입니다.

## 개요

Aegis는 도메인 계층의 안정성 확보를 목적으로, 각 도메인 객체의 내부 불변식과 외부 도메인 상태 의존성을 함께 검증하도록 강제합니다.
쉽게 말해, 도메인 객체가 항상 만족해야 하는 진리와 다른 도메인의 현재 상태도 함께 만족하는지 검증 구현을 강제하는 프레임워크입니다.

즉, **도메인 내부 검증** 과 **외부 의존 검증** 두 가지를 모두 지원하는 경량 프레임워크입니다. 

# 주요 기능

## 1. 도메인 스캔 및 등록
- 애플리케이션 실행 시 검증 대상 도메인 클래스를 자동 탐색한다.
- 다음 세 가지 탐색 방식을 지원한다.
  - 경로 기반 탐색(별도 Config 선언): 지정된 패키지 경로를 기준으로 자동 스캔
  - 명시적 선언(별도 Config 선언): `domains: [Account, Member, Order]`
  - 어노테이션 선언: @DomainValidated 가 붙은 클래스를 자동 스캔
- 스캔된 도메인은 프레임워크의 관리 대상에 등록된다.
- 각 도메인은 다음 조건을 만족해야 한다.
  - @DomainValidated 애노테이션 선언 혹은 Config 등록
  - 대응되는 Validator 클래스 존재
  - 검증이 의존되는 상대 도메인 선언
- 누락 시 부트스트랩 단계에서 예외가 발생한다. (`MissingValidatorException`)

## 2. Validator 구현 강제
- 모든 도메인은 반드시 DomainValidator<T> 인터페이스를 구현해야 한다.
- 도메인과 관련된 모든 검증 로직은 Validator 내부에 캡슐화된다.
- 각 도메인의 모든 필드에 대해 검증 메서드를 작성해야 하며, 검증이 필요 없는 필드는 해당 메소드에 `@NoValidationRequired`로 명시할 수 있다.
- Aegis는 애플리케이션 시작 시 Validator 구현 여부를 자동 검사한다.

```java
@DomainValidated
public class Member {
  private final String name;
}
```
```java
public class MemberValidator implements DomainValidator<Member> {
  public void validate() { ... }
}
```
- Validator 미구현 시 부트스트랩 단계에서 예외가 발생한다.

## 3. 자동 검증 실행 보장
- 관리 대상 도메인으로 등록된 클래스는 생성자 종료 시점에 Validator의 최상위 메소드 `validate()`가 자동 실행된다.
- 개발자가 검증 메서드를 직접 호출할 필요가 없다.
- 즉, 객체 생성 시점의 유효 상태 보장의 불변식이 준수된다.

## 4. 의존 도메인 선언 검사
- 외부 도메인의 상태에 의존하는 검증이 필요한 경우, 해당 도메인에서 검증 의존 관계를 명시적으로 선언해야 한다. 
- 이는 **실제 코드 의존**이 아닌 **상태 검증 의존**을 의미한다.

```java 
// 예시: Evaluation 객체 생성시, Role의 권한이 Owner 이상인지 상태 검증이 필요하다.
        
@Required(domain = "Role")
public class Evaluation { ... }
```

- 부트스트랩 단계에서 Aegis는 다음을 검사한다.
  - 선언된 선행 도메인(Role)이 존재하는지
  - 해당 의존 검증을 수행하는 Validator 구현체가 있는지
  - 누락 시 `MissingDependencyValidatorException` 이 발생한다.

## 5. 런타임 도중 도메인 상태 검증
- 런타임 중 도메인 객체 생성 시, 도메인의 불변식이 올바른지 내부 독립 검증이 이뤄진다.
- 또한 검증 의존 도메인이 있는 경우, 실제 상태가 유효한지 자동으로 검증한다.

## 6. 부가기능 1 — 공통 검증 어노테이션
- 일반적인 검증(Null, Blank, Range 등)은 도메인 필드가 아닌 Validator 클래스의 메서드에 선언할 수 있다.
- 기본 제공 어노테이션(@NotBlank, @Range, @Email) 외에도 사용자 정의 커스텀 어노테이션을 추가할 수 있다.
```java
@NotBlank
@Email
public void validateEmail(String email) { ... }

```

## 7. 부가기능 2 — 검증 실패 처리 (예외 및 로그)
**예외 처리**

검증 실패는 두 가지 예외로 구분되어 관리된다.
1. 도메인 내부 검증 실패 → `DomainValidationException`
2. 의존 관계 검증 실패 → `DependentDomainValidationException`

**로그 처리**
- 모든 검증 실패는 공통 포맷으로 기록된다.
  - 예시
```ini
  [ValidationError] domain=Member validator=MemberValidator code=EMAIL_INVALID message=“비활성화된 이메일입니다.” path=Member.email
```
- 중앙 예외 핸들러(ValidationExceptionHandler)가 모든 예외를 종합 담당한다.

## 8. 부가기능 3 — 검증 의존 관계 시각화
- 도메인 간 검증 관계를 그래프 형태로 시각화할 수 있다.
  - 노드(Node): 도메인 클래스
  - 엣지(Edge): 검증 의존 관계 
- 이를 통해, 순환 의존 및 누락된 검증 관계를 탐지 가능하다.

# 라이프 사이클에서의 프레임워크 역할

| 단계      | 시점      | 주요 동작 내용                                          |
|---------|---------|---------------------------------------------------|
| 부트스트랩 단계 | 앱 시작 시  | 도메인 스캔, Validator 등록 여부 검사, 의존 관계 누락 탐지 |
| 런타임 단계  | 객체 생성 시 | 내부 규칙 검증 + 외부 의존 상태 검증 자동 실행                              |
| 개발/빌드 단계 | 개발 시점   | 검증 관계 시각화 및 순환 구조 탐지                               |

# 기대효과
1.	도메인 검증 로직이 Validator 내부에 완전히 캡슐화되어 관리된다.
2.	도메인 간 상태 의존 관계를 명시적으로 표현하고, 자동으로 검증이 가능하다.
3.	시각화 기능을 통해 구조적 의존 관계를 쉽게 파악할 수 있다.
4.	부트 단계에서 검증 누락을 사전에 탐지할 수 있습니다.
