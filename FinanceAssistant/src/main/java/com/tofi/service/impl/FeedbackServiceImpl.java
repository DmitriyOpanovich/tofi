package com.tofi.service.impl;

import com.tofi.model.Feedback;
import com.tofi.repository.FeedbackRepository;
import com.tofi.repository.enums.FeedbackTypeRepository;
import com.tofi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    FeedbackTypeRepository feedbackTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> getAllUnansweredFeedbacks() {
        return feedbackRepository.findAllByIsAnsweredFalseOrderByDateDesc();
    }

    @Override
    @Transactional
    public void answerOnFeedbacks(List<Long> feedbackIds) {
        List<Feedback> persistFeedbacks = feedbackRepository.findAll(feedbackIds);
        persistFeedbacks.forEach(feedback -> {
            feedback.setAnswered(true);
            feedback.setDate(new Timestamp(System.currentTimeMillis()));
        });
        feedbackRepository.save(persistFeedbacks);
        feedbackRepository.flush();
    }

    @Override
    @Transactional
    public void postFeedback(Feedback feedback) {
        feedback.setAnswered(false);
        mergeFeedbacks(feedback, feedback);
        feedbackRepository.saveAndFlush(feedback);
    }

    private void mergeFeedbacks(Feedback source, Feedback target) {
        target.setType(
                source.getType() == null ?
                        null :
                        feedbackTypeRepository
                                .findOneByName(source.getType().getName())
                                .orElse(source.getType()));
    }
}
