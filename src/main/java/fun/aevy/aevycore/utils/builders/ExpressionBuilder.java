package fun.aevy.aevycore.utils.builders;

import java.util.Stack;

/**
 * Implementation of the Djikstra's two stack algorithm for evaluating arithmetic expressions.
 * @author Sorridi
 * @since 1.8
 */
public class ExpressionBuilder
{
    private final String raw;

    private final Stack<String> operations;
    private final Stack<Double> values;

    /**
     * Creates a new ExpressionBuilder instance.
     * @param raw The raw expression to be evaluated.
     */
    public ExpressionBuilder(String raw)
    {
        this.raw        = raw;
        this.operations = new Stack<>();
        this.values     = new Stack<>();
    }

    /**
     * Evaluates the expression.
     * @return The result of the expression.
     */
    public double getResult()
    {
        eval();
        return values.pop();
    }

    /**
     * Clears the stacks.
     */
    public void clear()
    {
        operations.clear();
        values.clear();
    }

    /**
     * Evaluates the expression.
     */
    private void eval()
    {
        String[] split = raw.split(" ");

        for (String s : split)
        {
            switch (s)
            {
                case "(":
                    break;
                case ")":
                    evalValues();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "pow2":
                case "sqrt":
                    operations.push(s);
                    break;
                default:
                    values.push(Double.parseDouble(s));
                    break;
            }
        }
    }

    /**
     * Evaluates the values.
     */
    private void evalValues()
    {
        String op   = operations.pop();
        double val  = values.pop();

        switch (op)
        {
            case "+":
                val += values.pop();
                break;
            case "-":
                val -= values.pop();
                break;
            case "*":
                val *= values.pop();
                break;
            case "/":
                val /= values.pop();
                break;
            case "pow2":
                val = Math.pow(val, 2);
                break;
            case "sqrt":
                val = Math.sqrt(val);
                break;
        }

        values.push(val);
    }

}