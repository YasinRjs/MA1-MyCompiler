all:
	#-----------------
	#START MAKE
	#-----------------
	#
	java -jar src/jflex-1.6.1.jar src/LexicalAnalyzer.flex
	javac src/*.java -d .
	#
	#-----------------
	#END MAKE
	#-----------------
launch:
	#-----------------
	#LAUNCHING APP
	#-----------------
	java Main ../test/Euclid.imp

clean:
	rm *.class

jar:
	javac src/Main.java
	jar cfm ../dist/part2.jar manifest.mf Main.class
	java -jar ../dist/part2.jar ../test/Euclid.imp
