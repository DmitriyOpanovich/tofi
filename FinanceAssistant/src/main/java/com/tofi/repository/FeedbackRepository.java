package com.tofi.repository;

import com.tofi.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ulian_000 on 26.12.2016.
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByIsAnsweredFalseOrderByDateDesc();
}
