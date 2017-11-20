all:
	#Care Ligne 801, useless out.println();
	#Care Ligne 801, useless out.println();
	#java -jar jflex-1.6.1.jar LexicalAnalyzer.flex
	#Care Ligne 801, useless out.println();
	#Care Ligne 801, useless out.println();
	javac LexicalAnalyzer.java
	javac Parser.java
	javac Main.java
launch:
	javac LexicalAnalyzer.java
	javac Parser.java
	javac Main.java
	java Main ../test/Euclid.imp
jar:
	javac Main.java
	jar cfm ../dist/LexicalAnalyzer.jar manifest.mf Main.class
	java -jar ../dist/LexicalAnalyzer.jar ../test/Euclid.imp
