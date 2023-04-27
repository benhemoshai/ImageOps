import java.awt.Color;

/* Demonstrates the greyscaling and the morphing service of Instush.java.
 * The program recieves two command-line arguments: the name of a PPM file
 * that represents the source image (a string) and the number of morphing steps (an int).
 */

public class Editor4 {
    public static void main(String[] args) {
        String fileName = args[0];
        Color[][] a = Instush.read(fileName);
        Color[][] b = new Color[a.length][a[0].length];
        b = Instush.greyscaled(a);
        int steps = Integer.parseInt(args[1]);
        Instush.morph(a, b, steps);
    }
}
