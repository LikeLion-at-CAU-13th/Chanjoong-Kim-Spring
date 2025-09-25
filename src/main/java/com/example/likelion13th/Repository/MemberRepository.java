package com.example.likelion13th.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.likelion13th.domain.Member;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long>
{
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    Page<Member> findByAgeGreaterThanEqualOrderByNameAsc(Integer age, Pageable pageable);
    Page<Member> findByNameStartingWithOrderByNameAsc(String prefix, Pageable pageable);
    // 이름 중복 검사 쿼리
    boolean existsByName(String name);
}
//Spring Data JPA가 자동으로 인식해서 내부적으로 JPQL 쿼리를 생성해준다!