package com.oleh;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PatternWallpaper {

    public static void main(String[] args) {
        int red = 15539236;
        try {
            // retrieve image
            BufferedImage bi = new BufferedImage(1920, 4, BufferedImage.TYPE_INT_RGB);

            int x = 960, x2 = 961;
            int step = 2;
            bi.setRGB(x, 0, red);
            bi.setRGB(x, 1, red);
            bi.setRGB(x, 2, red);
            bi.setRGB(x, 3, red);
            bi.setRGB(x2, 0, red);
            bi.setRGB(x2, 1, red);
            bi.setRGB(x2, 2, red);
            bi.setRGB(x2, 3, red);
            int stepInc = 1;
            while(x > 0 && x2 < 1920) {
                x-=step;
                x2+=step;
                step+=stepInc;
                stepInc++;
                if (x > 0 && x2 < 1920) {
                    bi.setRGB(x, 0, red);
                    bi.setRGB(x, 1, red);
                    bi.setRGB(x, 2, red);
                    bi.setRGB(x, 3, red);
                    bi.setRGB(x2, 0, red);
                    bi.setRGB(x2, 1, red);
                    bi.setRGB(x2, 2, red);
                    bi.setRGB(x2, 3, red);
                }
            }

            File outputfile = new File("saved.png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
        }
    }
}
