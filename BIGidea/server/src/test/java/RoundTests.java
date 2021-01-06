import enums.Category;
import enums.Difficulty;
import model.Round;
import model.Settings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoundTests {

    Round round;
    Settings settings;

    @BeforeEach
    public void setup(){
        settings = new Settings(Category.GENERAL, 5, 10, Difficulty.EASY);
        round = new Round(settings);
    }

    @AfterEach
    public void cleanUp(){

    }

    @Test
    public void Questions_Get_Generated(){
        int expectedAmount = 5;

        assertEquals(expectedAmount, round.getQuestions().size());
    }


    @Test
    public void Settings_Are_Equal(){

        assertEquals(settings, round.getSettings());
    }

}
