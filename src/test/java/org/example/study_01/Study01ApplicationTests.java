package org.example.study_01;

import org.example.study_01.mysite.sbb.Question;
import org.example.study_01.mysite.sbb.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class Study01ApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreatedAt(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장
    }

}
