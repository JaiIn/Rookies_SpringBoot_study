package org.example.study_01.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.example.study_01.mysite.sbb.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor    // 레포지터리 객체를 생성자 방식으로 주입
@Service                    // 서비스 애너테이션
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 목록을 조회-> 리턴하는 메서드
    public List<Question> getList(){
        // 저번에 QuestionController의 내용을 그대로 옮김
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);

        // id값이 존재하는지 검사
        if (question.isPresent()) {
            return question.get();

            // 없는 경우 예외 클래스 실행
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
}