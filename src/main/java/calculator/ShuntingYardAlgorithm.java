package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ShuntingYardAlgorithm {
    private Map<String,Integer> priority;

    public ShuntingYardAlgorithm()
    {
        priority=new HashMap<String,Integer>(){{
            put("+", 1);
            put("-", 1);
            put("/", 2);
            put("*", 2);
        }};
    }

    public void formatToReversedPolishNotation(String equation)
    {
        Stack<String> operators=new Stack<>();
        String[] splitInput=equation.split(" ");
        StringBuilder reversedPolish=new StringBuilder();
        for(String item:splitInput)
        {
            //number
            if(item.matches("^[0-9]+$"))
            {
                if(!reversedPolish.toString().equals(""))
                {
                   reversedPolish.append(" ");
                }
                reversedPolish.append(item).append(" ");
            }
            else {
                //if its an operator
                if (priority.containsKey(item)){

                    //while the top item of the stack is a higher priority operator than the current one
                    while (!operators.isEmpty() &&priority.containsKey(operators.peek()) &&priority.get(operators.peek()) > priority.get(item)) {
                        reversedPolish.append(operators.pop()).append(" ");
                        operators.pop();
                    }
                    operators.add(item);
                }
                else if(item.equals(")"))
                {
                   while(!operators.isEmpty()&&!operators.peek().equals("("))
                   {
                       reversedPolish.append(operators.pop()).append(" ");
                   }

                   //if "(" hasnt been found
                   if(operators.isEmpty())
                   {
                       System.out.println("Invalid expression");
                       return;
                   }
                   operators.pop();
                }
                else
                {
                    operators.add(item);
                }
            }
        }
        while(!operators.isEmpty())
        {
            if(operators.peek().equals("("))
            {
                System.out.println("Invalid equation");
                return;
            }
            reversedPolish.append(operators.pop()).append(" ");
        }
        System.out.println(reversedPolish.toString());
    }
}
