## Makefile
## build phases for recipe lib

all: run

build:
	cd src && javac -cp ../lib librecipe/*.java

jar: build
	cd src && jar cf ../librecipe.jar librecipe/*.class

run: jar
	java -cp ".:${PWD}/librecipe.jar" Sample

docs: .PHONY
	javadoc -d docs src/librecipe/*.java

clean:
	rm -f librecipe.jar src/librecipe/*.class test/*.class test/util/*.class

test: .PHONY
	javac -cp ./ test/util/Runner.java
	java test/util/Runner

.PHONY:
