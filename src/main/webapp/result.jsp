<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" type="text/css" href="styles.css">
<html>
<head>
    <title>Result</title>
</head>
<body>

<div class="form-container">
    <h2 class="result-message-text">${message}</h2>
    <h2 class="result-text">${endGameMessage}</h2>

    <img src="${imgPath}">

    <form action="/questions" method="post">
        <input type="submit" value="Start Again" class="button">
    </form>
</div>

</body>
</html>
