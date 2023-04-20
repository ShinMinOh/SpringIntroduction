package hello2.hellospring2.service;

import hello2.hellospring2.domain.Member;
import hello2.hellospring2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//비즈니스 로직-->같은 이름이 있는 회원은 안된다라고 가정.

//@Service
public class MemberService { // ctrl + shift + t 하면 test 생성

    /* private final MemberRepository memberRepository=new MemoryMemberRepository();
    기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성하게 했습니다.
    회원 서비스를 만들려면 일단 MemberRepository가 있어야함.
    여기서 만들어지는 memberRepository와 Test에서의 memberRespitory는 서로 다른 repository임.다른 instance. new로 새로 만들었기 때문에
    MemoryMemberRepository의 저장소인 store가 static객체라 충돌이 없었지만 static이 아닐경우 완전 다른 DB가 되면서문제가 생길 수 있음.

    */
    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        //외부에서 memberReporitory에 직접 넣어주는걸로 바꾸기. DB의 통일성을 위해서
    }

    /*회원 가입*/
    public Long join(Member member){
        //같은 이름이 있는 중복 회원X
        /*Optional<Member> result = memberRepository.findByName(member.getName()); //Ctrl+Alt+V
        result.ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        optional로 반환되므로 result안의 member(m)값이 있으면
        ifPresent: result값이 null이 아니라 어떤 값이 있으면 괄호 안의 동작을 실행.
        Optional로 result를 감쌌기 때문에 쓸 수 있는 메소드임.
        그래서 요즘은 null이 있을 가능성이 있는 변수의 경우 Optional로 감싸고 그에 따른 다양한 메소드를 쓸 수 있음.

         */
        validateDuplicateMember(member); //1. 중복 회원 검증.
        /*위의 코드를 간단히 쓰면 아래 validateDuplicateMember 처럼 쓸 수 있다.
          findByName하고 로직이 쭉 길게 나올경우 위의 한줄 같이 메소드로 따로 빼는것이 가독성이 좋음.
          메소드는 아래에 나옴.
          Refactoring 단축키: Ctrl+Alt+M 두번눌러서 이름변경
        * */
        memberRepository.save(member); //2. 중복 회원 검증이 되면 해당 멤버 저장
        return member.getId(); //회원가입을 하면 아이디만 반환.
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) //Ctrl+Alt+V
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //한사람의 ID조회 해서 찾기
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }


}
