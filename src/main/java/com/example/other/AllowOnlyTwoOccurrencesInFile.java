package com.example.other;

import com.opencsv.CSVReader;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllowOnlyTwoOccurrencesInFile {

    public static void main(String[] args) {

        List<String> toWrite = new ArrayList<>();
        Path filePath = FileSystems.getDefault().getPath("mock-data");

        try(
                CSVReader reader = new CSVReader(new FileReader(filePath.resolve("mock_departments.csv").toFile()));
                Writer writer = new FileWriter(filePath.resolve("mock_departments_short.csv").toFile());
                ) {


            String[] nextLine;

            while((nextLine = reader.readNext()) != null){
//                for(String token : nextLine) {
//                    System.out.print(token + "\t");
//                }
//                System.out.printf("%n");
                if(Collections.frequency(toWrite, nextLine[0]) < 2) {
                    toWrite.add(nextLine[0]);
                }
            }

//            toWrite.stream().forEach(System.out::println);
//            toWrite.stream().forEach((element) -> System.out.printf("|%s|%n", element));
            System.out.printf("Array size is %d.%n", toWrite.size());
            
            int lineCount = 0;
            
            for (String item : toWrite) {
                ++lineCount;
                if (lineCount < toWrite.size()) {
                    writer.write(item + System.lineSeparator());
                } else {
                    writer.write(item);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

