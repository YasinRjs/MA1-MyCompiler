/**
 * Exception class for the LexicalAnalyzer
 */
class AnalyzingException extends Exception{
    /**
     * Basic constructor of the exception.
     * It will simply display where the error has occured
     * @param  String token     The token that gave the error
     * @param int line          The line where the error has occured
     */
    AnalyzingException(String token, int line){
        System.err.println("Error while analyzing: unknown token \""+token+"\" at line "+line+".");
    }
}
