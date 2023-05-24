package hello2.hellospring2.repository;

import hello2.hellospring2.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//Jdbc->Jdbc Template->JPA(SQL 쿼리도 JPA가 자동으로 처리해줌.) 순서로 간단해짐
public class JdbcTemplateMemberRepository implements MemberRepository{  //Jdbc Template 버젼


    private final JdbcTemplate jdbcTemplate;

    @Autowired  //이것과같이 생성자가 1개일경우 Autowired 생략가능
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result=jdbcTemplate.query("select * from member where id = ?", memberRowMapper(),id); //id 파라미터가 제대로 세팅되지 않은 상태이므로 끝에 id를 적어줘야함.
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result=jdbcTemplate.query("select * from member where name = ?", memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member ", memberRowMapper());
         }

    private RowMapper<Member> memberRowMapper(){
        return new RowMapper<Member>(){  //Alt+Enter 눌러서 람다식으로 교체가능
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException{
                Member member=new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        };
    }
}
