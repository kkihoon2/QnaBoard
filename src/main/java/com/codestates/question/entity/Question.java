package com.codestates.question.entity;

import com.codestates.audit.Auditable;
import com.codestates.member.entity.Member;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

//Todo:
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDateTime createdAt =LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 25, nullable = false)
    private Question.QuestionStatus questionStatus = QuestionStatus.QUESTION_REGISTRATION;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 25, nullable = false)
    private Question.QuestionDisclosure questionDisclosure;
    public enum QuestionStatus{
        QUESTION_REGISTRATION("질문등록 상태"),
        QUESTION_ANSWERED("답변완료 상태"),
        QUESTION_DELETED("질문삭제 상태");
        @Getter
        private String status;
        QuestionStatus(String status){
            this.status=status;
        }
    }
    public enum QuestionDisclosure{
        QUESTION_PUBLIC("공개글"),
        QUESTION_SECRET("비밀글");
        @Getter
        private String disclosure;

        QuestionDisclosure(String disclosure){
            this.disclosure=disclosure;
        }
    }

}
