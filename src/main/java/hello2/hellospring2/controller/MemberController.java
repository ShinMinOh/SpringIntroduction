package hello2.hellospring2.controller;

import hello2.hellospring2.domain.Member;
import hello2.hellospring2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //SpringConatiner가 뜰때 생성되면서 생성자가 실행.
public class MemberController {
    //private final MemberService=new MemberService  이렇게 new로 정의하게 되면 MemberController말고 다른 여러 컨트롤러들이 MemberService를 가져다 쓸수 있게됨.
      private final MemberService memberService;

      @Autowired
      //생성자에 Autowired가 되어 있으면, 아래 memberService를 스프링이 Container에 있는 memberService에 연결시켜줌.
      public MemberController(MemberService memberService) {
          this.memberService = memberService;
    }
    //MemberService 클래스는 안에 별 기능이 없어서 여러개 생성하지 않고 하나 생성해 놓고 같이쓰면 됨.
    //그래서 spring container에 아에 등록을 해놓고 쓰면 됨. 여기애 등록하면 딱 하나만 등록됨.

    @GetMapping("/members/new")
    public String createForm(){
          return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member= new Member();
        member.setName(form.getName());  //form에서 getName으로 이름을 꺼냄.

        //System.out.println("member= "+ member.getName());
        memberService.join(member);     //회원가입할 member 이름 넘김.

        return "redirect:/";     //회원가입이 끝나면 home화면으로 보냄.
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);  //members안에는 list로 모든 회원을 조회해서 model에 담아서 보냄.
        return "/members/memberList";
      }
}
