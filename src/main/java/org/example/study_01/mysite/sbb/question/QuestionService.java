package org.example.study_01.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.example.study_01.mysite.sbb.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import java.time.LocalDateTime;

@RequiredArgsConstructor    // 레포지터리 객체를 생성자 방식으로 주입
@Service                    // 서비스 애너테이션
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 목록을 조회-> 리턴하는 메서드
    public List<Question> getList(){
        return this.questionRepository.findAll();
    }

    // 질문 조회 (상세페이지)
    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        // id값이 존재하는지 검사
        if (question.isPresent()) {
            return question.get();

            // 없는 경우 예외 클래스 실행
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }

    //3-2 페이징 기능 처리
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
}
