## Makefile
## build phases for lib.*

all: build

build:
	javac lib/*.java

clean:
	rm -f lib/*.class test/*.class test/util/*.class

test: .PHONY
	javac test/util/Runner.java
	java test/util/Runner

.PHONY:
