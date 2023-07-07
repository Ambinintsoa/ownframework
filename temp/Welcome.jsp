
<!DOCTYPE html>
<html>
<head>
  <title>Page d'accueil</title>
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
    <center>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="jumbotron">
          <h1>Bienvenue sur notre site ! <br/><% out.print(request.getSession().getAttribute("name"));%></h1>
          <p>Phase de test de mon framework.</p>
          <p><a class="btn btn-danger" href="invalidate.fn" role="button">DECONNECTION</a>
            <a class="btn btn-danger" href="delete.fn" role="button">DELETE INFORMATION</a></p>
          <p><a class="btn btn-success" href="liste.fn" role="button">LIST OF EMPLOYE</a></p>
          <p><a class="btn btn-secondary" href="random.fn" role="button">RANDOM by Annotation</a>
            <a class="btn btn-secondary" href="random_model.fn" role="button">RANDOM by modelView</a>
        </p>

<!--                      -->
<div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h2>AJOUTER PAR ARGUMENT </h2>
        <form action="save.fn" method="post" >
          <div class="form-group mt-2">
            <label for="email">Nom :</label>
            <input type="text" class="form-control mt-2" id="email" placeholder="Entrez votre nom" name="nom">
          </div>
          <button type="submit" class="btn btn-success mt-5">Ajouter</button>
        </form>
      </div>
    </div>
  </div>
<!--                        -->
<!--                      -->
<div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h2>AJOUTER PAR ATTRIBUT </h2>
        <form action="save_without.fn" method="get" >
          <div class="form-group mt-2">
            <label for="email">Nom :</label>
            <input type="text" class="form-control mt-2" id="email" placeholder="Entrez votre nom" name="name">
          </div>
          <button type="submit" class="btn btn-success mt-5">Ajouter</button>
        </form>
      </div>
    </div>
  </div>
<!--                        -->
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h2>Envoyer un fichier</h2>
        <form action="file.fn" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="file">Choisir un fichier :</label>
                <input type="file" class="form-control-file" id="file" name="fi">
                <button type="submit" class="btn btn-success mt-5">TELECHARGER IMAGE </button>
              </div>
        </form>
      </div>
    </div>
  </div>

  <!--                         -->
</div>
</div>
</div>
</div>
</center>
  <!-- Inclure les fichiers JavaScript -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>