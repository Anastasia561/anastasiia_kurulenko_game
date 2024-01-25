package ua.javarush.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import ua.javarush.game.Game;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class GameProcessor {
    private static final int POSITIVE_ANSWER_INDEX = 0;
    private static final int NEGATIVE_ANSWER_INDEX = 1;
    private static final int FAiL_ANSWER_INDEX = -1;
    private static final int MAX_NUMBER_OF_QUESTIONS = 3;
    private static final String VICTORY_MESSAGE = "You won!";
    private static final String FAILURE_MESSAGE = "Failure";
    private static final String VICTORY_IMG = "https://miro.medium.com/v2/resize:fit:1400/1*tGMPy19-xJa2p73q3qfEpA.jpeg";
    private static final String FAILURE_IMG = "https://as2.ftcdn.net/v2/jpg/02/45/98/61/1000_F_245986170_7OVFjzWCLqH6a8RBEfqHVKQcD8RXINpF.jpg";
    private Game game;

    public GameProcessor() {
    }

    public void createGame(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = GameProcessor.class.getResourceAsStream(path);
        try {
            game = objectMapper.readValue(is, Game.class);
            log.info("Game object created");
        } catch (IOException e) {
            log.error("Fail to parse json game object");
            throw new IllegalArgumentException("Fail to parse json game object");
        }
    }

    public String generateQuestionText(int questionId) {
        return game.getQuestions().get(questionId).getText();
    }

    public String generateFirstAnswerText(int questionId) {
        return game.getQuestions().get(questionId).getAnswers().get(POSITIVE_ANSWER_INDEX).getText();
    }

    public String generateSecondAnswerText(int questionId) {
        return game.getQuestions().get(questionId).getAnswers().get(NEGATIVE_ANSWER_INDEX).getText();
    }

    public String generateAnswerMessage(int questionId, String answerIndex) {
        if (answerIndex != null) {
            int answerId = Integer.parseInt(answerIndex);
            return game.getQuestions().get(questionId).getAnswers().get(answerId).getMessage();
        } else {
            return "";
        }
    }

    public boolean checkNextQuestionId(int questionId, String answerIndex) {
        if (answerIndex != null &&
                game.getQuestions().get(questionId).getAnswers().get(Integer.parseInt(answerIndex)).getNextQuestionId() == FAiL_ANSWER_INDEX) {
            return false;
        }
        return true;
    }

    public int generateNumberOfGames(Object number) {
        if (number != null) {
            return Integer.parseInt(number.toString()) + 1;
        } else {
            return 1;
        }
    }

    public String generateEndGameMessage(int questionId, String answerIndex) {
        int answerId = Integer.parseInt(answerIndex);
        if (questionId == MAX_NUMBER_OF_QUESTIONS && answerId != NEGATIVE_ANSWER_INDEX) {
            log.info("Quest completed with victory");
            return VICTORY_MESSAGE;
        } else {
            log.info("Quest completed with failure");
            return FAILURE_MESSAGE;
        }
    }

    public String generateResultImgPath(int questionId, String answerIndex) {
        int answerId = Integer.parseInt(answerIndex);
        if (questionId == MAX_NUMBER_OF_QUESTIONS && answerId != NEGATIVE_ANSWER_INDEX) {
            return VICTORY_IMG;
        } else {
            return FAILURE_IMG;
        }
    }

    public String generateProgress(int questionId) {
        return switch (questionId) {
            case 1 -> "33";
            case 2 -> "66";
            default -> "0";
        };
    }
}
