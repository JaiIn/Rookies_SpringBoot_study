package org.example.study_01.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/question") // 프리픽스
@RequiredArgsConstructor
@Controller
public class QuestionController {
//
//    private final QuestionRepository questionRepository;
//
//    @GetMapping("/question/list")
//    public String list(Model model) {
//        List<Question> questionList = this.questionRepository.findAll();
//        model.addAttribute("questionList", questionList);
//        return "question_list";
//    }
    // 2-7에서는  레포지터리에서 그대로 가져옴

    private final QuestionService questionService;

    // 2-9 질문 목록 반환
    @GetMapping("/list") // question 빼고 뒷부분만
    public String questionList(Model model) {
        // 서비스에서 만든 메서드를 사용
        List<Question> questionList = this.questionService.getList();

        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    // 2-10 질문 상세 페이지
    @GetMapping(value = "/detail/{id}")
    public String questionDetail(Model model, @PathVariable("id") Integer id) {
        // 특정 id에 해당하는 질문 조회
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
