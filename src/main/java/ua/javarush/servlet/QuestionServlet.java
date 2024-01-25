package ua.javarush.servlet;

import ua.javarush.processor.GameProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QuestionServlet", urlPatterns = "/questions")
public class QuestionServlet extends HttpServlet {
    private final GameProcessor gameProcessor = new GameProcessor();

    {
        gameProcessor.createGame("/game.json");
        final String username;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("ip", req.getRemoteAddr());

        int questionId = Integer.parseInt(req.getSession().getAttribute("questionId").toString());
        String answerId = req.getParameter("choice");

        req.setAttribute("message", gameProcessor.generateAnswerMessage(questionId - 1, answerId));

        if (gameProcessor.checkNextQuestionId(questionId - 1, answerId)) {
            req.setAttribute("progress", gameProcessor.generateProgress(questionId));
            req.setAttribute("questionText", gameProcessor.generateQuestionText(questionId));
            req.setAttribute("firstAnswerText", gameProcessor.generateFirstAnswerText(questionId));
            req.setAttribute("secondAnswerText", gameProcessor.generateSecondAnswerText(questionId));
            req.getRequestDispatcher("/question.jsp").forward(req, resp);
        } else {
            req.setAttribute("endGameMessage", gameProcessor.generateEndGameMessage(questionId, answerId));
            req.setAttribute("imgPath", gameProcessor.generateResultImgPath(questionId, answerId));
            req.getSession().setAttribute("questionId", "0");
            req.getSession().setAttribute("numberOfGames", gameProcessor.generateNumberOfGames(req.getSession().getAttribute("numberOfGames")));
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/question.jsp").forward(req, resp);
    }
}
