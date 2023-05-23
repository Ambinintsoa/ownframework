
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
mkdir ../temp
mkdir ../temp/WEB-INF 
mkdir ../temp/WEB-INF/classes
for file in "WEB-INF/classes"/*
do 
    if [ -d "$file"  ]; then
    mkdir  "../temp/WEB-INF/classes/"$(basename "$file")
    cp  "$file"/*.class "../temp/WEB-INF/classes/"$(basename "$file")/
    fi
done

for file in "../temp/WEB-INF/classes"/*
do 
    if [ -z $(ls -A "$file") ]; then
        rmdir $file
    fi
done
    cp "."/*.jsp  "../temp"/ 
    cp "WEB-INF"/*.xml  "../temp/WEB-INF"/ 
    cp -r "WEB-INF/lib"  "../temp/WEB-INF"/ 
    cd ../temp/
jar -cvf ../../../TEST.war .
