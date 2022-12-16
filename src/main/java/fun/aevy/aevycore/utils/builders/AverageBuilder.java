package fun.aevy.aevycore.utils.builders;

import lombok.Getter;

/**
 * Monitors averages of execution times. Lightweight.
 * @author Sorridi
 * @since 1.8
 */
public class AverageBuilder
{
    private final int maxCount;

    private int count   = 0;
    private double sum  = 0;

    @Getter
    private double start, end;

    public AverageBuilder(int maxCount)
    {
        this.maxCount = maxCount;
    }

    /**
     * Sets the start time.
     */
    public void setStart()
    {
        start = System.nanoTime();
    }

    /**
     * Sets the end time and adds the average to the sum.
     */
    public void setEnd()
    {
        end = System.nanoTime() - start;
        add(end);
    }

    /**
     * Adds a new value to the average.
     * @param value The value to add.
     */
    public void add(double value)
    {
        sum     += count != maxCount ? value : -sum;
        count   += count != maxCount ? 1 : -count;
    }

    /**
     * Gets the average in milliseconds.
     * @return Average in milliseconds.
     */
    public double get()
    {
        return get(1_000_000);
    }

    /**
     * Gets the average in the specified unit.
     * @param div The unit to divide by.
     * @return Average in the specified unit.
     */
    public double get(double div)
    {
        return sum / div / count;
    }

}