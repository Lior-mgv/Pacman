package View;

import javax.swing.table.AbstractTableModel;
import java.util.Random;

public class Board extends AbstractTableModel {
    public int[][] model;

    public Board(int[][] model) {
        this.model = model;
    }

    @Override
    public int getRowCount() {
        return model.length;
    }

    @Override
    public int getColumnCount() {
        return model[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return model[rowIndex][columnIndex];
    }

    public int[][] getModel() {
        return model;
    }
}
