package Model;

import java.util.Random;

public class MazeGenerator {
    private final int rows;
    private final int cols;
    private static final double GAP_PROBABILITY = 0.33;

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public int[][] generate() {
        var arr = new int[rows][cols];
        var rnd = new Random();
        // iterate levels of the maze starting from the border rectangle to the center
        for(int lvl=0; lvl<(double)cols/2 && lvl<(double)rows/2; lvl++) {
            var innerRows = rows-lvl*2;
            var innerCols = cols-lvl*2;
            boolean corridor = lvl%2==1; // make every second inner rectangle a corridor (contains only free spaces, no obstacles)
            var centerRect = (lvl+1)*2>=Math.min(rows,cols); // the center rectangle should be filled in with obstacles to avoid wrong corridor configuration
            var gap = -2; // index of the last gap in the wall rectangle.

            // clock-wise traversal of the inner rectangle starting from the top-left corner
            int r = lvl, c=lvl;
            for(int i=0; i<(innerRows-1)*2 + (innerCols-1)*2; i++) {
                var pt = new RectPoint(rows, cols, lvl, r, c);

                if (lvl==0) {
                    // maze borders have a fixed cell configuration
                    arr[r][c] = getBorderCell(pt).getValue();
                }
                else if (corridor) {
                    // corridor rectangles are empty, except for the center rectangle which should be filled with obstacles
                    arr[r][c] = centerRect ? CellTypes.OBSTACLE.getValue() : CellTypes.FREE.getValue();
                }
                else {
                    var dice = rnd.nextInt((int)(1/GAP_PROBABILITY));
                    if (pt.corner() || dice != 1 || i - gap < 2 || centerRect) {
                        arr[r][c] = CellTypes.OBSTACLE.getValue();
                    } else {
                        arr[r][c] = CellTypes.FREE.getValue();
                        gap = i;
                    }
                }

                // move to the next point of the inner rectangle
                if (pt.top() && !pt.right())
                    c++;
                else if (pt.right() && !pt.bottom())
                    r++;
                else if (pt.bottom() && !pt.left())
                    c--;
                else if (pt.left() && !pt.top())
                    r--;
            }

            // make sure that there's at least one gap in the inner rectangle to make all corridor cells accessible
            if (lvl>0 && !corridor && !centerRect && gap<0) {
                arr[r][c+1] = CellTypes.FREE.getValue();
            }
        }

        return arr;
    }

    private class RectPoint {
        private final int cols;
        private final int rows;
        private final int lvl;
        private final int r;
        private final int c;

        public RectPoint(int rows, int cols, int lvl, int r, int c) {
            this.cols = cols;
            this.rows = rows;
            this.lvl = lvl;
            this.c = c;
            this.r = r;
        }
        public boolean top() {
            return r == lvl;
        }
        public boolean right() {
            return c == cols - lvl - 1;
        }
        public boolean bottom() {
            return r == rows - lvl - 1;
        }
        public boolean left() {
            return c == lvl;
        }
        public boolean corner() {
            return top()&&left() || top()&&right() || bottom()&&right() || bottom()&&left();
        }
    }

    private CellTypes getBorderCell(RectPoint pt) {
        if (pt.corner()) {
            return CellTypes.EMPTY;
        }
        else if (pt.top()) {
            return CellTypes.TOP_WALL;
        }
        else if (pt.right()) {
            return CellTypes.RIGHT_WALL;
        }
        else if (pt.bottom()) {
            return CellTypes.BOTTOM_WALL;
        }
        else {
            return CellTypes.LEFT_WALL;
        }

    }
}
