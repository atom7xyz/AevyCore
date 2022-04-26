package fun.aevy.aevycore.utils.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfigEntry
{
    private final String path;
    private final Object value;
}
