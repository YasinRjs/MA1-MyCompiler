all:
	#-----------------
	#START MAKE
	#-----------------
	#
	javac LexicalAnalyzer.java
	javac Parser.java
	javac Main.java
	#
	#-----------------
	#END MAKE
	#-----------------
launch:
	#-----------------
	#LAUNCHING APP
	#-----------------
	java Main ../test/Euclid.imp
jar:
	#java -jar jflex-1.6.1.jar LexicalAnalyzer.flex
	javac Main.java
	jar cfm ../dist/LexicalAnalyzer.jar manifest.mf Main.class
	java -jar ../dist/LexicalAnalyzer.jar ../test/Euclid.imp
