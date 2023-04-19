package hello2.hellospring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")  //  URL localhost:8080/hello 의 주소로 들어간다는 뜻.
    public String hello(Model model){
        /*
        * localhost:8080/hello라는 URL로 매칭이 되면서 HelloController안에 있는 hello라는 메소드가 실행됨.
        * Spring model이라는 것을 만들어서 넣어줌. 이 모델에다가 addAtribute를 하는데 key 값은 data이고 이곳의 attribute를 hello!!로 바꿈
        * */

        model.addAttribute("data","spring!!");

        return "hello";
        /*resources안의 templates의 html파일 이름과 동일. 이 화면을 실행시키라는 뜻
        * 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버가('viewResolver)가 화면을 찾아서 처리한다.
        * 'resources:templates/ +{ViewName}+ .html 이 열리게 됨.  ex) hello.html'*/
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return  "hello-template";
    }

    @GetMapping("hello-string") // API방식
    @ResponseBody //http에서 body부에 return값 즉 "hello + name 값을 직접 넣어주겠다는 뜻"
    //template engine과의 차이점은 이 문자 그대로 올라감. View 이런것이 존재하지 않음.
    public String helloString(@RequestParam("name") String name){
        return "hello "+name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello(); //ctro+shift+Enter 따옴표찍고 마무리 해주는 단축키
        hello.setName(name);
        return hello; //처음으로 문자가 아닌 객체를 넘김.
    }


    static class Hello{ //static class로 만들면 HelloController 클래스 안에서 또 클래스를 쓸 수 있음.
        private String name;  //private형식으로 지정해놔서 외부에서 쓸때 아래와 같은 get/set 메소드를 통해서 사용해야함.

        // Alt+Insert 단축키: Getter & Setter 생성
        public String getName() {  //꺼낼때
            return name;
        }

        public void setName(String name) {  //넣을때
            this.name = name;
        }
    }
}

