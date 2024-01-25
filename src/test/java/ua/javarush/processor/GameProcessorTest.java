package ua.javarush.processor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameProcessorTest {
    private static final GameProcessor gameProcessor = new GameProcessor();

    @BeforeAll
    public static void init() {
        gameProcessor.createGame("/testGame.json");
    }

    @Test
    public void createGameShouldThrowExceptionWhenPathIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> gameProcessor.createGame(""));
    }

    @Test
    public void generateQuestionTextWhenQuestionIdIsNotNull() {
        String expected = "Do you want to accept UFO challenge?";
        String actual = gameProcessor.generateQuestionText(0);
        assertEquals(expected, actual);
    }

    @Test
    public void generateFirstAnswerTextWhenQuestionIdIsNotNull() {
        String expected = "Accept";
        String actual = gameProcessor.generateFirstAnswerText(0);
        assertEquals(expected, actual);
    }

    @Test
    public void generateSecondAnswerTextWhenQuestionIdIsNotNull() {
        String expected = "Not accept";
        String actual = gameProcessor.generateSecondAnswerText(0);
        assertEquals(expected, actual);
    }

    @Test
    public void generateAnswerMessageShouldReturnNotEmptyStringWhenQuestionIdIsNotNull() {
        String expected = "You accepted challenge";
        String actual = gameProcessor.generateAnswerMessage(0, "0");
        assertEquals(expected, actual);
    }

    @Test
    public void generateAnswerMessageShouldReturnEmptyStringWhenQuestionIdIsNull() {
        String expected = "";
        String actual = gameProcessor.generateAnswerMessage(0, null);
        assertEquals(expected, actual);
    }

    @Test
    public void checkNextQuestionIdShouldReturnTrueWhenAnswerIndexIsNull() {
        assertTrue(gameProcessor.checkNextQuestionId(0, null));
    }

    @Test
    public void checkNextQuestionIdShouldReturnTrueWhenAnswerIndexIsNotFail() {
        assertTrue(gameProcessor.checkNextQuestionId(0, "0"));
    }

    @Test
    public void checkNextQuestionIdShouldReturnFalseWhenAnswerIndexIsFail() {
        assertFalse(gameProcessor.checkNextQuestionId(0, "1"));
    }

    @Test
    public void generateNumberOfGamesWhenNumberIsNotNull() {
        assertEquals(2, gameProcessor.generateNumberOfGames("1"));
    }

    @Test
    public void generateNumberOfGamesShouldReturnOneWhenNumberIsNull() {
        assertEquals(1, gameProcessor.generateNumberOfGames(null));
    }

    @Test
    public void generateEndGameMessageForVictory() {
        String expected = "You won!";
        String actual = gameProcessor.generateEndGameMessage(3, "0");
        assertEquals(expected, actual);
    }

    @Test
    public void generateEndGameMessageForFailureWhenQuestionIdIsNotMax() {
        String expected = "Failure";
        String actual = gameProcessor.generateEndGameMessage(2, "0");
        assertEquals(expected, actual);
    }

    @Test
    public void generateEndGameMessageForFailureWhenAnswerIndexIsNegative() {
        String expected = "Failure";
        String actual = gameProcessor.generateEndGameMessage(3, "1");
        assertEquals(expected, actual);
    }

    @Test
    public void generateResultImgPathForVictory() {
        String expected = "https://miro.medium.com/v2/resize:fit:1400/1*tGMPy19-xJa2p73q3qfEpA.jpeg";
        String actual = gameProcessor.generateResultImgPath(3, "0");
        assertEquals(expected, actual);
    }

    @Test
    public void generateResultImgPathForFailureWhenQuestionIdIsNotMax() {
        String expected = "https://as2.ftcdn.net/v2/jpg/02/45/98/61/1000_F_245986170_7OVFjzWCLqH6a8RBEfqHVKQcD8RXINpF.jpg";
        String actual = gameProcessor.generateResultImgPath(2, "0");
        assertEquals(expected, actual);
    }

    @Test
    public void generateResultImgPathForFailureWhenAnswerIndexIsNegative() {
        String expected = "https://as2.ftcdn.net/v2/jpg/02/45/98/61/1000_F_245986170_7OVFjzWCLqH6a8RBEfqHVKQcD8RXINpF.jpg";
        String actual = gameProcessor.generateResultImgPath(3, "1");
        assertEquals(expected, actual);
    }

    @Test
    public void generateProgressForId0() {
        assertEquals("0", gameProcessor.generateProgress(0));
    }

    @Test
    public void generateProgressForId1() {
        assertEquals("33", gameProcessor.generateProgress(1));
    }

    @Test
    public void generateProgressForId2() {
        assertEquals("66", gameProcessor.generateProgress(2));
    }
}
