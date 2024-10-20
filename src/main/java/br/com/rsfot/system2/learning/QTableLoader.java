package br.com.rsfot.system2.learning;

import java.io.*;
import java.util.List;
import java.util.Map;

public class QTableLoader {
    private Map<List<Integer>, Map<String, Double>> qTable;
    private Map<List<Integer>, List<Double>> qTableV2;

    public void loadQTable(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            qTable = (Map<List<Integer>, Map<String, Double>>) objectInputStream.readObject();
        }
    }

    public void loadQTableV2(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            qTableV2 = (Map<List<Integer>, List<Double>>) objectInputStream.readObject();
        }
    }

    public Map<List<Integer>, Map<String, Double>> getQTable() {
        return qTable;
    }

    public Map<List<Integer>, List<Double>> getQTableV2() {
        return qTableV2;
    }
}