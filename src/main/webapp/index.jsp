<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<script src=https://code.jquery.com/jquery-3.6.0.min.js></script>
<link rel="stylesheet" type="text/css" href="styles.css">
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<script>
    function showDescription() {
        document.getElementById("description-container").style.visibility = "visible";
    }
</script>

<div class="main-container">
    <h2 class="main-text">UFO CHALLENGE GAME</h2>
    <button class="button" onclick="showDescription()">Read history</button>

    <form action="/questions" method="post">
        <input type="text" class="input-field" name="username" placeholder="username" autofocus><br>
        <input type="submit" value="Start Game" class="button">
    </form>
    <% request.getSession().setAttribute("questionId", 0); %>

</div>

<div id="description-container">
    <div class="main-text-container">
        <h2 class="description-header">UFO Challenge</h2>
        <p class="main-description">If UFOs were visiting our world, where were these extraterrestrials? Could they be
            hidden among us? Comic books and television illustrates how the possibility of extraterrestrial visitors
            reflected anxieties of that era.</p>
        <p class="additional-description">Skepticism and speculative imagination come together as two halves of the
            whole.
            It's essential to entertain and explore new ideas, however strange. The vast majority of all sightings that
            anyone has ever reported or seen are easily identifiable mistakes, or confusion with known astronomical
            bodies
            or known spacecraft or aircraft. A huge percentage of UFO sightings actually turn out to be the planet
            Venus,
            sort of an unexpectedly bright object in the sky. A huge percentage of UFO sightings in the 1950s and early
            1960s
            were the U2 spy plane, which was a secret project that was a literal UFO. It was a plane that didn't look
            like known
            planes flying at an altitude that planes weren't known to fly at, at speeds planes were not known to fly at.
            A large
            number of UFO sightings in years since have been other secret spy planes.This game illustrates the
            development of
            events if UFOs really visited our planet.</p>
    </div>

    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQiiws3NOp2e3e4mdnfA4FewY2sdFDmHuY4A&usqp=CAU"
         alt="ufo picture"
         class="picture">
</div>

</body>
</html>
