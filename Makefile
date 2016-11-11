## Makefile
## build phases for recipe lib

CLASSPATH	= .:$(PWD)/lib/jackson-all-1.9.0.jar
JAR_FILE	= librecipe.jar

all: run

build:
	cd src && javac -cp "$(CLASSPATH)" librecipe/*.java

jar: build
	cd src && jar cf ../$(JAR_FILE) librecipe/*.class

run: jar
	java -cp "$(CLASSPATH):${PWD}/$(JAR_FILE)" Sample

docs: .PHONY
	javadoc -d docs src/librecipe/*.java

clean:
	rm -f $(JAR_FILE) src/librecipe/*.class test/*.class test/util/*.class

test: jar
	javac -cp "$(CLASSPATH):${PWD}/$(JAR_FILE)" test/util/Runner.java
	java -cp "$(CLASSPATH):${PWD}/$(JAR_FILE)" test/util/Runner

.PHONY:
