package core.engine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// /Users/zacharylederman/Documents/Code/python/pgn/venv/bin/first_game.txt
public class boardReader {
    Scanner s;
    public boardReader(String fileName)throws FileNotFoundException{

        File file = new File(fileName);
        this.s = new Scanner(file);
    }

    public Scanner getS() {
        return s;
    }


}
