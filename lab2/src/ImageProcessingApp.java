import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

public class ImageProcessingApp {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    private JFrame frame;
    private JLabel imageLabel;
    private Mat loadedImage;
    private Mat originalImage;

    public ImageProcessingApp() {
        frame = new JFrame("Image Processing");
        imageLabel = new JLabel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        JButton loadImageButton = new JButton("Load Image");
        loadImageButton.addActionListener(e -> loadImage());

        JButton gaussianBlurButton = new JButton("Apply Gaussian Blur");
        gaussianBlurButton.addActionListener(e -> applyGaussianBlur());

        JButton erodeButton = new JButton("Apply Erosion");
        erodeButton.addActionListener(e -> applyErosion());

        JButton dilateButton = new JButton("Apply Dilation");
        dilateButton.addActionListener(e -> applyDilation());

        JButton openButton = new JButton("Apply Opening");
        openButton.addActionListener(e -> applyOpening());

        JButton closeButton = new JButton("Apply Closing");
        closeButton.addActionListener(e -> applyClosing());

        JButton viewOriginalButton = new JButton("View Original Image");
        viewOriginalButton.addActionListener(e -> viewOriginalImage());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadImageButton);
        buttonPanel.add(gaussianBlurButton);
        buttonPanel.add(erodeButton);
        buttonPanel.add(dilateButton);
        buttonPanel.add(openButton);
        buttonPanel.add(closeButton);
        buttonPanel.add(viewOriginalButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");

        File defaultDirectory = new File("D:\\infa\\3cours\\KG\\LAB_KG\\lab2\\img");
        fileChooser.setCurrentDirectory(defaultDirectory);

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "bmp", "gif"));

        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadedImage = Imgcodecs.imread(selectedFile.getAbsolutePath());
            if (loadedImage.empty()) {
                JOptionPane.showMessageDialog(frame, "Image could not be loaded!");
                return;
            }

            originalImage = loadedImage.clone();

            resizeAndDisplayImage(loadedImage);
        }
    }

    private void resizeAndDisplayImage(Mat image) {
        int labelWidth = imageLabel.getWidth();
        int labelHeight = imageLabel.getHeight();

        if (labelWidth == 0 || labelHeight == 0) {
            labelWidth = 800;
            labelHeight = 600;
        }

        Mat resizedImage = new Mat();
        Size size = new Size(labelWidth, labelHeight);
        Imgproc.resize(image, resizedImage, size);

        ImageIcon icon = new ImageIcon(matToBufferedImage(resizedImage));
        imageLabel.setIcon(icon);
    }

    // Гаусс
    private void applyGaussianBlur() {
        if (loadedImage == null || loadedImage.empty()) {
            JOptionPane.showMessageDialog(frame, "Please load an image first!");
            return;
        }

        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(loadedImage, blurredImage, new Size(15, 15), 0);

        resizeAndDisplayImage(blurredImage);
    }

    // Эрозия
    private void applyErosion() {
        if (loadedImage == null || loadedImage.empty()) {
            JOptionPane.showMessageDialog(frame, "Please load an image first!");
            return;
        }

        Mat erodedImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.erode(loadedImage, erodedImage, kernel);

        resizeAndDisplayImage(erodedImage);
    }

    // Дилатация
    private void applyDilation() {
        if (loadedImage == null || loadedImage.empty()) {
            JOptionPane.showMessageDialog(frame, "Please load an image first!");
            return;
        }

        Mat dilatedImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.dilate(loadedImage, dilatedImage, kernel);

        resizeAndDisplayImage(dilatedImage);
    }

    // Размыкание - Opening
    private void applyOpening() {
        if (loadedImage == null || loadedImage.empty()) {
            JOptionPane.showMessageDialog(frame, "Please load an image first!");
            return;
        }

        Mat openedImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(loadedImage, openedImage, Imgproc.MORPH_OPEN, kernel);

        resizeAndDisplayImage(openedImage);
    }

    // Замыкание - Closing
    private void applyClosing() {
        if (loadedImage == null || loadedImage.empty()) {
            JOptionPane.showMessageDialog(frame, "Please load an image first!");
            return;
        }

        Mat closedImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(loadedImage, closedImage, Imgproc.MORPH_CLOSE, kernel);

        resizeAndDisplayImage(closedImage);
    }

    private void viewOriginalImage() {
        if (originalImage == null || originalImage.empty()) {
            JOptionPane.showMessageDialog(frame, "No original image available. Please load an image first!");
            return;
        }

        resizeAndDisplayImage(originalImage);
    }

    // Преобразование Mat (OpenCV) в BufferedImage (для Swing)
    private BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageProcessingApp::new);
    }
}
