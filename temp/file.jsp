<%@page import="etu1864.framework.FileUpload" %>
<% FileUpload file  = (FileUpload)(request.getAttribute("file"));%>
<!DOCTYPE html>
<html>
<head>
  <title>Fichier</title>
  <!-- Inclure les fichiers CSS -->
  <link rel="stylesheet" href="assets/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
  <style>
    /* Style personnalisé */
    body {
      background-color: #f8f9fa;
    }
    .jumbotron {
      background-color: #fff;
      padding: 2rem 1rem;
      border-radius: 0.5rem;
    }
    h1, p {
      text-align: center;
    }
  </style>
</head>
<body>

  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="jumbotron">
          <h1>File uploaded : <br/><% out.print(file.getFilename());%></h1>
          <p>Phase de test de mon framework.</p>
    </div>
    </div>
    </div>
</div>
          <!-- Inclure les fichiers JavaScript -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>