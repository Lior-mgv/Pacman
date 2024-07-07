package Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class HighScores implements Serializable{
    private ArrayList<HighScoresEntry> highScoresList;

    public HighScores() {
        this.highScoresList = new ArrayList<>();
    }

    public void addEntry(HighScoresEntry newEntry) {
        for (var entry : highScoresList) {
            if(entry.getName().equals(newEntry.getName())){
                if(newEntry.getScore() > entry.getScore())
                    highScoresList.set(getHighScoresList().indexOf(entry),newEntry);
                return;
            }
        }
        highScoresList.add(newEntry);
    }

    public void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("scores.txt"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HighScores loadHighScores() {
        HighScores highScores = new HighScores();
        Path path = Path.of("scores.txt");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            return highScores;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("scores.txt"))) {
            File file = new File("scores.txt");
            if (file.length() != 0) {
                highScores = (HighScores) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return highScores;
    }
    public void sortHighScores(){
        for (int i = 0; i < highScoresList.size(); i++) {
            HighScoresEntry max = highScoresList.get(i);
            for (int j = i; j < highScoresList.size(); j++) {
                var curScore = highScoresList.get(j).getScore();
                if(curScore > max.getScore()) max = highScoresList.get(j);
            }
            highScoresList.set(highScoresList.indexOf(max), highScoresList.get(i));
            highScoresList.set(i, max);
        }
    }

    public ArrayList<HighScoresEntry> getHighScoresList() {
        return highScoresList;
    }
}
