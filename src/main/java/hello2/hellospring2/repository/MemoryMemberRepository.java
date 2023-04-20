package hello2.hellospring2.repository;

import hello2.hellospring2.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {
    //Repository와 관련해서 발생하는 기능들 구현.
    private static Map<Long, Member> store = new HashMap<>();
    //save를 할때 저장을 해야됨.메모리니까. key값은 id (Long타입) 값은 member
    private static long sequence=0L;//0,1,2이렇게 key값을 생성해주는 변수

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //Id를 시스템이 셋팅.
        store.put(member.getId(), member); //store에 Map형태로 저장.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /*return store.get(id);
        찾는 id가 없으면 Null이 반환됨. 요즘에는 null이 반환될 가능성이 있으면 Optional로 감싼다.
        Null을 Optional로 감쌀 경우 뒤의 client단에서 무언가를 할 수 있음.
         */

        return Optional.ofNullable((store.get(id)));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        /*member에서 member.getName과 파라미터로 넘어온 name과 같은지 확인.
          같은 경우에만 필터링. 그 중에서 하나 찾아지면면 바로 반환(findAny() 기능).
        */
    }


    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //store는 Map인데 반환은 List로 되어 있음.실무에서는 List를 많이씀(Loop 돌리기 편하기 때문에).
        //store.values() : store에 있는 values가 총 Member들임.
    }

    public void clearStore(){
        store.clear();
    }
}
