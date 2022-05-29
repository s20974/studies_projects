package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.io.File;
import java.awt.*;
import java.awt.Image;
import org.json.*;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class CountryTable {
    JTable table;
    public CountryTable()  {
        try{

            DefaultTableModel model = new DefaultTableModel();
            table = new JTable(model);

            model.addColumn("Name");
            model.addColumn("Capital");
            model.addColumn("Population");
//            model.addColumn("Flag");

            table.getColumn("Name").setCellRenderer(new LabelRendar());
            table.getColumn("Capital").setCellRenderer(new LabelRendar());
            table.getColumn("Population").setCellRenderer(new LabelRendar());
//            table.getColumn("Flag").setCellRenderer(new LabelRendar());

           JLabel populationColor = null;
           JLabel name = null;
           JLabel capital = null;
            URLRader rdr = new URLRader();
            JSONArray jsonArray = rdr.render();
            int row = 0;
           for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                if (item.getInt("population") > 2000000){
                    Icon icon = new ImageIcon(image(item.getString("flag")));
                    name = new JLabel("<html><font color='red'; style='font-weight: bold;'>"+item.getString("name")+"</font></html>", icon, JLabel.CENTER);
                    capital = new JLabel("<html><font color='red'; style='font-weight: bold;'>"+item.getString("capital")+"</font></html>", JLabel.CENTER);
                    populationColor = new JLabel("<html><font color='red'; style='font-weight: bold;'>"+item.getInt("population")+"</font></html>", JLabel.CENTER);
                    model.addRow(new Object[]{name, capital, populationColor});
                }   else {
                    Icon icon = new ImageIcon(image(item.getString("flag")));
                    name = new JLabel("<html><font style='font-weight: normal;'>"+item.getString("name")+"</font></html>", icon, JLabel.CENTER);
                    capital = new JLabel("<html><font style='font-weight: normal;'>"+item.getString("capital")+"</font></html>", JLabel.CENTER);
                    populationColor = new JLabel("<html><font style='font-weight: normal;'>"+item.getInt("population")+"</font></html>", JLabel.CENTER);
                    model.addRow(new Object[]{name, capital, populationColor});
                }
                row ++;
            }
        }   catch (Exception e){
        }
    }

    public BufferedImage image(String path){
        try{

            MyTranscoder imageTranscoder = new MyTranscoder();

            imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(30));
            imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(30));

            TranscoderInput input = new TranscoderInput(path);
            imageTranscoder.transcode(input, null);

            BufferedImage image = imageTranscoder.getImage();

            return image;
        }   catch (Exception exp) {
        }
        return null;
    }

    public JTable create(){
        return table;
    }

}


