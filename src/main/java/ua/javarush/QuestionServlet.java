package ua.javarush;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QuestionServlet", urlPatterns= "/questions")
public class QuestionServlet extends HttpServlet {
    private final GameProcessor gameProcessor = new GameProcessor("/game.json");
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(gameProcessor.generateNextQuestionId(req.getParameter("choice"))){
            req.setAttribute("questionText", gameProcessor.generateQuestionText());
            req.setAttribute("firstAnswerText", gameProcessor.generateFirstAnswerText());
            req.setAttribute("secondAnswerText", gameProcessor.generateSecondAnswerText());
            req.setAttribute("message", gameProcessor.generateAnswerMessage(req.getParameter("choice")));

            req.getRequestDispatcher("/question.jsp").forward(req, resp);

        }else{
            req.setAttribute("message", gameProcessor.generateAnswerMessage(req.getParameter("choice")));
            req.getRequestDispatcher("/fail.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("number", "world");
        req.getRequestDispatcher("/question.jsp").forward(req, resp);
    }
}

