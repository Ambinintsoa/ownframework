
<!DOCTYPE html>
<html>
<head>
  <title>Formulaire </title>
  <!-- Inclure les fichiers CSS de Bootstrap -->
  <link rel="stylesheet" href="assets/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
</head>
<body>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h2>Connexion </h2>
        <h3>User <% out.print(request.getSession().getAttribute("profile"));%></h3>
        <form action="log_in.fn" method="get" enctype="multipart/form-data">
          <div class="form-group mt-5">
            <label for="email">Email :</label>
            <input type="email" class="form-control mt-2" id="email" placeholder="Entrez votre email" name="name">
          </div>
          <div class="form-group mt-5">
            <label for="password">Mot de passe :</label>
            <input type="password" class="form-control mt-2 " id="password" placeholder="Entrez votre mot de passe" name="password">
          </div>
          <button type="submit" class="btn btn-primary mt-5">Se connecter</button>
        </form>
      </div>
    </div>
  </div>

  <!-- Inclure les fichiers JavaScript de Bootstrap -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>