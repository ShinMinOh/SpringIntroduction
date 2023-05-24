package hello2.hellospring2.service;

import hello2.hellospring2.aop.TimeTraceAop;
import hello2.hellospring2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    /*
    * Spring이 뜰때 @Configuration을 읽고 @Bean을 보고 이걸 spring bean에 등록하란 뜻이네? 라고 생각하며
    * MemberService 로직을 호출하여 Spring Bean애 등록함.
    * */

    /*기존에 이 주석안코드로 Entity Manager를 받음.
   private DataSource dataSource;
    //Spring Boot가 application.properties를 보고 db와 연결할수 있는 dataSource를 자체적으로 만듬.
    // 그리고 아래와 DI(의존관계) 설정을 통해서 dataSource를 주입해줌.

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
*/

//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource, EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository); //위의 memberRepository와 의존관계 세팅.위이 코드로 Injection 받아서 등록
        //return new MemberService(memberRepository());
        //생성자이므로 ()안에 파라미터 넘겨줘야함. MemberService 클래스를 보면 안에 memberRepository를 받는다고 되어있음.
        //그러므로 아래 Spring Bean 의 memberRepository랑 엮어줌.
        /*SpringConfig가 뜰때, 위의 memberService와, 아래 memberRepository를 Spring Bean에 등록하고
          springbean에 등록되어있는 memberRepository를 MemberService에 넣어줌.
        *
        */
    }

//    @Bean // Component scan을 안쓰는 경우 써주기
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }

 //   @Bean
 //   public MemberRepository memberRepository(){
        //return new MemoryMemberRepository(); //MemberRepository 즉 interface는 new가 안됨.

       // return new JdbcMemberRepository(dataSource);
        /*Jdbc db를 만들었으므로 JdbcMemoryRepository와 연결. Interface(MemberReposiroty)를 실제 메모리와 연결된 JdbcMemoryRepository로 확장.
        Spring이 제공하는 이 SpringConfiguration만 손댐.*/

       // return new JdbcTemplateMemberRepository(dataSource);

        //return new JpaMemberRepository<M, Number>(em);//Entity Manager필요.
 //   }
}
