# Mon propre framework - Guide pratique

Ce guide pratique vous aidera à créer votre propre framework étape par étape.

## Documentation

Le framework utilise servlet-api.jar. Assurez-vous de l'avoir importé dans votre projet.
Le framework utilise Gson.jar. Assurez-vous de l'avoir importé dans votre projet.


## Prise en main

1. Importez le framework (etu1864.jar) dans le répertoire lib de votre projet.
2. Importez error.jsp dans votre projet.
3. Paramétrez votre fichier XML en suivant les étapes ci-dessous :

### Ajout de syntaxe dans le fichier XML

<servlet>
  <servlet-name>processServlet</servlet-name>
  <servlet-class>etu1864.framework.servlet.FrontServlet</servlet-class>
  <init-param>
    <param-name>package_to_scan</param-name>
    <param-value>chemin vers votre répertoire classes</param-value>
    <description>scan package having this url</description>
  </init-param>
  <init-param>
      <param-name>upload_directory</param-name>
      <param-value>chemin vers votre répertoire de sauvegarde de fichier</param-value>
      <description>all files are upload here</description>
  </init-param>
</servlet>

<servlet-mapping>
  <servlet-name>processServlet</servlet-name>
  <url-pattern>*.fn</url-pattern>
</servlet-mapping>

## Utilisation

### Mise en relation méthode-URL

Annotez votre méthode comme suit :

@Urls(name = "/nom_de_l_url_associee.fn")
public void method() {
   // Code
}

### Retour de valeur

Utilisez la classe ModelView pour retourner une valeur :

- Utilisez setView(String) pour spécifier le nom de la page de retour.
- Utilisez setData(HashMap<String,Object>) pour associer des données à retourner.
   - Utilisez une clé de type String pour le nom de la donnée.
   - Utilisez une valeur de type Object pour la valeur de la donnée.

Exemple :

public ModelView method() {
   // Code
   ModelView modelView = new ModelView();
   modelView.setView("nom_de_la_page_de_retour");
   HashMap<String, Object> data = new HashMap<>();
   data.put("nom_de_donnee", valeur_de_donnee);
   modelView.setData(data);
   return modelView;
}

### Ajout de session 
- Pour connecter un client il faut les informations suivantes(isConnected(boolean),profile(profile de l'utilisateur))
- exemple:
@Urls(name = "/login.fn" )
@Arguments(arguments = {"name","password"})
    public ModelView log_in(String name,String password){
        ModelView mv = new ModelView();
        mv.setSession(new HashMap<String,Object>());
        //traitement
        mv.addSession("isConnected", valeur);
        mv.addSession("profile", "valeur");
        mv.setView("page d'accueil");
        return mv;
}
-pour ajouter une session
  mv.addSession("nom", valeur);

### Suppression de session
- Ajouter les commandes suivantes

  v.setInvalidateSession(true);
  ArrayList list = new ArrayList<>();
  list.add("nom de session");
  v.setRemoveSession(list);

### Récupération des données

Utilisez request.getAttribute("nom_de_la_donnee") pour récupérer les données dans la classe.

### Récupération des sessions

Utilisez request.getSession().getAttribute("nom_de_la_session")

### Fonction avec arguments
- Pour les fonctions qui nécessitent un argument, ajoutez l'annotation @Arguments(arguments = {"arg1","arg2"}).

### Utilisation de session dans la classe 

Ajoutez l'annotation @Session à chaque fonction qui utilise la session. Assurez-vous d'avoir un attribut HashMap<String,Object> session déclaré dans votre classe.

### Annotation pour une catégorie de personne spécifique

Utilisez l'annotation @Auth(profile = "profile") pour spécifier que la méthode est réservée à une catégorie de personnes spécifique.

### SINGLETON
Pour transformer votre classe en Singleton, utiliser l'annotation @Scope(singleton = true)

 ### JSON

- Pour que les données du ModelView retourné soient transformées en JSON, utilisez modelview.setJson(true).
- Pour les fonctions qui retournent un objet que vous souhaitez transformer en JSON, ajoutez l'annotation @Json.

## Remarques

- Chaque paramètre de requête portant le même nom qu'un attribut de classe permet d'assigner automatiquement cette valeur à l'attribut.
- Assurez-vous de modifier uniquement la valeur "chemin vers votre répertoire classes" dans le fichier XML pour éviter tout dysfonctionnement du framework.
- si l'attribut invalidateSession du modelview est true or la liste removeSession est null ,la session actuelle sera invalide