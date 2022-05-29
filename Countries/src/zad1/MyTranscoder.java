package zad1;

import org.apache.batik.transcoder.image.ImageTranscoder;
import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.TranscoderOutput;

public class MyTranscoder  extends ImageTranscoder {
    private BufferedImage image = null;
    public BufferedImage createImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return image;
    }
    public void writeImage(BufferedImage img, TranscoderOutput out) {
        this.image = image;
    }
    public BufferedImage getImage() {
        return image;
    }
}
