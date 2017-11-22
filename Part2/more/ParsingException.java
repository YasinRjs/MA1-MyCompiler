/**
 * Exception class for the parser
 */
class ParsingException extends Exception{

    /**
     * Basic constructor of the exception.
     * It will simply display where the syntax error has occured
     * @param  Symbol token     The token that gave the error
     */
    public ParsingException(Symbol token){
      System.err.println("\n--------");
      System.err.println("Error while parsing: didn't expect \""+ token.getValue() + "\" at line "+ token.getLine()+".");
  }

}
