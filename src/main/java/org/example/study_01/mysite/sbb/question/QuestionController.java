package org.example.study_01.mysite.sbb.question;

import java.security.Principal;
import java.util.List;

import org.example.study_01.mysite.sbb.answer.AnswerService;
import org.example.study_01.mysite.sbb.user.SiteUser;
import org.example.study_01.mysite.sbb.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.server.ResponseStatusException;



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
    private final UserService userService;

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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        // TODO 질문을 저장한다.
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
