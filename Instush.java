import java.awt.Color;

/**
 * A library of image processing functions.
 */
public class Instush {

	public static void main(String[] args) {
		String fileName = args[0];
		Color[][] a = read(fileName);
		String fileName1 = args[1];
		Color[][] b = read(fileName1);
		b = scaled(b, a[0].length, a.length);
		morph(a, b, 3);
		// Color c1 = new Color(100, 40, 100);
		// Color c2 = new Color(200, 20, 40);
		// System.out.println((blend(c1, c2, 0.75)));
	}

	/**
	 * Returns an image created from a given PPM file.
	 * SIDE EFFECT: Sets standard input to the given file.
	 * 
	 * @return the image, as a 2D array of Color values
	 */
	public static Color[][] read(String filename) {
		StdIn.setInput(filename);
		// Reads the PPM file header (ignoring some items)
		StdIn.readString();
		int numRows = StdIn.readInt();
		int numCols = StdIn.readInt();
		StdIn.readInt();
		// Creates the image
		Color[][] image = new Color[numCols][numRows];
		// Reads the RGB values from the file, into the image.
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and
		// makes pixel (i,j) refer to that object.

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				image[i][j] = new Color(StdIn.readInt(), StdIn.readInt(), StdIn.readInt());
			}

		}

		return image;
	}

	/**
	 * Prints the pixels of a given image.
	 * Each pixel is printed as a triplet of (r,g,b) values.
	 * For debugging purposes.
	 * 
	 * @param image - the image to be printed
	 */
	public static void print(Color[][] image) {
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				println(image[i][j]);
			}
			System.out.println();
		}

	}

	public static void println(Color c) {
		System.out.print("(");
		System.out.printf("%3s", c.getRed()); // Prints the color's red component
		System.out.printf("%4s", c.getGreen()); // Prints the color's green component
		System.out.printf("%4s", c.getBlue()); // Prints the color's blue component
		System.out.print(") ");

	}

	/**
	 * Returns an image which is the horizontally flipped version of the given
	 * image.
	 * 
	 * @param image - the image to flip
	 * @return the horizontally flipped image
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] Horizon = new Color[image.length][image[0].length];

		for (int i = 0; i < Horizon.length; i++) {
			int p = Horizon[0].length - 1;
			for (int j = 0; j < Horizon[0].length; j++) {
				Horizon[i][j] = image[i][p];
				p--;
			}
		}
		return Horizon;
	}

	/**
	 * Returns an image which is the vertically flipped version of the given image.
	 * 
	 * @param image - the image to flip
	 * @return the vertically flipped image
	 */
	public static Color[][] flippedVertically(Color[][] image) {
		Color[][] Verti = new Color[image.length][image[0].length];
		int p = Verti.length - 1;
		for (int i = 0; i < Verti.length; i++) {
			for (int j = 0; j < Verti[0].length; j++) {
				Verti[i][j] = image[p][j];
			}
			p--;
		}
		return Verti;
	}

	/**
	 * Returns the average of the RGB values of all the pixels in a given image.
	 * 
	 * @param image - the image
	 * @return the average of all the RGB values of the image
	 */
	public static double average(Color[][] image) {
		return 0.0;
	}

	/**
	 * Returns the luminance value of a given pixel. Luminance is a weighted average
	 * of the RGB values of the pixel, given by 0.299 * r + 0.587 * g + 0.114 * b.
	 * Used as a shade of grey, as part of the greyscaling process.
	 * 
	 * @param pixel - the pixel
	 * @return the greyscale value of the pixel, as a Color object
	 *         (r = g = b = the greyscale value)
	 */
	public static Color luminance(Color pixel) {
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();

		int lum1 = (int) (0.299 * r + 0.587 * g + 0.114 * b);
		Color lum = new Color(lum1, lum1, lum1);
		return lum;
	}

	/**
	 * Returns an image which is the greyscaled version of the given image.
	 * 
	 * @param image - the image
	 * @return rhe greyscaled version of the image
	 */
	public static Color[][] greyscaled(Color[][] image) {
		Color[][] grey = new Color[image.length][image[0].length];

		for (int i = 0; i < grey.length; i++) {
			for (int j = 0; j < grey[0].length; j++) {
				grey[i][j] = new Color(luminance(image[i][j]).getRed(), luminance(image[i][j]).getGreen(),
						luminance(image[i][j]).getBlue());
			}
		}
		return grey;
	}

	/**
	 * Returns an image which is the scaled version of the given image.
	 * The image is scaled (resized) to be of the given width and height.
	 * 
	 * @param image  - the image
	 * @param width  - the width of the scaled image
	 * @param height - the height of the scaled image
	 * @return - the scaled image
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] sc = new Color[height][width];
		for (int i = 0; i < sc.length; i++) {
			for (int j = 0; j < sc[0].length; j++) {
				sc[i][j] = image[i * image.length / height][j * image[0].length / width];
			}
		}
		return sc;
	}

	/**
	 * Returns a blended color which is the linear combination of two colors.
	 * Each r, g, b, value v is calculated using v = (1 - alpha) * v1 + alpha * v2.
	 * 
	 * @param pixel1 - the first color
	 * @param pixel2 - the second color
	 * @param alpha  - the linear combination parameter
	 * @return the blended color
	 */
	public static Color blend(Color c1, Color c2, double alpha) {

		int r = (int) ((1 - alpha) * c1.getRed() + alpha * c2.getRed());
		int g = (int) ((1 - alpha) * c1.getGreen() + alpha * c2.getGreen());
		int b = (int) ((1 - alpha) * c1.getBlue() + alpha * c2.getBlue());
		Color blend = new Color(r, g, b);
		return blend;
	}

	/**
	 * Returns an image which is the blending of the two given images.
	 * The blending is the linear combination of (1 - alpha) parts the
	 * first image and (alpha) parts the second image.
	 * The two images must have the same dimensions.
	 * 
	 * @param image1 - the first image
	 * @param image2 - the second image
	 * @param alpha  - the linear combination parameter
	 * @return - the blended image
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blend = new Color[image1.length][image1[0].length];

		for (int i = 0; i < blend.length; i++) {
			for (int j = 0; j < blend[0].length; j++) {
				blend[i][j] = blend(image1[i][j], image2[i][j], alpha);

			}
		}
		return blend;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * The target image is an image which is scaled to be a version of the target
	 * image, scaled to have the width and height of the source image.
	 * 
	 * @param source - source image
	 * @param target - target image
	 * @param n      - number of morphing steps
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		for (int i = 0; i <= n; i++) {
			show(blend(target, source, ((n - i) / n)));
		}
	}

	/**
	 * Renders (displays) an image on the screen, using StdDraw.
	 * 
	 * @param image - the image to show
	 */
	public static void show(Color[][] image) {
		StdDraw.setCanvasSize(image[0].length, image.length);
		int width = image[0].length;
		int height = image.length;
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		StdDraw.show(25);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the color of the pixel
				StdDraw.setPenColor(image[i][j].getRed(),
						image[i][j].getGreen(),
						image[i][j].getBlue());
				// Draws the pixel as a tiny filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}
