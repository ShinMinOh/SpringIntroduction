package hello2.hellospring2.repository;

import hello2.hellospring2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//interface가 interface 받을때는 extends.Interface는 다중 상속이 가능.Member의 Id의 타입이 Long.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
//JpaRepository를 받고 있으면 자동으로 구현체 생성. 자동으로 spring bean에 등록해줌. 그걸 가져다 쓰면됨-->springconfig로 이동

    //findBy+Name ==> JPQL select m from Member m where m.name = ?란 의미.findBy 뒤의 변수명만으로도 개발이 가능.이것이 dataJpa의 장점.
    @Override
    Optional<Member> findByName(String name);
}
