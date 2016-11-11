## Makefile
## build phases for recipe lib

all: jar

build:
	cd src && javac -cp ../lib librecipe/*.java

jar: build
	cd src && jar cf ../librecipe.jar librecipe/*.class

docs: .PHONY
	javadoc -d docs src/librecipe/*.java

clean:
	rm -f librecipe.jar src/librecipe/*.class test/*.class test/util/*.class

test: .PHONY
	javac test/util/Runner.java
	java test/util/Runner

.PHONY:
