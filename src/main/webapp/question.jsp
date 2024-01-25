<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" type="text/css" href="styles.css">
<html>
<head>
    <title>Question</title>
</head>
<body>

<div class="form-container">
    <form action="/questions" method="post">
        <h2 class="message-text">${message}</h2>
        <h2 class="question-text">${questionText}</h2>

        <input type="radio" id="choice1" name="choice" value="0">
        <label for="choice1" class="option-text">${firstAnswerText}</label><br>

        <input type="radio" id="choice2" name="choice" value="1">
        <label for="choice2" class="option-text">${secondAnswerText}</label><br>

        <input type="submit" value="Answer" class="question-button">
    </form>
</div>
<% Object questionId = request.getSession().getAttribute("questionId");
    request.getSession().setAttribute("questionId", (Integer.parseInt(questionId.toString())) + 1); %>

<div class="progress-light">
    <div class="progress" style="width:${progress}%"></div>
</div>

<div class="statistics-container">
    <h4 class="statistics-header">Statistics</h4>
    <p>IP Address: ${sessionScope.ip}</p>
    <p>Number of games played: ${sessionScope.numberOfGames} </p>
</div>

</body>
</html>
