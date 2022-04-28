package fun.aevy.aevycore.utils.builders;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for expressions.
 * @author Sorridi
 * @since 1.3
 */
public interface Lambda
{
    <G> boolean expression(@NotNull G g);
}
