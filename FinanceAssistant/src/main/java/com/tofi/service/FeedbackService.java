package com.tofi.service;

import com.tofi.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ulian_000 on 26.12.2016.
 */
@Service
public interface FeedbackService {
    List<Feedback> getAllUnansweredFeedbacks();
    void answerOnFeedbacks(List<Long> feedbackIds);
    void postFeedback(Feedback feedback);
}
