all:
	java -jar jflex-1.6.1.jar part1.flex
	javac MyScanner.java
	java MyScanner Euclid.imp
test:
	java part1 test.txt
