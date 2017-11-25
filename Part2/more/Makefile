all:
	#-----------------
	#-----------------
	#
	#
	java -jar src/jflex-1.6.1.jar src/LexicalAnalyzer.flex
	javac src/*.java -d .
	#
	#
	#-----------------
	#-----------------
euclid:
	#-----------------
	#LAUNCH EUCLID.imp
	#-----------------
	java Main ../test/Euclid.imp
	#
	#-----------------
	#
	#-----------------
test:
	#-----------------
	# LAUNCH Test.imp
	#-----------------
	java Main ../test/Test.imp
	#
	#-----------------
	#
	#-----------------

aa:
	#-----------------
	# LAUNCH Test.imp
	#-----------------
	java Main ../test/aa.imp
	#
	#-----------------
	#
	#-----------------

clean:
	rm *.class

jar:
	javac src/Main.java
	jar cfm ../dist/part2.jar manifest.mf Main.class
	java -jar ../dist/part2.jar ../test/Euclid.imp
