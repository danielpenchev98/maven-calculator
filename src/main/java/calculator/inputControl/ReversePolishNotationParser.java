package calculator.inputControl;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ReversePolishNotationParser {

    private Map<String,OperatorSettings> priorityOfOperators;

    private class OperatorSettings
    {
        public int priority;
        public boolean isLeftAssociative;

        public OperatorSettings(final int prior, final boolean leftAssoci)
        {
            priority=prior;
            isLeftAssociative=leftAssoci;
        }
    }

    private Stack<String> operators;
    private StringBuilder reversedPolishEquation;
    private EquationValidator checker;

    public ReversePolishNotationParser()
    {
       priorityOfOperators=new HashMap<String,OperatorSettings>(){{
            put("+", new OperatorSettings(2,true));
            put("-", new OperatorSettings(2,true));
            put("/", new OperatorSettings(3,true));
            put("*", new OperatorSettings(4,true));
            put("^",new OperatorSettings(4,false));
        }};
        operators=new Stack<>();
        reversedPolishEquation=new StringBuilder();
        checker=new EquationValidator();
    }

    //TODO fix this documentation and to use Polymorphism
    /**
     * Shunting yard algorithm
     * Rules:
     * 1) If it's a number - add to equation
     * 2) If it's an opening bracket - add to operator stack
     * 3) if it's an operator :
     * 3.1) if the operator on the top of the stack has greater priority, add it to Equation and push the current operator
     * 3.2) if the operator on the top of the stack has lower or equal priority, add current one to stack
     * 4) if it's closing bracket - add everything from the stack to equation, till you find opening bracket in the stack
     * 5) In the end add everything from the stack to equation
     * @param equation infix notation
     * @return reverse polish notation
     */
    public String formatFromInfixToReversedPolishNotation(String equation)
    {
        String[] components=getIndividualComponents(equation);
        for(String component:components)
        {
            if(checker.isValidNumber(component))
            {
                addComponentToReversedPolishEquation(component);
                addSpaceToReversedPolishEquation();
            }
            else if (checker.isValidOperator(component)){

                    while (!operators.isEmpty() &&priorityOfOperators.containsKey(operators.peek())&&(hasLowerPriority(operators.peek(),component)||(hasEqualPriority(operators.peek(),component)&&isLeftAssoiciative(operators.peek()))))
                    {
                        addComponentToReversedPolishEquation(operators.pop());
                        addSpaceToReversedPolishEquation();
                    }
                    operators.add(component);
            }
            else if(isClosingBracket(component))
            {
                while(hasSpareOperators()&&!isOpeningBracket(operators.peek()))
                {
                    addComponentToReversedPolishEquation(operators.pop());
                    addSpaceToReversedPolishEquation();
                }

                operators.pop();
            }
            else
            {
                operators.add(component);
            }
        }
        while(!operators.isEmpty())
        {

            addComponentToReversedPolishEquation(operators.pop());
            addSpaceToReversedPolishEquation();
        }
        removeParasiteSpace();
        return reversedPolishEquation.toString();
    }

    private void addComponentToReversedPolishEquation(final String component)
    {
        reversedPolishEquation.append(component);
    }

    private void addSpaceToReversedPolishEquation()
    {
        reversedPolishEquation.append(" ");
    }

    private boolean hasLowerPriority(final String previousOperator,final String currentOperator)
    {
        return priorityOfOperators.get(previousOperator).priority > priorityOfOperators.get(currentOperator).priority;
    }

    private boolean hasEqualPriority(final String previousOperator,final String currentOperator)
    {
        return priorityOfOperators.get(previousOperator).priority == priorityOfOperators.get(currentOperator).priority;
    }

    private boolean isLeftAssoiciative(final String component)
    {
        return priorityOfOperators.get(component).isLeftAssociative;
    }

    private boolean isOpeningBracket(final String item)
    {
        return item.equals("(");
    }

    private boolean isClosingBracket(final String item)
    {
        return item.equals(")");
    }

    private boolean hasSpareOperators()
    {
        return operators.size()>0;
    }

    private String[] getIndividualComponents(final String equation)
    {
        return equation.split(" ");
    }

    private void removeParasiteSpace()
    {
        reversedPolishEquation.deleteCharAt(reversedPolishEquation.lastIndexOf(" "));
    }
}
