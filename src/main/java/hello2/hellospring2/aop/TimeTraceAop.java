package hello2.hellospring2.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //이걸 적어야 AOP사용 가능.
@Component
public class TimeTraceAop {

    @Around("execution(* hello2.hellospring2..*(..))")
    /*hello2.hellospring2의 하위에 다 적용해 라는뜻.
     hello2.hellospring2.service..*(..)이라 쓰면 service의 하위 항목에 있는 메소드들의 걸린 시간만 측정됨.*/

    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start=System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());

        try{
            Object result = joinPoint.proceed();//다음메소드로 진행.
            return result;
        } finally {
            long finish=System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("END: " + joinPoint.toString() +" "+timeMs+"ms");
        }
    }
}
