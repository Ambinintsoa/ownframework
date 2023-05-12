export CLASSPATH=.:/home/isabelle/jar/servlet-api.jar
source ~/.bashrc
cd FRAMEWORK/src
javac -parameters -d . *.java 
jar -cvf ../../TEST_FRAMEWORK/WEB-INF/lib/etu1864.jar etu1864
cp ../../TEST_FRAMEWORK/WEB-INF/lib/etu1864.jar /home/isabelle/jar/
cd ../../TEST_FRAMEWORK/WEB-INF/classes
export CLASSPATH=.:/home/isabelle/jar/etu1864.jar
source ~/.bashrc
javac -parameters -d . *.java 
cd ../../
jar -cvf ../TEST_FRAMEWORK.war .
