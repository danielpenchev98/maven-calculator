package calculator;

import calculator.computation.MathComponentType;
import calculator.inputControl.EquationValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ReversePolishNotationParser {

    private Map<String,Integer> priority;

    private Stack<String> operators;
    private StringBuilder reversedPolishEquation;
    private EquationValidator checker;

    public ReversePolishNotationParser()
    {
        priority=new HashMap<String,Integer>(){{
            put("+", 1);
            put("-", 1);
            put("/", 2);
            put("*", 2);
        }};
        operators=new Stack<>();
        reversedPolishEquation=new StringBuilder();
        checker=new EquationValidator();
    }

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

                    //while the top item of the stack is a higher priority operator than the current one
                    while (!operators.isEmpty() &&priority.containsKey(operators.peek()) &&hasLowerPriority(operators.peek(),component)) {
                        addComponentToReversedPolishEquation(operators.pop());
                        addSpaceToReversedPolishEquation();
                    }
                    operators.add(component);
            }
            else if(isClosingBracket(component))
            {
                while(hasSpareOperators()&&!isOpeningBracket(component))
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
        return priority.get(previousOperator) > priority.get(currentOperator);
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
