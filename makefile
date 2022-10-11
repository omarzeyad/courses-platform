
all: compile-files run-CMS

compile-files:
	javac src/Main.java src/platform/*.java src/user/*.java src/io/*.java -d bin/

run-CMS:
	java -classpath bin/ Main

clean:
	rm -r bin/
