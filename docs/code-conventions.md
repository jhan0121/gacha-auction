## 1. 코드 포맷

- **우아한테크코스 코드 스타일**을 기준으로 포맷을 통일합니다.
- [우테코 코드 스타일 설정 가이드](https://github.com/woowacourse/woowacourse-docs/blob/main/style/java-style-guide.md)
- https://github.com/woowacourse/woowacourse-docs/blob/main/styleguide/java/intellij-java-wooteco-style.xml

---

## 2. 코딩 규칙

### import

- 와일드카드 import 사용을 금지합니다.
    - 예시

        ```java
        import java.util.* // 와일드카드 import 사용 금지
        ```

### 파라미터

- Column이 120을 넘을 경우, 파라미터를 개행 후 하나씩 나열합니다.

    ```java
    public void exampleMethod(
        String parameterA,
        String parameterB,
        String parameterC
    ) {
        // ...
    }
    ```

### `var` 키워드

- `var` 사용을 지양합니다.
- 사용이 필요한 경우, 팀원과 논의 후 결정합니다.

### 접근 제어자 순서

- 클래스 멤버는 아래 순서로 정렬합니다.
    1. `public`
    2. `private`

### Service layer 구조

- 초기에는 단일 클래스로 기능을 구현합니다.
- 코드 자체를 설명하는 주석은 지양합니다.
- 비즈니스 로직이나 히스토리 등 **배경 설명**이 필요한 경우에만 작성합니다.

---

## 3. DTO (Data Transfer Object)

### 네이밍

- 클래스명에서 `Dto` 접미사를 작성하지 않습니다.

### 계층별 DTO 분리

- **Controller**와 **Service** 계층에서 사용하는 DTO를 명확히 분리합니다.
    - **Controller (Web → Controller)**
        - `...Request`
        - `...Response`
    - **Service (Controller → Service & Service → Service)**
        - **Controller → Service**
            - `...Input`
            - `...Output`
        - **Service → Service**
            - `...Input`
            - `...Output`

---

## 4. 아키텍처

### Service 참조

- Service 계층 간 참조 아키텍처를 적용합니다.

### 패키지 구조

- **도메인별**로 패키지를 먼저 구분한 후, 그 안에서 **계층별**로 구분합니다.
- **도메인별 패키지 구조**
    - `controller`: 웹 계층 (Controller, DTO)
    - `service`: 비즈니스 로직 계층 (Service, DTO)
    - `repository`: 외부 시스템 연동 및 데이터 영속성 계층 (Repository 구현체)
    - `domain`: 핵심 도메인 모델 (Entity, Repository 인터페이스)
- **도메인별 패키지 구조 예시 (`example` 도메인)**

    ```
    +---example
    	+---service
    	|   +---dto
    	|   |   \---input
    	|   |           ExampleInput.java
    	|   |   \---output
    	|   |           ExampleOutput.java
    	|   |
      |       ExampleService.java
    	|
    	+---domain
    	|       Example.java
    	|       ExampleRepository.java
    	|
    	+---repository
    	|       ExampleRepository.java
    	|
    	\---controller
    	|				ExampleController.java
    	|   +---dto
    	        \---request
    	                ExampleCreateRequest.java
    	        \---response
    	                ExampleCreateResponse.java
    ```

---

## 5. 테스트 (Test)

### 테스트명

- `@DisplayName`을 사용하여 테스트 목적을 한글로 명확히 작성합니다.
- 영어 메서드명은 간단하게 작성합니다.

### given-when-then

- 기본적으로 given-when-then 주석을 작성하는 것을 원칙으로 합니다.
- 아래 두 가지 주석 스타일을 적용하여 일관되게 사용합니다.
    - **기본 작성법**

        ```java
        // given
        
        // when
        
        // then
        ```

    - **when, then 중 대상 코드가 없을 시 붙여서 작성 case**

        ```java
        // given
        
        // when 
        // then
        ```

### 트랜잭션

- 프로덕션 Service 클래스 내 각 메서드에`@Transactional`을 선언하는 것을 원칙으로 합니다.
- 조회만 있는 메서드의 경우에는 `readOnly=true` 옵션을 설정합니다.

### 테스트 범위

- Controller → 통합 테스트
- Service → Mock 기반 단일 테스트

---

## 6. 어노테이션 순서

### JPA

- 아래 순서로 annotation을 정렬합니다.
    1. `@Entity`
    2. `@Column`

### Lombok

- 아래 순서로 annotation을 정렬합니다.
    1. `@NoArgsConstructor(access = ...)`
    2. `@Getter`
    3. `@ToString`
    4. `@EqualsAndHashCode`

### Test

- 아래 순서로 annotation을 정렬합니다.
    1. `@Test` , `@ParameterizedTest`  등 Test annotation
    2. `@CsvSource` , `@EnumSource` , `@MethodSource`
    3. `@DisplayName`

---

## 7. 메서드 순서

- 클래스 내 CRUD 메서드는 아래 순서로 정렬합니다.
    1. **R**ead
    2. **C**reate/Update
    3. **D**elete

---

## 8. DB 테이블 & JPA Entity

- 테이블 id 이름은 `id`로 통일합니다.
- Entity는 `BaseEntity`를 상속합니다.
- Entity는 `equals`, `hashcode` 를 별도로 구현하지 않습니다.
    - `BaseEntity` 로의 상속을 통해 `equals`, `hashcode`를 적용합니다.
- entity 생성은 `new` 를 사용하지 않고 정적 팩토리 메서드(`withId`, `withoutId` 등)를 구성하여 생성해야 합니다.
- 생성 시점에 validate를 수행합니다.
