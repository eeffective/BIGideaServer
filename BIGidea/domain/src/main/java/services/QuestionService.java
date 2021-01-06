package services;

import enums.Category;
import enums.Difficulty;
import model.Answer;
import model.Question;
import model.Settings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class QuestionService {
    public List<Question> generateQuestions(Settings settings) throws IOException {
        String newUrl = "https://opentdb.com/api.php?amount=" + settings.getQuestions() + "&category=" + settings.getCategory().getValue() + "&difficulty=" + settings.getDifficulty().getValue() + "&type=multiple";
        URL url = new URL(newUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader bReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bReader.readLine()) != null) {
            response.append(inputLine);
        }

        bReader.close();

        List<Question> questions = new ArrayList<>();

        try {
            JSONObject questionsJson = new JSONObject(response.toString());
            JSONArray questionsArray = questionsJson.getJSONArray("results");
            for (int i = 0; i < questionsArray.length(); i++) {
                List<Answer> answers = new ArrayList<>();
                JSONObject questionObj = questionsArray.getJSONObject(i);
                JSONArray wrongsArray = questionObj.getJSONArray("incorrect_answers");
                for (int j = 0; j < wrongsArray.length(); j++) {
                    answers.add(new Answer(wrongsArray.get(j).toString(), false));
                }
                answers.add(new Answer(questionObj.getString("correct_answer"), true));
                Collections.shuffle(answers);
                Question q = new Question(questionObj.getString("question"), answers);
                questions.add(q);
            }
        } catch (JSONException e) {
            System.out.println(e);
        }

        return questions;
    }


}
