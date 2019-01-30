for jar in find libs -name "*.jar" do
 export CLASSPATH=$CLASSPATH:$jar
done

find src -name "*.java" > sources.txt

rm -rf ./classes

mkdir ./classes

javac -d $(dirname $(readlink -f $0))/classes -classpath $CLASSPATH @sources.txt -nowarn

rm -rf ./sources.txt
