<%@page import="olona.Emp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <% Emp a = (Emp)request.getAttribute("employe"); %>
    <h1>Hello <% out.print(a.getName());%>, nous savons que vous avez <%out.print(a.getAge()); %></h1>
</body>
</html>