<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<script src=https://code.jquery.com/jquery-3.6.0.min.js></script>
<html>
<head>
    <title>Second page</title>
</head>
<body>
<h2>Start Game</h2>
<form action="/questions" method="post">
    <input type="submit" value="Submit">
</form>

</body>
</html>