package calculator.computation;

public class NumberComponent implements EquationComponent {

    private String value;

    public NumberComponent(final String value)
    {
        this.value=value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj!=null&&getClass()==obj.getClass()) {
          return  value.equals(((NumberComponent) obj).value);
        }
        return false;
    }
}
