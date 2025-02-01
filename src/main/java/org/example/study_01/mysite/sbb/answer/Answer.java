package org.example.study_01.mysite.sbb.answer;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.study_01.mysite.sbb.question.Question;
import org.example.study_01.mysite.sbb.user.SiteUser;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
//    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    private SiteUser author;
}
