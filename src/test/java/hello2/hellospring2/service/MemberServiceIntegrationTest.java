package hello2.hellospring2.service;

import hello2.hellospring2.domain.Member;
import hello2.hellospring2.repository.MemberRepository;
import hello2.hellospring2.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
/*이 annotation을 Test에 달면 test를 실행할 때,트랜잭션을 먼저 실행하고,db에 데이터를 insert query로 다 넣은 다음에,
    test가 끝나면 Roll Back을 해줌. 그래서 test때 넣었던 데이터가 반영이 안되고 다 지워짐.
*/

class MemberServiceIntegrationTest {

    /*Service의 MemberService선택후 해당 클래스 네임 블록지정후 Ctrl+Shift+T 누르면 testclass자동으로 만들어짐.
    * Test 네임은 한글로 바꿔도 상관없음.*/

    //MemberService memberService=new MemberService(memberRepository);
    //MemoryMemberRepository memberRepository=new MemoryMemberRepository(); //afterEach 즉 clear하기 위해서
    //MemberService를 생성할때 MemoryMemberRepository에 직접 넣어주는 걸로 바꿈.

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;



    @Test
    //@Commit //db에 반영하려면 @Commit
    void 회원가입() { //주로 아래 3가지 형식으로 나눠짐.
        //given: 이러한 상황이 주어져서~. 이 데이터를 기반으로 함.
        Member member=new Member();
        member.setName("spring100");
        //name이 spring일 경우 afterEach가 없으면 순서가 보장되지 않고 test가 실행되서 중복_회원_예외에서 spring 값이 누적되서 Exception 발생 .


        //when: 이것을 실행했을때. 무엇을 검증할 것인가?
        Long saveId=memberService.join(member);  //반환 받는 변수 만들기 ctrl + alt + v. memberService의 join기능을 검증하겠다. 저장한 Id가 return되게 만들기로 계획했음.


        //then: 결과가 이것이 나와야함. 검증부 결과.
        Member findMember  = memberService.findOne(saveId).get(); //단순하게 값을 받기 위해서 Optional이 아닌 그냥 Member 변수로 받음.
        assertThat(member.getName()).isEqualTo(findMember.getName());
        //static import는 alt + enter. 저장하는 값이 repository에 있는지 없는지 확인 .

    }
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1=new Member();
        member1.setName("spring");

        Member member2=new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e=assertThrows(IllegalStateException.class, ()->memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");




    }



}