all:
	java -jar jflex-1.6.1.jar part1.flex
	javac Main.java
launch:
	java Main Euclid.imp
jar:
	javac Main.java
	jar cfm ../dist/LexicalAnalyzer.jar manifest.mf Main.class
	java -jar ../dist/LexicalAnalyzer.jar ../test/Euclid.imp
test:
	java part1 test.txt
