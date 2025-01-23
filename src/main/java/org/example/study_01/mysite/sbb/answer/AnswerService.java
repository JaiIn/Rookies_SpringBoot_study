package org.example.study_01.mysite.sbb.answer;

import lombok.RequiredArgsConstructor;
import org.example.study_01.mysite.sbb.question.Question;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    // 특정 질문에 맞는 답변을 저장하는 메서드
    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}

