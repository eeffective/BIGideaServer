import model.Answer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnswerTests {
    Answer correct;
    Answer incorrect;

    @BeforeEach
    public void setup(){
         correct = new Answer("Good", true);
         incorrect = new Answer("Bad", false);
    }

    @AfterEach
    public void cleanUp(){
        // Nothing to clean
    }

    @Test
    public void Answer_Is_Correct(){
        assertTrue(correct.getCorrect());
    }

    @Test
    public void Answer_Is_Incorrect(){
        assertFalse(incorrect.getCorrect());
    }

    @Test
    public void Answer_Content_Is_Equal(){
        String expectedAnswer = "Good";
        assertEquals(expectedAnswer, correct.getContent());
    }



}
