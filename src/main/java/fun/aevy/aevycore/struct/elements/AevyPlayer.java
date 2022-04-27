package fun.aevy.aevycore.struct.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * The representation of a {@link Player}.
 * @since 1.0
 * @author Sorridi
 */
@Getter
@AllArgsConstructor
public class AevyPlayer
{
    private final Player player;
}
