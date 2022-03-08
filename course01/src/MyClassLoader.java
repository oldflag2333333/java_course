import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * @author oldFlag
 * @since 2022/3/7
 */
public class MyClassLoader extends ClassLoader {


    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = switching(Objects.requireNonNull(getFileContent("Hello.xlass")));
        return defineClass(name, bytes, 0, bytes.length);
    }

    private static byte[] switching(byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) (255 - Byte.toUnsignedInt(bytes[i]));
        }
        return result;
    }

    private static byte[] getFileContent(String name) {
        try (InputStream inputStream = MyClassLoader.class.getClassLoader().getResourceAsStream(name)) {
            int length = inputStream.available();
            byte[] byteArray = new byte[length];
            inputStream.read(byteArray);
            inputStream.close();
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void writeFileContent(String name, byte[] content) {
        Path path = Paths.get(name);
        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.write(content, 0, content.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encode() {
        byte[] fileContent = getFileContent("Hello.class");
        byte[] bytes = switching(fileContent);
        writeFileContent("./src/Hello.xlass", bytes);
    }


    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        encode();
        Class clazz = new MyClassLoader().findClass("Hello");
        Object hello = clazz.newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (!method.getName().startsWith("hello")) continue;
            method.invoke(hello);
        }
    }


}
