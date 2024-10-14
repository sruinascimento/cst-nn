package br.com.rsfot.system2.learning;

import java.io.*;
import java.util.List;
import java.util.Map;

public class QTableLoader {
    private Map<List<Integer>, Map<String, Double>> qTable;

    public void loadQTable(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            qTable = (Map<List<Integer>, Map<String, Double>>) objectInputStream.readObject();
        }
    }

    public void saveQTable(String filePath) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(qTable);
        }
    }

    public Map<List<Integer>, Map<String, Double>> getQTable() {
        return qTable;
    }

    public void setQTable(Map<List<Integer>, Map<String, Double>> qTable) {
        this.qTable = qTable;
    }
}