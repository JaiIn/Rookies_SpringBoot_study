package org.example.study_01.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.data.domain.Page;

import org.example.study_01.mysite.sbb.answer.AnswerForm;


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
    public String questionList(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        // 서비스에서 만든 메서드를 사용
        //List<Question> questionList = this.questionService.getList();
        //model.addAttribute("questionList", questionList);

        // 3-2. 페이징 처리
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);

        return "question_list";
    }

    // 2-10 질문 상세 페이지
    @GetMapping(value = "/detail/{id}")
    public String questionDetail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        // 특정 id에 해당하는 질문 조회
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 2-16 질문등록버튼 URL매핑
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        // TODO 질문을 저장한다.
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }

}