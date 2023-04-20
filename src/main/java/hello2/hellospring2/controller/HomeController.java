package hello2.hellospring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")  //localhost:8080딱 들어오면 호출됨. 이 부분이 없을 경우 기본 HelloController에 있는 것이 home화면에 뜸.
    public String home(){
        return "home";   //home.html이 호출됨.
    }
}
