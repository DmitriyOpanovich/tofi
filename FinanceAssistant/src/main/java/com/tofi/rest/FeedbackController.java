package com.tofi.rest;

import com.tofi.dto.FeedbackDTO;
import com.tofi.model.Feedback;
import com.tofi.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ulian_000 on 26.12.2016.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
    public List<FeedbackDTO> getFeedbacks(){
        return feedbackService.getAllUnansweredFeedbacks().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public void postFeedback(@RequestBody FeedbackDTO newFeedback){
        feedbackService.postFeedback(modelMapper.map(newFeedback, Feedback.class));
    }

    @RequestMapping(value = "/feedbacks/answer", method = RequestMethod.POST)
    public void postFeedback(@RequestBody List<Long> answeredFeedbackIds){
        feedbackService.answerOnFeedbacks(answeredFeedbackIds);
    }
}
