package ua.javarush;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class GameProcessor {
    private static Game game;
    private int questionId = 0;
    private int answerId = 0;

    public GameProcessor(String path) {
        createGame(path);
    }

    private void createGame(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = GameProcessor.class.getResourceAsStream(path);
        try {
            game = objectMapper.readValue(is, Game.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateQuestionText() {
        return game.getQuestions().get(questionId).getText();
    }

    public String generateFirstAnswerText() {
        return game.getQuestions().get(questionId).getAnswers().get(0).getText();
    }

    public String generateSecondAnswerText() {
        return game.getQuestions().get(questionId).getAnswers().get(1).getText();
    }

    public String generateAnswerMessage(String answerIndex) {
        if (checkAnswerId(answerIndex)) {
            return game.getQuestions().get(questionId).getAnswers().get(answerId).getMessage();
        }
        return "";
    }

    public boolean generateNextQuestionId(String answerIndex) {
        if (checkAnswerId(answerIndex)) {
            if (game.getQuestions().get(questionId).getAnswers().get(answerId).getNextQuestionId() == -1) {
                return false;
            } else {
                questionId = game.getQuestions().get(questionId).getAnswers().get(answerId).getNextQuestionId();
                return true;
            }
        } else {
            questionId = 0;
            return true;
        }
    }

    private boolean checkAnswerId(String answerIndex) {
        if (answerIndex != null) {
            answerId = Integer.parseInt(answerIndex);
            return true;
        }
        return false;
    }
}
