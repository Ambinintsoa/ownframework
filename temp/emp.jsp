<%@page import="java.util.*,olona.Emp,etu1864.framework.FileUpload" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<%
    ArrayList<Emp> a = (ArrayList<Emp> )request.getAttribute("dg");
    FileUpload fi = (FileUpload)request.getAttribute("file");
    for(int i =0;i<a.size();i++){
        out.print(((Emp)(a.get(i))).getName());
    }
    out.print(fi.getFilename());
%>
<center>
    <h1>COUCOU EVERYBODY</h1>
</center>
</body>
</html>