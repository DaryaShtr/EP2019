import java.io.File;

public class Main {
    public static native void Function();
    static
    {
        System.load(new File("").getAbsolutePath()+"\\DLL\\x64\\Debug\\DLL.dll");
    }

    public static void main(String[] args) {
        Function();
    }
}
