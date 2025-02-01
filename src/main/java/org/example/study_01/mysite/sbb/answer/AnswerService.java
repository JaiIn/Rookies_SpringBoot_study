package org.example.study_01.mysite.sbb.answer;

import lombok.RequiredArgsConstructor;
import org.example.study_01.mysite.sbb.DataNotFoundException;
import org.example.study_01.mysite.sbb.question.Question;
import org.example.study_01.mysite.sbb.user.SiteUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setAuthor(author);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }

//    public Answer getAnswer(Integer id) {
//        Optional<Answer> answer = this.answerRepository.findById(id);
//        if (answer.isPresent()) {
//            return answer.get();
//        } else {
//            throw new DataNotFoundException("answer not found");
//        }
//    }
}