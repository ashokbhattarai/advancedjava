<%
    Cookie[] cookies = request.getCookies();
    String username = "Not Found";

    if (cookies != null) {
        for (Cookie c : cookies) {
            if (c.getName().equals("username")) {
                username = c.getValue();
            }
        }
    }
%>

<html>
<head><title>Result Page</title></head>
<body>

<h2>Result Page</h2>

<p>Welcome User: <%= username %></p>

<!-- JSP Declaration -->
<%! int counter = 0; %>

<% counter++; %>

<p>Page Visit Counter (JSP Declaration): <%= counter %></p>

<br>
<a href="index.jsp">Back to Home</a>

</body>
</html>
