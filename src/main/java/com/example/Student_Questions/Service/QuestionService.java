package com.example.Student_Questions.Service;

import com.example.Student_Questions.Entity.Questions;
import com.example.Student_Questions.Repository.QuestionsDTO;
import com.example.Student_Questions.Request.QuestionsRequest;
import com.example.Student_Questions.Request.Response;
import com.example.Student_Questions.Wrapper.QuestionsReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionsDTO questionsDTO;
    public ResponseEntity<String> createQuestions(List<QuestionsRequest> questionsRequest) {
        List<Questions> questions =
                questionsRequest.stream().map(this::requestToEntity).toList();
        questionsDTO.saveAll(questions);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    private Questions requestToEntity(QuestionsRequest req) {
        Questions questions = new Questions();
        questions.setQuestion(req.getQuestions());
        questions.setOption1(req.getOption1());
        questions.setOption2(req.getOption2());
        questions.setOption3(req.getOption3());
        questions.setOption4(req.getOption4());
        questions.setRightAnswer(req.getRightAnswer());
        questions.setStudentClass(req.getStudentClass());
        questions.setCategoryId(req.getCategory());
        return questions;
    }

    public ResponseEntity<Map<String, List<String>>> getDataOnCategory(String category) {
        List<Questions> question = questionsDTO.findByCategory(category);
        Map<String, List<String>> maps = question.stream()
                .collect(Collectors.groupingBy(
                        Questions::getCategoryId,
                        Collectors.mapping(Questions::getQuestion, Collectors.toList())
                ));
        return new ResponseEntity<>(maps,HttpStatus.OK);
    }

    public ResponseEntity<List<Questions>> getAllQuestions() {
        return new ResponseEntity<>(questionsDTO.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> generateId(String category, Integer num) {
        return new ResponseEntity<>(questionsDTO.findByCategoryAndIds(category, num),HttpStatus.OK);
    }

    public ResponseEntity<List<com.example.Student_Questions.Wrapper.QuestionsReturn>> generateQuestions(List<Integer> request) {
        List<Questions> questions= request.stream().map(id-> questionsDTO.findById(id.longValue()).get()).toList();
        List<QuestionsReturn> response = questions.stream().map(this::entityToResponse).collect(Collectors.toList());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private QuestionsReturn entityToResponse(Questions question) {
        QuestionsReturn questionReturn = new QuestionsReturn();
        questionReturn.setQuestions(question.getQuestion());
        questionReturn.setOption1(question.getOption1());
        questionReturn.setOption2(question.getOption2());
        questionReturn.setOption3(question.getOption3());
        questionReturn.setOption4(question.getOption4());
        return questionReturn;
    }

    public ResponseEntity<Integer> getScore(List<Response> response) {
        int i  = 0;
        for (Response res : response) {
            Questions questions = questionsDTO.findById(res.getId()).get();
            if(questions.getRightAnswer().equals(res.getAnswer())) {
                i += 1;
            }
        }
        return  new ResponseEntity<>(i,HttpStatus.OK);
    }
}
