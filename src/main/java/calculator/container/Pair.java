package calculator.container;

//should switch to Java Generics, instead of using String, maybe
public class Pair {

    private String first_member;
    private String second_member;

    public Pair(final String first_arg,final String second_arg)
    {
        first_member=first_arg;
        second_member=second_arg;
    }

    public String getFirstMember()
    {
        return first_member;
    }

    public String getSecondMember()
    {
        return second_member;
    }

}
