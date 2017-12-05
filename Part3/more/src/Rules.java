/**
 * Class which will apply the rule accordingly
 */
class Rules {

    private static CodeGenerator generator = new CodeGenerator();

    /**
     * Apply the rule number X of the IMP grammar
     * @param Parser parser     The parser that's doing the parsing
     * @param int    ruleNumber the number of the rule to apply
     * @throws ParsingException A syntax error has been met
     */
    public static void applyRule(Parser parser, int ruleNumber) throws ParsingException{
        System.out.print(ruleNumber+ " ");
        String var;
        switch(ruleNumber){
            case 1:
                parser.match(LexicalUnit.BEGIN);
                generator.generateBegin();
                parser.code();
                parser.match(LexicalUnit.END);
                generator.generateEnding();
                break;
            case 2:
                break;
            case 3:
                parser.instList();
                break;
            case 4:
                parser.instruction(); parser.afterInstruction();
                break;
            case 5:
                parser.assign();
                break;
            case 6:
                parser.ifState();
                break;
            case 7:
                parser.whileState();
                break;
            case 8:
                parser.forState();
                break;
            case 9:
                parser.printState();
                break;
            case 10:
                parser.readState();
                break;
            case 11:
                break;
            case 12:
                parser.match(LexicalUnit.SEMICOLON); parser.instList();
                break;
            case 13:
                var = parser.getCurrentTokenValue();
                parser.match(LexicalUnit.VARNAME);
                parser.match(LexicalUnit.ASSIGN);
                parser.expression();
                generator.generateAssign("%"+var);
                break;
            case 14:
                parser.prodOrDiv(); parser.expressionPrime();
                break;
            case 15:
                parser.secondOp(); parser.expression();
                break;
            case 16:
                break;
            case 17:
                parser.atom(); parser.prodOrDivPrime();
                break;
            case 18:
                parser.firstOp(); parser.prodOrDiv();
                break;
            case 19:
                break;
            case 20:
                parser.match(LexicalUnit.MINUS);
                generator.addElementInExpression("~");
                parser.atom();
                break;
            case 21:
                var = generator.generateVariable("%"+parser.getCurrentTokenValue());
                parser.match(LexicalUnit.VARNAME);

                generator.addElementInExpression(var);
                break;
            case 22:
                var = parser.getCurrentTokenValue();
                parser.match(LexicalUnit.NUMBER);
                generator.addElementInExpression(var);
                break;
            case 23:
                parser.match(LexicalUnit.LPAREN);
                generator.addElementInExpression("(");
                parser.expression();
                parser.match(LexicalUnit.RPAREN);
                generator.addElementInExpression(")");
                break;
            case 24:
                parser.match(LexicalUnit.TIMES);
                generator.addElementInExpression("*");
                break;
            case 25:
                parser.match(LexicalUnit.DIVIDE);
                generator.addElementInExpression("/");
                break;
            case 26:
                parser.match(LexicalUnit.PLUS);
                generator.addElementInExpression("+");
                break;
            case 27:
                parser.match(LexicalUnit.MINUS);
                generator.addElementInExpression("-");
                break;
            case 28:
                parser.match(LexicalUnit.IF);
                parser.cond();
                parser.match(LexicalUnit.THEN);
                generator.generateIf();
                parser.code();

                parser.afterIf();
                break;
            case 29:
                parser.match(LexicalUnit.ENDIF);
                generator.generateEndIf();
                break;
            case 30:
                parser.match(LexicalUnit.ELSE);
                generator.generateElse();
                parser.code();
                parser.match(LexicalUnit.ENDIF);
                generator.generateEndIf();
                break;
            case 31:
                parser.andCond(); parser.condPrime();
                break;
            case 32:
                parser.match(LexicalUnit.OR);
                generator.addCondition("or");
                parser.cond();
                break;
            case 33:
                break;
            case 34:
                parser.simpleCond(); parser.andCondPrime();
                break;
            case 35:
                parser.match(LexicalUnit.AND);
                generator.addCondition("and");
                parser.andCond();
                break;
            case 36:
                break;
            case 37:
                parser.notCond();
                parser.expression();
                var = generator.generateExpression();
                generator.addCondition(var);
                var = parser.getCurrentTokenValue();
                parser.comp();
                generator.addCondition(var);
                parser.expression();
                var= generator.generateExpression();
                generator.addCondition(var);
                break;
            case 38:
                parser.match(LexicalUnit.NOT);
                generator.addCondition("not");
                break;
            case 39:
                break;
            case 40:
                parser.match(LexicalUnit.EQ);
                break;
            case 41:
                parser.match(LexicalUnit.LEQ);
                break;
            case 42:
                parser.match(LexicalUnit.GT);
                break;
            case 43:
                parser.match(LexicalUnit.GEQ);
                break;
            case 44:
                parser.match(LexicalUnit.LT);
                break;
            case 45:
                parser.match(LexicalUnit.NEQ);
                break;
            case 46:
                parser.match(LexicalUnit.WHILE);
                generator.generateWhile();
                parser.cond();
                generator.generateIf();
                parser.match(LexicalUnit.DO);
                parser.code();
                parser.match(LexicalUnit.DONE);
                generator.generateDoneWhile();
                break;
            case 47:
                parser.match(LexicalUnit.FOR); parser.match(LexicalUnit.VARNAME); parser.match(LexicalUnit.FROM); parser.expression(); parser.afterFor();
                break;
            case 48:
                parser.byExpr(); parser.match(LexicalUnit.TO); parser.expression(); parser.match(LexicalUnit.DO); parser.code(); parser.match(LexicalUnit.DONE);
                break;
            case 49:
                parser.match(LexicalUnit.BY); parser.expression();
                break;
            case 50:
                break;
            case 51:
                parser.match(LexicalUnit.PRINT);
                parser.match(LexicalUnit.LPAREN);
                var = parser.getCurrentTokenValue();
                parser.match(LexicalUnit.VARNAME);
                generator.generatePrint("%"+var);
                parser.match(LexicalUnit.RPAREN);
                break;
            case 52:
                parser.match(LexicalUnit.READ);
                parser.match(LexicalUnit.LPAREN);
                var = parser.getCurrentTokenValue();
                parser.match(LexicalUnit.VARNAME);
                generator.generateRead("%"+var);
                parser.match(LexicalUnit.RPAREN);
                break;
        }
    }

}
