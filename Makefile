## Makefile
## build phases for recipe lib

CLASSPATH	= .:$(PWD)/lib/jackson-all-1.9.0.jar
JAR_FILE	= librecipe.jar

all: $(JARFILE)

build:
	cd src && javac -cp "$(CLASSPATH)" librecipe/*.java

$(JAR_FILE): build
	cd src && jar cf ../$(JAR_FILE) librecipe/*.class

run: $(JAR_FILE)
	javac -cp "$(CLASSPATH):${PWD}/$(JAR_FILE)" Sample.java
	java -cp "$(CLASSPATH):${PWD}/$(JAR_FILE)" Sample

docs: .PHONY
	javadoc -cp "$(CLASSPATH)" -d docs src/librecipe/*.java

clean:
	rm -f $(JAR_FILE) src/librecipe/*.class test/*.class test/util/*.class

test: $(JAR_FILE)
	javac -cp "$(CLASSPATH):${PWD}/$(JAR_FILE)" test/util/Runner.java
	java -cp "$(CLASSPATH):${PWD}/$(JAR_FILE)" test/util/Runner

.PHONY:
