package hello2.hellospring2.domain;

import javax.persistence.*;

@Entity //Member는 jpa가 관리하는 Entity라는 뜻.
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //DB에 값을 넣으면 DB가 ID를 자동으로 생성해줌:IDENTITY 전략
    private Long id;

    //db에 column 네임이 username일 경우. db에 있는 컬럼네임이랑 username이랑 메핑이됨.
    @Column(name="name") //초록색 글씨는 db의 column명이다. db에는 name이라 해놓고 (name="username")이라 해놔서 오류났었음.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
