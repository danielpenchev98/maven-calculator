package calculator.computation;

public class ClosingBracket implements EquationComponent {
   final static String SYMBOL=")";

   @Override
   public boolean equals(Object obj)
   {
      return obj!=null&&getClass()==obj.getClass();
   }
}