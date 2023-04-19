package hello2.hellospring2.service;

import hello2.hellospring2.domain.Member;
import hello2.hellospring2.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// Assertions을 static으로 선언하여 assertThat() 메서드를 바로 사용한다.
import static org.junit.jupiter.api.Assertions.*;


class MemberServiceTest {
    /*Service의 MemberService선택후 해당 클래스 네임 블록지정후 Ctrl+Shift+T 누르면 testclass자동으로 만들어짐.
    * Test 네임은 한글로 바꿔도 상관없음.*/

    //MemberService memberService=new MemberService(memberRepository);
    //MemoryMemberRepository memberRepository=new MemoryMemberRepository(); //afterEach 즉 clear하기 위해서
    //MemberService를 생성할때 MemoryMemberRepository에 직접 넣어주는 걸로 바꿈.

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){ //테스트 실행되기 전에 독립적으로 먼저 실행되는 함수.
        /*
        @BeforeEach: 각 테스트 실행 전에 호출됩니다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어줍니다.
        MemoryMemberRepository를 생성하여 MemberService에 주입해줍니다.(DI)

         * 1.각 테스트를 실행하기 전에 MemoryMemberRepository를 만들고
         * 2.걔를 memberRepository에 넣고
         * 3.MemberService의 public MemberService함수에 위 2번의 memberRepository를 넘겨줌.
         * 4.그러면 같은 MemoryMemberRespotory를 MemberService 클래스와 MemberServiceTest클래스가 공유하게 됨.

         위가 원래 코드였다. 하지만 이렇게 되면 test 코드에서 생성된 memberRepository가 MemberService class에서 생성된 memberRepository와 다른 객체가 된다. 이렇게 되는 경우 같은 역할을 한다고 하여 생성해놓은 객체가 한쪽에는 저장이 되고,
         한쪽에는 저장이 안되는 등 문제가 발생할 수 있으므로, 하나로 공유하게끔 해주어야 한다.
        그래서 해당 코드를 위에 작성한 부분과 같이 수정하여 같은 메모리 memberRepository가 사용될 수 있도록 한다.
        위와 같이 외부에서 메모리를 넣어주는 것을 DI(Dependency Injection), 의존성 주입이라고 한다.


         */
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){//테스트가 실행하고 난후 모든 변수 초기화.
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() { //주로 아래 3가지 형식으로 나눠짐.
        //given: 이러한 상황이 주어져서~. 이 데이터를 기반으로 함.
        Member member=new Member();
        member.setName("hello");
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
        /*
        try{
            memberService.join(member2);
            fail();
        }   catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
        }*/

        //  assertThrows(IllegalStateException.class, ()->memberService.join(member2);  //try-catch 코드를 assertThrows( )로 줄여서 사용할 수 있다.
        IllegalStateException e=assertThrows(IllegalStateException.class, ()->memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");




    }


    @Test
    void findMembers() {

    }

    @Test
    void findOne() {

    }
}