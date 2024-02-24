package com.example.transportcompany.utils.fileLogic;

import com.example.transportcompany.models.entities.Transportation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransportationFileLogic {

    private static final String FILE_PATH = "transportations.txt";
    public static void writeTransportationsToFile(List<Transportation> transportations) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (Transportation transportation : transportations) {
                bufferedWriter.write(transportation.getEndPoint());
                bufferedWriter.newLine();
            }

            System.out.println("Successfully wrote the data in a file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTransportationsFromFile() {
        List<Transportation> loadedTransportations = new ArrayList<>();

        try (FileReader fileReader = new FileReader(FILE_PATH);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("Successfully read the date from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
