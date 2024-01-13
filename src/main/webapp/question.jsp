<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Question</title>
</head>
<body>

<form action="/questions" method="post">
    <h2>${message}</h2>
    <h2>${questionText}</h2>

    <input type="radio" id="choice1" name="choice" value="0">
    <label for="choice1">${firstAnswerText}</label><br>

    <input type="radio" id="choice2" name="choice" value="1">
    <label for="choice2">${secondAnswerText}</label><br>

    <input type="submit" value="Submit">
</form>
</body>
</html>