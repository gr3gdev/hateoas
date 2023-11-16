package com.example.hateoas.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public final class ImageUtils {

    private ImageUtils() {
        // None
    }

    static BufferedImage resizeImage(BufferedImage originalImage, float maxHeight) throws IOException {
        final float delta = maxHeight / originalImage.getHeight();
        final int targetWidth = (int) (originalImage.getWidth() * delta);
        final int targetHeight = (int) (originalImage.getHeight() * delta);
        final BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        final Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static byte[] optimizeImage(byte[] data) throws IOException {
        final BufferedImage inputImage = resizeImage(ImageIO.read(new ByteArrayInputStream(data)), 100f);
        final Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        final ImageWriter writer = writers.next();

        final Path outputImage = Files.createTempFile("opt_", ".png");
        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(outputImage.toFile())) {
            writer.setOutput(outputStream);

            ImageWriteParam params = writer.getDefaultWriteParam();
            params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            params.setCompressionQuality(0.5f);
            writer.write(null, new IIOImage(inputImage, null, null), params);
            writer.dispose();
        }
        final byte[] optimizedData = Files.readAllBytes(outputImage);
        Files.deleteIfExists(outputImage);
        return optimizedData;
    }

}
