package hello2.hellospring2.repository;

import hello2.hellospring2.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository<M, L extends Number> implements MemberRepository{

    private final EntityManager em; //Jpa는 EntityManager로 모든 동작을 함.
    //pk기반이 아닌 메소드들은 jpql을 작성해 줘야함.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    //jpa를 쓰려면 항상 transaction이 있어야함. 서비스 계층(MemberService 클래스)에 @Transactional을 붙이기.
    // 데이터를 저장하거나 변경할때는 @Transactional이 있어야함
    public Member save(Member member) {
        em.persist(member);  //jpa가 insert 쿼리 만들어서 db에 집어넣고 다해줌.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        //Jpql이라는 쿼리언어: "select m from Member m". 객체전체(id이렇게가 아닌 m으로)를 대상으로 쿼리 날림.
        return result;
    }
}
