package org.example.study_01.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject); // 4.findBySubject 메서드
    Question findBySubjectAndContent(String subject, String content); // 5.findBySubjectAndContent 메서드
    List<Question> findBySubjectLike(String subject); // 6.findBySubjectLike 메서드
    Page<Question> findAll(Pageable pageable); // 3-2.페이징 기능 추가
    Page<Question> findAll(Specification<Question> spec, Pageable pageable); // 3-13. 검색 기능 추가
    /*
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
     3-13. 쿼리 애노테이션으로 검색 기능 구현*/
}