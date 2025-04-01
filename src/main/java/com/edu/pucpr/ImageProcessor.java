package com.edu.pucpr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    private static JLabel imageLabel;

    public static void main(String[] args) {
        try {
            File inputFile = selectImageFile();
            if (inputFile == null) {
                System.out.println("Nenhuma imagem selecionada. Saindo...");
                return;
            }

            BufferedImage image = ImageIO.read(inputFile);
            if (image == null) {
                System.out.println("Erro ao carregar a imagem.");
                return;
            }

            String pixelsIniciaisString = JOptionPane.showInputDialog(
                    "Digite as coordenadas do pixel inicial (x, y) separadas por vírgula (0, 0):");

            int[] pixels = getInitialPixels(pixelsIniciaisString);

            System.out.println("Pixel inicial: " + pixels[0] + ", " + pixels[1]);

            String outputFileName = getOutputFileName();
            if (outputFileName == null || outputFileName.trim().isEmpty()) {
                System.out.println("Nome do arquivo inválido. Saindo...");
                return;
            }

            createAndShowGUI(image);
            processImageQueue(image, pixels);

            File outputFile = new File(outputFileName + ".png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Imagem salva como: " + outputFile.getAbsolutePath());

            System.exit(0);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static File selectImageFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione uma imagem");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private static String getOutputFileName() {
        return JOptionPane.showInputDialog("Digite o nome do arquivo de saída (sem extensão):");
    }

    public static int[] getInitialPixels(String initialPixelString) {
        String[] initialPixel = initialPixelString.split(",");
        return new int[] { Integer.parseInt(initialPixel[0].trim()), Integer.parseInt(initialPixel[1].trim()) };
    }
    

    public static void processImageQueue(BufferedImage image, int[] pixels) throws InterruptedException {
        Node initialPixel = new Node(pixels[0], pixels[1], null);

        Queue pixelsQueue = new Queue();
        pixelsQueue.enqueue(initialPixel.x, initialPixel.y);

        while(!pixelsQueue.isEmpty()) {
            Node removedNode = pixelsQueue.dequeue();

            if (removedNode.x >= image.getWidth() || removedNode.x <= 0 || removedNode.y >= image.getHeight() || removedNode.y <= 0) {
                continue;
            } else {
                int pixel = image.getRGB(removedNode.x, removedNode.y);
                Color color = new Color(pixel, true);

                if (color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255) {
                    image.setRGB(removedNode.x, removedNode.y, Color.RED.getRGB());

                    updateImage(image);

                    Thread.sleep(1);

                    pixelsQueue.enqueue(removedNode.x, removedNode.y - 1);
                    pixelsQueue.enqueue(removedNode.x - 1, removedNode.y);
                    pixelsQueue.enqueue(removedNode.x, removedNode.y + 1);
                    pixelsQueue.enqueue(removedNode.x + 1, removedNode.y);
                }
            }

        }
    }

    private static void createAndShowGUI(BufferedImage image) {
        JFrame frame = new JFrame("Imagem Modificada");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(imageLabel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void updateImage(BufferedImage image) {
        SwingUtilities.invokeLater(() -> {
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.repaint();
        });
    }
}
