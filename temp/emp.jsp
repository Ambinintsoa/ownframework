<%@page import="java.util.*,olona.Emp,etu1864.framework.FileUpload" %>
<% ArrayList<Emp> employe = (ArrayList<Emp>)(request.getAttribute("employe"));%>
<% Emp new_employe = (Emp)(request.getAttribute("new_employe"));%>
<!DOCTYPE html>
<html>
<head>
  <title>Liste des employés</title>
  <!-- Inclure les fichiers CSS de Bootstrap -->
  <link rel="stylesheet" href="assets/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
</head>
<body>
  <div class="container">
    <h2>Liste des employés</h2>
    <table class="table">
      <thead>
        <tr>
          <th>Nom</th>
        </tr>
      </thead>
      <tbody>

         <%
         if(employe !=null){
         
         for(int i =0 ; i< employe.size() ;i++){%>
        <tr>
          <td><% out.print(employe.get(i).getName());%></td>
        </tr>
          <% } } %>
       
      </tbody>
    </table>
    <h2>nouceau employé</h2>
    <table class="table">
        <thead>
          <tr>
            <th>Nom</th>
          </tr>
        </thead>
        <tbody>
           <% 
           if(new_employe !=null){%>
          <tr>
            <td><% out.print(new_employe.getName());%></td>
          </tr>
            <%  } %>
         
        </tbody>
      </table>
  </div>

  <!-- Inclure les fichiers JavaScript de Bootstrap -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1