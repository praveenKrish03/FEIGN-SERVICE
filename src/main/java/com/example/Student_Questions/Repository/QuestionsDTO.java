package com.example.Student_Questions.Repository;

import com.example.Student_Questions.Entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsDTO extends JpaRepository<Questions,Long> {

    List<Questions> findByCategory(String category);

    @Query(value = "Select t.id from Question_Details t where t.Questions:category ORDER BY RANDOM() Limit:num", nativeQuery = true)
    List<Integer> findByCategoryAndIds(String category, Integer num);
}
