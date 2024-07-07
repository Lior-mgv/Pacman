package Tests;

import Model.MazeGenerator;

public class MazeGeneration {
    public static void main(String[] args) {
        var mazeGenerator = new MazeGenerator(11, 24);
        var maze = mazeGenerator.generate();

        for(int i=0; i<maze.length; i++) {
            for(int j=0; j<maze[0].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}
