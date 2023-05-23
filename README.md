# ownframework
create my own framework step by step

#GUIDE PRATIQUE DU FRAMEWORK
DOC:
	-le framework utilise servlet-api.jar
	
	
prise en main :
-importation du framework(etu1864.jar) dans le répertoire lib de votre projet
-importation de error.jsp dans votre projet
-paramétrage de votre xml:
ajout de syntaxe
  <servlet>
    <servlet-name>processServlet</servlet-name>
    <servlet-class>etu1864.framework.servlet.FrontServlet</servlet-class>
    <init-param>
      <param-name>package_to_scan</param-name>
      <param-value>chemin vers votre répertoire classes </param-value>
      <description>scan package having this url</description>
  </init-param>
  </servlet>

  <servlet-mapping>
    <servlet-name>processServlet</servlet-name>
      <url-pattern>/</url-pattern>
  </servlet-mapping>
  
  
  UTILISATION:	
 -mise en relation method-url
	 annoter votre méthode ainsi 
	     @Urls(name = "/nom de l'url associée")
	    public  method(){
	       //code
	    }
-retour de valeur:
utiliser la classe ModelView
     setView(String ): nom de la page de retour
     setData(HashMap<String,Object> ):association de données
    	string : nom de donnée
    	object : valeur de donnée
    ex:
    	    public  ModelView method(){
	       //code
	    }
   -utiliser request.getAttribute("nom des donnée specifiée dans data") pour prendre les données dans la classe


REMARQUES:
	_le framework ne peut pas prendre en compte des méthodes ayant des arguments
	_chaque  paramètre de requête de même nom qu'une attribut de classe  permet automatiquement d'assigner cette valeur à l'attribut
	_"chemin vers votre répertoire classes":est la seule valeur a modifiée DANS LE .XML pour éviter tout disfonctionnement du framework
