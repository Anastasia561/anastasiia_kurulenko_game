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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("username", gameProcessor.generateUsername(req.getParameter("username")));
        req.getSession().setAttribute("numberOfGames", gameProcessor.getNumberOfGames());
        req.getSession().setAttribute("ip", req.getRemoteAddr());

        req.setAttribute("message", gameProcessor.generateAnswerMessage(req.getParameter("choice")));

        if (gameProcessor.generateNextQuestionId(req.getParameter("choice"))) {
            req.setAttribute("progress", gameProcessor.generateProgress());
            req.setAttribute("questionText", gameProcessor.generateQuestionText());
            req.setAttribute("firstAnswerText", gameProcessor.generateFirstAnswerText());
            req.setAttribute("secondAnswerText", gameProcessor.generateSecondAnswerText());
            req.getRequestDispatcher("/question.jsp").forward(req, resp);
        } else {
            req.setAttribute("endGameMessage", gameProcessor.generateEndGameMessage());
            req.setAttribute("imgPath", gameProcessor.generateResultImgPath());
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/question.jsp").forward(req, resp);
    }
}
