package fun.aevy.aevycore.utils;

import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;

/**
 * Utility class for annotations.
 */
public class AnnotationUtils
{

    /**
     * Scans the package of the plugin for classes with the specified annotation.
     * @param annotationClass   The annotation to scan for.
     * @param action            The action to perform on the classes.
     * @param javaPlugin        The plugin to scan.
     */
    @SneakyThrows
    public static void forEachAnnotation(
            Class<? extends Annotation> annotationClass,
            Action action,
            JavaPlugin javaPlugin
    ) {
        Class<?> mainClass  = javaPlugin.getClass();

        File file   = new File(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath());
        URL url     = file.toURI().toURL();

        Reflections reflections = new Reflections(url);

        for (Class<?> c : reflections.getTypesAnnotatedWith(annotationClass))
        {
            action.expression(c.getAnnotation(annotationClass), c);
        }
    }

    public interface Action
    {
        void expression(@NotNull Annotation annotation, Class<?> classFound);
    }

}