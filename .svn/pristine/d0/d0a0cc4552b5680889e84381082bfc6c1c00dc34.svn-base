import java.awt.Color;
import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    private int width;
    private int height;
    private double[][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new java.lang.NullPointerException();
        this.picture = new Picture(picture);
        width = picture.width();
        height = picture.height();
        energy = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energy[i][j] = energy(i, j);
            }
        }
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
            throw new java.lang.IndexOutOfBoundsException();
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
            return 1000;

        Color color = picture.get(x - 1, y);
        int[] recordl = { color.getRed(), color.getGreen(), color.getBlue() };

        color = picture.get(x + 1, y);
        int[] recordr = { color.getRed(), color.getGreen(), color.getBlue() };

        color = picture.get(x, y - 1);
        int[] recordu = { color.getRed(), color.getGreen(), color.getBlue() };

        color = picture.get(x, y + 1);
        int[] recordd = { color.getRed(), color.getGreen(), color.getBlue() };

        int total = 0;
        for (int i = 0; i < 3; i++) {
            total += Math.pow(recordl[i] - recordr[i], 2);
            total += Math.pow(recordu[i] - recordd[i], 2);
        }

        return Math.sqrt(total);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        double[] current = new double[height], last = new double[height];
        int[][] edgeto = new int[width][height];

        for (int i = 0; i < width; i++) {

            for (int temp = 0; temp < height; temp++)
                current[temp] = Double.MAX_VALUE;

            for (int j = 0; j < height; j++) {
                for (int k = -1; k < 2; k++) {
                    if (j + k >= 0 && j + k < height) {
                        if (last[j] + energy[i][j + k] < current[j + k]) {
                            current[j + k] = last[j] + energy[i][j + k];
                            edgeto[i][j + k] = j;
                        }
                    }
                }
            }

            last = Arrays.copyOf(current, height);
        }

        int colRecord = -1;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < height; i++) {
            if (last[i] < min) {
                min = last[i];
                colRecord = i;
            }
        }

        Stack<Integer> route = new Stack<Integer>();
        while (route.size() < width) {
            route.push(colRecord);
            colRecord = edgeto[width - route.size()][colRecord];
        }

        int[] routearray = new int[width];
        for (int i = 0; i < width; i++)
            routearray[i] = route.pop();
        return routearray;

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[] current = new double[width], last = new double[width];
        int[][] edgeto = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int temp = 0; temp < width; temp++)
                current[temp] = Double.MAX_VALUE;

            for (int j = 0; j < width; j++) {
                for (int k = -1; k < 2; k++) {
                    if (j + k >= 0 && j + k < width) {
                        if (last[j] + energy[j + k][i] < current[j + k]) {
                            current[j + k] = last[j] + energy[j + k][i];
                            edgeto[i][j + k] = j;
                        }
                    }
                }
            }

            last = Arrays.copyOf(current, width);
        }

        int colRecord = -1;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < width; i++) {
            if (last[i] < min) {
                min = last[i];
                colRecord = i;
            }
        }

        Stack<Integer> route = new Stack<Integer>();
        while (route.size() < height) {
            route.push(colRecord);
            colRecord = edgeto[height - route.size()][colRecord];
        }

        int[] routearray = new int[height];
        for (int i = 0; i < height; i++)
            routearray[i] = route.pop();
        return routearray;

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width)
            throw new java.lang.IllegalArgumentException();
        if (picture == null)
            throw new java.lang.NullPointerException();
        if (height <= 1)
            throw new java.lang.IllegalArgumentException();

        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height - 1)
                throw new java.lang.IllegalArgumentException();
            if (seam[i] < minVal)
                minVal = seam[i];
            if (seam[i] > maxVal)
                maxVal = seam[i];
        }

        minVal = Math.max(minVal - 1, 0);
        maxVal = Math.min(maxVal, height - 2);

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1)
                throw new java.lang.IllegalArgumentException();

        }

        double[][] tempenergy = new double[width][height - 1];
        Picture tempPicture = new Picture(width, height - 1);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (j < seam[i]) {
                    tempenergy[i][j] = energy[i][j];
                    tempPicture.set(i, j, picture.get(i, j));
                }
                if (j > seam[i]) {
                    tempenergy[i][j - 1] = energy[i][j];
                    tempPicture.set(i, j - 1, picture.get(i, j));
                }
            }
        }

        height = height - 1;
        picture = tempPicture;

        for (int col = 0; col < width; col++) {
            for (int row = minVal; row <= maxVal; row++) {
                tempenergy[col][row] = energy(col, row);
            }
        }

        energy = tempenergy;

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height)
            throw new java.lang.IllegalArgumentException();
        if (picture == null)
            throw new java.lang.NullPointerException();
        if (width <= 1)
            throw new java.lang.IllegalArgumentException();

        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width - 1)
                throw new java.lang.IllegalArgumentException();
            if (seam[i] < minVal)
                minVal = seam[i];
            if (seam[i] > maxVal)
                maxVal = seam[i];
        }

        minVal = Math.max(minVal - 1, 0);
        maxVal = Math.min(maxVal, width - 2);

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1)
                throw new java.lang.IllegalArgumentException();
        }

        double[][] tempenergy = new double[width - 1][height];
        Picture tempPicture = new Picture(width - 1, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j < seam[i]) {
                    tempenergy[j][i] = energy[j][i];
                    tempPicture.set(j, i, picture.get(j, i));
                }
                if (j > seam[i]) {
                    tempenergy[j - 1][i] = energy[j][i];
                    tempPicture.set(j - 1, i, picture.get(j, i));
                }
            }
        }

        picture = tempPicture;
        width = width - 1;

        for (int col = minVal; col <= maxVal; col++) {
            for (int row = 0; row < height; row++) {
                tempenergy[col][row] = energy(col, row);
            }
        }

        energy = tempenergy;
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver test = new SeamCarver(picture);
        int[] outcome = test.findHorizontalSeam();
        test.removeHorizontalSeam(outcome);
    }
}
