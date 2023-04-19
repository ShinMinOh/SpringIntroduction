package hello2.hellospring2.repository;

import hello2.hellospring2.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository=new MemoryMemberRepository();

    /*어떤 메소드가 끝날때마다 여기로 돌아와서 아래 동작을 실행.
     * save메소드가 끝나고 afterEach(), findByName메소드가 끝나고 afterEach()...
     * Test를 실행할때 순서롤 보장하지 않고 무작위로 메소드를 골라서 test를 진행한다.
     * Test는 서로 순서,의존관계 상관없이 설계되어야함.
     * 따라서 각 메소드간의 충돌을 피하기 위해서 메소드를 실행하고 종료할때 깨끗이 변수들을 모두 초기화 해줘야 한다.
     * 그러기 위해서 @AfterEach를 통해서 그것을 진행해주는 것이다.
     * clearSotre()는 MemoryMemberRepository클래스에서 만든 store를 clear해주는 기능이다.*/
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }


    @Test
    public void save(){
        Member member=new Member();
        member.setName("spring");

        repository.save(member);
        //member가 save되는 동시에 해당 고객의 이름과 아이디가 세팅됨.

        Member result =repository.findById(member.getId()).get();
        //제대로 들어갔나 확인.findById의 반환 타입이 Optional임.
        // Optional에서 값을 꺼낼때는 get()으로 꺼낼 수 있음. 하지만 좋은 방법은 아님.

        /*Assertions.assertEquals(member, result);

        Assersions (org.junit.jupiter.api)
        저장하는 member가 result안에 있는지 비교. 두 값이 같을경우 실행시켰을때 아무것도 안뜨는 반면
        다르면 빨간색이 뜸
        member: Expected, result= actual
        */

        //Assertions.assertThat(member).isEqualTo(result);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        //이름이 같을경우 shift+F6눌러서 이름을 바꾸면 밑에 다른 이름도 다 같이 뱌ㅏ뀜
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }


}
