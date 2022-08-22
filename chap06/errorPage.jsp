<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>errorPage</title>
</head>
<body>
    <h1>/WEB-INF/views/errorPage.jsp</h1>
    <hr>

    <p>${_EXCEPTION_}</p>


    <ol>
        <c:forEach var="element" items="${ _EXCEPTION_.getStackTrace() }" >
            <li>at ${element}</li>
        </c:forEach>
    </ol>

    <hr>

    <p>Caused by ${ _EXCEPTION_.getCause() }</p>

    <ol>
        <c:forEach var="oelement" items="${ _EXCEPTION_.getCause().getStackTrace() }" >
            <li>at ${oelement}</li>
        </c:forEach>
    </ol>

</body>
</html>