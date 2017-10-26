all:
	java -jar jflex-1.6.1.jar LexicalAnalyzer.flex
	javac Main.java
launch:
	java Main ../test/Euclid.imp
jar:
	javac Main.java
	jar cfm ../dist/LexicalAnalyzer.jar manifest.mf Main.class
	java -jar ../dist/LexicalAnalyzer.jar ../test/Euclid.imp
