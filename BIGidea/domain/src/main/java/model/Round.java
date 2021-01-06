package model;

import enums.Category;
import enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import services.QuestionService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Round {

    @Getter
    private List<Question> questions;


    @Getter
    private Settings settings;

    @Autowired
    private final QuestionService qService;

    public Round(Settings settings) {
        this.settings = settings;
        this.qService = new QuestionService();
        try {
            this.questions = generateQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Question> generateQuestions() throws IOException {
        return qService.generateQuestions(this.settings);
    }

}
