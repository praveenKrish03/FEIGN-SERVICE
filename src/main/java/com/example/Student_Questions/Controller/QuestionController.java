package com.example.Student_Questions.Controller;



import com.example.Student_Questions.Entity.Questions;
import com.example.Student_Questions.Request.QuestionsRequest;
import com.example.Student_Questions.Request.Response;
import com.example.Student_Questions.Service.QuestionService;
import com.example.Student_Questions.Wrapper.QuestionsReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/createQuestion")
    public ResponseEntity<String> createQuestions(List<QuestionsRequest> questionsRequest) {
        return questionService.createQuestions(questionsRequest);
    }

    @GetMapping("/getDataCategory")
    public ResponseEntity<Map<String,List<String>>> getDataonCategory
            (@RequestParam String category) {
        return questionService.getDataOnCategory(category);
    }

    @GetMapping("/allquestions")
    public ResponseEntity<List<Questions>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/generateIds")
    public ResponseEntity<List<Integer>> generateId(String category, Integer num) {
        return questionService.generateId(category, num);
    }

    @GetMapping("/generateQuestions")
    public ResponseEntity<List<QuestionsReturn>> generateQuestions(List<Integer> request) {
        return questionService.generateQuestions(request);
    }
    @GetMapping("getScore")
    public ResponseEntity<Integer> getScore(List<Response> response) {
        return questionService.getScore(response);
    }
}
