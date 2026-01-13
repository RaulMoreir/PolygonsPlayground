package main;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import main.FIPE.cache.APICache;
import main.FIPE.pojo.GenericItem;
import main.exceptions.InvalidShapeFormatException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPlayground {

    final static String Path = "C:\\Users\\raula\\IdeaProjects\\untitled\\src\\test\\resources\\Report.csv";

    public static void main(String[] args) throws InvalidShapeFormatException, IOException, CsvException {

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

