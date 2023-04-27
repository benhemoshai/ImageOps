import java.awt.Color;

/**
 * Demonstrates the morphing service of Instush.java.
 * The program recieves three command-line arguments: the name of a PPM file
 * that represents the source image (a string), the name of a PPM file that
 * represents
 * the target image (a string), and the number of morphing steps (an int).
 * For example:
 * java Editor3 cake.ppm ironman.ppm 300
 * If the two images don't have the same dimensions, the program scales the
 * target image
 * to the dimensions of the source image.
 */
public class Editor3 {

	public static void main(String[] args) {
		String fileName = args[0];
		Color[][] a = Instush.read(fileName);
		String fileName1 = args[1];
		Color[][] b = Instush.read(fileName1);
		b = Instush.scaled(b, a[0].length, a.length);
		int steps = Integer.parseInt(args[2]);
		Instush.morph(a, b, steps);
	}
}
