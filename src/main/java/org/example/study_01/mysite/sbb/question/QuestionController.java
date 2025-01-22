package org.example.study_01.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {
//2-7에서는  레포지터리에서 그대로 가져옴
//    private final QuestionRepository questionRepository;
//
//    @GetMapping("/question/list")
//    public String list(Model model) {
//        List<Question> questionList = this.questionRepository.findAll();
//        model.addAttribute("questionList", questionList);
//        return "question_list";
//    }

    private final QuestionService questionService;

    // 질문 목록 조회
    @GetMapping("/question/list")
    public String questionList(Model model) {
        // 서비스에서 만든 메서드를 사용
        List<Question> questionList = this.questionService.getList();

        model.addAttribute("questionList", questionList);
        return "question_list";
    }
}