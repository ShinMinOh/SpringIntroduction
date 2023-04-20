package hello2.hellospring2.service;

import hello2.hellospring2.repository.MemberRepository;
import hello2.hellospring2.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    /*
    * Spring이 뜰때 @Configuration을 읽고 @Bean을 보고 이걸 spring bean에 등록하란 뜻이네? 라고 생각하며
    * MemberService 로직을 호출하여 Spring Bean애 등록함.
    * */
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
        //생성자이므로 ()안에 파라미터 넘겨줘야함. MemberService 클래스를 보면 안에 memberRepository를 받는다고 되어있음.
        //그러므로 아래 Spring Bean 의 memberRepository랑 엮어줌.
        /*SpringConfig가 뜰때, 위의 memberService와, 아래 memberRepository를 Spring Bean에 등록하고
          springbean에 등록되어있는 memberRepository를 MemberService에 넣어줌.
        *
        */

    }


    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository(); //MemberRepository 즉 interface는 new가 안됨.
    }
}
