package main;

import com.opencsv.exceptions.CsvException;
import main.exceptions.InvalidShapeFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPlayground {

    final static String Path = "C:\\Users\\raula\\IdeaProjects\\untitled\\src\\test\\resources\\Report.csv";

    public static void main(String[] args) throws InvalidShapeFormatException, IOException, CsvException {
        for (int i = 1; i <= 4; i++){
            System.out.println(i);
        }
    }

    private static List<String[]> createCsvDataSimple() {
        String[] header = {"id", "name", "address", "phone"};
        String[] record1 = {"1", "first name", "address 1", "11111"};
        String[] record2 = {"2", "second name", "address 2", "22222"};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        list.add(record2);

        return list;
}
}

