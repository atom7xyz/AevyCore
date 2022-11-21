package fun.aevy.aevycore.utils.formatting;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@State(Scope.Benchmark)
public class MessagePropertiesTest {

    private MessageProperties messageProperties;

    @Test
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void replace() {
        messageProperties = new MessageProperties("Hello {player}!");
        messageProperties.replace("{player}", "Sorridi");
        assertEquals("Hello Sorridi!", messageProperties.getActualMessage());
    }

    static List<String> result = List.of("Hello Sorridi!", "HEY Sorridi!", "HEYO Sorridi!");

    @Test
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void testReplace() {
        messageProperties = new MessageProperties(List.of("Hello {player}!", "HEY {player}!", "HEYO {player}!"));
        messageProperties.replace("{player}", "Sorridi");
        assertEquals(result, messageProperties.getActualList());
    }

    @Test
    public void testReplace1() {
    }

    @Test
    public void testReplace2() {
    }

    @Test
    public void withPrefix() {
    }

    @Test
    public void noPrefix() {
    }

    @Test
    public void getMessage() {
    }

    @Test
    public void getMessages() {
    }

    @Test
    public void getPrimitiveMessage() {
    }

    @Test
    public void getPrefix() {
    }

    @Test
    public void getActualMessage() {
    }

    @Test
    public void isPrefixed() {
    }

    @Test
    public void getPrimitiveList() {
    }

    @Test
    public void getActualList() {
    }
}