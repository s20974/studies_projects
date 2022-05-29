package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer extends JLabel {
    ImageRenderer(BufferedImage icon){
        setIcon(new ImageIcon(icon));
    }

}