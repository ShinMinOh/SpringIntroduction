package hello2.hellospring2.repository;

import hello2.hellospring2.domain.Member;

import java.util.List;
import java.util.Optional;

//회원 Repository Interface. 회원 객체를 저장하는 저장소.
public interface MemberRepository {
    //name은 고객이 회원가입할 때 적는 값. 하지만 id는 시스템에 저장을 할 때 등록되는 시스템이 정해주는 값

    Member save(Member member); //save 회원 저장 기능.

    Optional<Member> findById(Long id);
    //id를 이용해서 회원을 찾는 기능. 시스템이

    Optional<Member> findByName(String name);
    //namedmf 이용해서 회원을 찾는 기능. Optional은 찾는 회원이 없을경우 Null반환해줌.

    List<Member> findAll();
    //저장된 모든 회원 list를 반환해주는 기능.



}
