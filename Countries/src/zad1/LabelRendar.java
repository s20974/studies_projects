package zad1;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;


public class LabelRendar implements TableCellRenderer {
    public static int height = 50;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        table.setRowHeight(this.height);
        return (Component) value;
    }
}