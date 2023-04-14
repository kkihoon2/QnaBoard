package com.codestates.question.repository;

import com.codestates.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Page<Question> findAllByOrderByCreatedAtAsc(Pageable pageable);

    Page<Question> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
