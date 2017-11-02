all:
	javac LexicalAnalyzer.java
	javac Parser.java
	javac Main.java
launch:
	java Main ../test/Euclid.imp
jar:
	javac Main.java
	jar cfm ../dist/LexicalAnalyzer.jar manifest.mf Main.class
	java -jar ../dist/LexicalAnalyzer.jar ../test/Euclid.imp
