import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.lang.annotation.Annotation;

public class ReflectionApp {

    public static void main(String[] args) {
        String folderPath = "D:\\Progrramare-avansata\\Programare-avansata\\Lab12\\src\\test\\java\\classes";

        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Folderul specificat nu exista sau nu este un director valid: " + folderPath);
            return;
        }

        try {
            //URLClassLoader care indică spre folderul extern
            URL url = folder.toURI().toURL();
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{url});

            List<Class<?>> annotationTypes = new ArrayList<>();
            List<Class<?>> publicClasses = new ArrayList<>();

            // Iteram prin toate fișierele din folder
            for (File file : folder.listFiles()) {
                if (file.getName().endsWith(".class")) {
                    String className = file.getName().replace(".class", "");

                    // incarcam clasa folosind URLClassLoader
                    Class<?> clazz = classLoader.loadClass(className);

                    //fisiere care reprezinta adnotari
                    if (clazz.isAnnotation()) {
                        annotationTypes.add(clazz);
                        System.out.println("Adnotare gasita: " + clazz.getName());
                    }
                    else if (Modifier.isPublic(clazz.getModifiers())) {
                        publicClasses.add(clazz);
                        System.out.println("Clasa publica gasita: " + clazz.getName());
                    }
                }
            }

            for (Class<?> clazz : publicClasses) {
                System.out.println("\nPrototip pentru clasa: " + clazz.getName());

                Object instance = null;

                for (Method method : clazz.getDeclaredMethods()) {
                    System.out.println("    Metoda: " + method.toString());

                    boolean hasIdentifiedAnnotation = false;
                    for (Annotation methodAnn : method.getAnnotations()) {
                        if (annotationTypes.contains(methodAnn.annotationType())) {
                            hasIdentifiedAnnotation = true;
                            break;
                        }
                    }

                    if (hasIdentifiedAnnotation) {
                        if (instance == null && !Modifier.isStatic(method.getModifiers())) {
                            instance = clazz.getDeclaredConstructor().newInstance();
                        }

                        if (method.getParameterCount() == 0) {
                            System.out.println("       execut metoda fara argumente: " + method.getName());
                            method.invoke(instance);
                        }
                        else if (method.getParameterCount() == 1 &&
                                (method.getParameterTypes()[0] == int.class || method.getParameterTypes()[0] == Integer.class)) {
                            int mockValue = 42; // valoarea mock
                            System.out.println("       execut metoda CU un argument intreg (mock value=" + mockValue + "): " + method.getName());
                            method.invoke(instance, mockValue);
                        }
                    }
                }
            }

            classLoader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}