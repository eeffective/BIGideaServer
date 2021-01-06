import model.Answer;
import model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTests {
    Question q;
    Answer a1;
    Answer a2;
    List<Answer> answers;

    @BeforeEach
    public void setup() {
        answers = new ArrayList<>();
        a1 = new Answer("2", true);
        a2 = new Answer("11", false);

        answers.add(a1);
        answers.add(a2);

        q = new Question("What's 1 + 1?", answers);
    }

    @AfterEach
    public void cleanUp() {
     // Nothing to clean up
    }

    @Test
    public void Correct_Answer_Is_Equal(){
        assertEquals(a1, q.getCorrectAnswer());
    }

    @Test
    public void Correct_Answer_Is_Not_Equal(){
        assertNotEquals(a2, q.getCorrectAnswer());
    }

    @Test
    public void Amount_Of_Answers_Is_Correct(){
        int expectedAmount = 2;

        assertEquals(expectedAmount, q.getAnswers().size());
    }

    @Test
    public void Content_Is_Equal(){
        String expectedContent = "What's 1 + 1?";

        assertEquals(expectedContent, q.getContent());
    }

}
