all:
	java -jar jflex-1.6.1.jar LexicalAnalyzer.flex
	javac Main.java
	java Main Euclid.imp
test:
	java part1 test.txt
