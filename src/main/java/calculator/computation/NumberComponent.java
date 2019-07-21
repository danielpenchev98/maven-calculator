package calculator.computation;

public class NumberComponent implements EquationComponent {

    private final String value;

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
        if(obj instanceof NumberComponent){

            return value.equals(((NumberComponent) obj).value);
        }
        return false;
    }
}
