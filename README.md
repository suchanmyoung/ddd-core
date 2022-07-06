### DDD 시작하기 중요내용 요약

#### 1장 도메인 모델 시작하기
* 도메인이란 소프트웨어로 해결하고자 하는 문제 영역을 말한다.
  * 도메인은 여러 하위 도메인으로 구성 된다.
* 모델을 구성하는 핵심 구성요소, 규칙, 기능을 찾는 것이 도메인 모델링의 기본작업
* 모델은 엔티티와 밸류로 구분할 수 있다.
  * 엔티티
    * 식별자를 가진다
    * 엔티티를 삭제할 때까지 식별자는 유지
    * 엔티티 식별자의 실제 데이터는 String인 경우가 많은데, 이때도 밸류타입을 이용해서 해당 필드의 의미를 드러낼 수 있다
  * 밸류
    * 배송 서비스에서 받는 사람의 정보(여러 인스턴스변수)는 "받는 사람" 이라는 개념 하나를 표현하고 있음
    * 배송 서비스에서 주소의 정보(여러 인스턴스변수)는 "주소" 라는 개념 하나를 표현하고 있음
    * -> 따로 클래스 만들어서 관리
    * 밸류 타입의 장점은 개념을 확실하게 표현하여 코드의 의미를 더 잘 이해할 수 있게 하고, 밸류 타입 만을 위한 기능을 추가할 수 있는 것
    * 밸류 객체의 데이터를 변경할 때는 기존 데이터를 변경하기보다 변경한 데이터를 갖는 새로운 밸류 객체를 생성
      * 이러한 경우처럼 데이터 변경 기능을 제공하지 않는 타입을 불변 타입이라고 함.
      * 가장 중요한 이유는 안전한 코드를 작성할 수 있음
* 도메인 모델에 set 메서드를 사용하지 말라
  * set 메서드는 도메인의 핵심 개념이나 의도를 코드에서 사라지게 한다.
  * changeShippingInfo / setShippingInfo 무엇이 의도가 드러나는가?
  * set을 쓸 때는 private 으로 클래스 내부에서 데이터를 변경할 목적으로 써라

#### 2장 아키텍처 개요
* 네 개의 영역
  * 표현
    * 컨트롤러(객체 변환, JSON 변환)
  * 응용
    * 서비스
  * 도메인
    * 핵심 로직은 도메인 모델에서 구현
  * 인프라스트럭처
    * 구현 기술을 다룸
    * RDB 연동, 메시지 큐, 몽고 DB나 레디스
    * 논리적 개념보다는 실제 구현
* 상위 계층에서 하위 계층으로는 의존하지만, 하위 계층은 상위 계층으로 의존하지 않는다.
  * 표현 > 응용 > 도메인 > 인프라스트럭처
  * 구현저장하고 조회하는 기능을 정의
    * 리포지터리 인터페이스는 도메인 모델 영역에 속하고, 실제 구현 클래스는 인프라스트럭처 영역에 속한다
    * 기본적으로 repositry를 사용하는 주체가 응용 서비스이기 때문에 애그리거트를 저상위 개념으로 표현
    * 애그리거트는 군집에 속한 객체를 관리하는 루트 엔티티를 갖는데, 애그리거트를 사용하는 코드는 루트가 제공하는 기능을 제공하고 루트를 통해서 간접적으로 애그리거트 내의 다른 엔티티나 밸류에 접근
  * Repository
    * 도메인 모델의 영속성을 처리
    * repository는 애그리거트 단위로 도메인 객체를 거트 루트 식별자로 조회하는 메서드를 가져야 한다장하는 메소드와 애그리의 편리함을 위해 계층 구조를 유연하게 적용하기 도 한다(응용 > 인프라스트럭처)
* 도메인 영역의 주요 구성요소
  * Entity
    * 고유의 식별자를 갖는 객체로 자신의 라이프 사이클을 가짐. 도메인 모델의 데이터를 포함하며 해당 데이터와 관련된 기능을 함께 제공(주문, 회원)
    * 도메인 모델의 엔티티와 RDB의 엔티티는 다르다. 특히 도메인 모델의 엔티티는 데이터와 함께 도메인 기능(배송지 주소 변경 등)을 함께 제공
  * Value
    * 고유의 식별자를 가지지 않는 객체로 개념적으로 하나의 값을 표현할 때 사용한다. 엔티티의 속성이면서 다른 밸류 타입의 속성으로도 사용.(주소, 금액)
  * Aggregate
    * 연관된 엔티티와 밸류 객체를 개념적으로 하나로 묶은 것(주문과 관련된 엔티티들을 주문 애그리거트로 묶을 수 있음)
    * 상위 수준에서 모델을 관리하지 않고 개별 요소에만 초점을 맞추다 보면 큰 수준에서 모델을 이해하지 못하기 때문에 군집을 통해
  * Domain service 
    * 특정 엔티티에 속하지 않은 도메인 로직을 제공(할인 금액 계산 등 다양한 조건을 이용해서 구현하는 것처럼 여러 엔티티와 밸류가 필요할 때 사용)
* 요청 처리 흐름
  * Controller > Service > Domain > Repository
* 인프라스트럭처는 구현의 편리함을 위해 적절히 의존할 수 있따. @Transactional 이나 #ntity 등을 서비스나 도메인 모델에 사용하는 것이 편리하다. 구현의 편리함은 설계의 완벽함보다 중요할 수도 있다!
* **패키지 구성 규칙에 정답이 존재하는 것은 아니다!**
  * 예시로 ui > application > domain < infrastructure 로 사용할 수 있다.
  * 도메인이 크면 각 도메인으로 directory(모듈)를 나누고 order > ui > application > domain < infra 형태로 구성할 수 있다.
  * 도메인 모듈은 애그리거트를 기준으로 다시 패키지를 구성할 수 있다.
  * catalog
    * ui
    * applicaton
    * domain
      * product
      * category
    * infra
  * 애그리거트, 도메인 모델, 리포지터리는 같은 패키지에 의치 시킨다.
  * 응용 서비스도 도메인 별로 패키지를 구분할 수 있다.
  * 정해진 규칙은 없지만 한 패키지에 너무 많은 타입이 몰려서 코드를 찾을 때 불편한 정도만 아니면 되고, 필자의 의견으로는 10~15개 미만으로 타입 개수를 유지하려고 노력함

#### 3장 애그리거트
* 애그리거트는 관련된 모델을 하나로 모았기 때문에 한 애그리거트에 속한 객체는 유사하거나 동일한 라이프 사이클을 가진다
* 애그리거트는 have a 관계가 아니다(상품과 리뷰는 함께 생성되거나 변경되지 않고 변경 주체도 다르다)
* 애그리거트에 속한 모든 객체가 일관된 상태를 유지하려면 애그리거트 전체를 관리할 주체가 필요한데, 이 책임을 지는 것이 루트 엔티티
* 일관성이 깨지지 않는 것이 중요하다 !
* 애그리거트 외부에서 애그리거트에 속한 객체를 직접 변경하면 안 된다. 이는 애그리거트가 강제하는 규칙을 적용할 수 없어 모델의 일관성을 깬다!
* 루트를 통해서만 도메인 로직을 구현하게 하려면,
  * 단순히 필드를 변경하는 set 메서드를 public 선언하지 않는다
    * set 메서드를 넣지 않으면 자연스럽게 cancel이나 change처럼 의미가 잘 드러나는 이름을 사용하는 빈도가 높아진다
  * 밸류 타입은 불변으로 구현한다
  * 불변으로 구현할 수 없다면 protected 선언하여 애그리거트 외부 상태 변경을 방지할 수 있다
* 트랜잭션의 범위
  * 트랜잭션의 범위는 작을 수록 좋다
  * 한 트랜잭션이 한 개 테이블을 수정하는 것과 세 개의 테이블을 수정하는 것을 비교하면 성능 차이가 발생한다
    * 한 개 테이블을 수정하면 트랜잭션 충돌을 막기 위해 잠그는 대상이 한 개 테이블의 한 행으로 한정되지만, 세 개의 테이블을 수정하면 잠금 대상이 많아지고 그만큼 동시에 처리할 수 있는 트랜잭션 개수가 줄어들어 처리량을 떨어뜨린다
    * 동일하게 한 트랜잭션에서는 한 개의 애그리거트만 수정해야 한다
    * 만약 수정해야 한다면, 애그리거트에서 수정하지 말고 응용 서비스 계층에서 두 애그리거트를 수정하도록 구현한다
* 애그리거트는 개념상 완전한 한 개의 도메인 모델을 표현하므로 객체의 영속성을 처리하는 리포지토리는 애그리거트 단위로 존재
* 한 객체가 다른 객체를 참조하는 것처럼 애그리거트도 다른 애그리거트를 참조한다. 애그리거트의 관리 주체는 루트이므로 애그리거트 > 애그리거트 참조는 다른 루트를 참조한다는 것과 같다
  * 여러 문제가 발생(의존 결합도 높임, 수정의 유혹, 성능 고민, 확장성)하는데 이럴 때는 RDB에서 외래키를 통해 참조하듯이 다른 애그리거트를 참조할 때는 루트 ID를 이용한다.
  * id 참조 방식에서 N+1 문제가 발생할 수도 있는데, 이때는 JPQL 등을 이용해서 그냥 JOIN 걸자
  * 애그리거트마다 다른 저장소를 사용하면 한 번의 쿼리로 관련 애그리거트를 조회할 수 없는데, 이때는 조회 성능을 높이기 위해 캐시를 적용하거나 조회 전용 저장소를 따로 구성
  * **애그리거트를 팩토리로 이용할 수도 있다!!**
