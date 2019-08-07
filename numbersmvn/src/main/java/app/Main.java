package app;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String fileName = "db.txt";
    public static File file = new File(fileName);

    public static Long smallest;
    public static Long largest;
    public static Double average;
    public static ArrayList<Double> digitsStorage;

    public static void main(String[] args) throws IOException {
    init();
    getStats();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String inputLine = bufferedReader.readLine();

        while (!inputLine.equals("exit")){

            if (inputLine.equals("stats")){
                getStats();
            } else {

                if (inputLine.contains(",")) {
                    String[] inputRaw = inputLine.split(",");

                    for (String s : inputRaw) {
                        if (s != null) {
                            s= s.trim();
                            if (Long.parseLong(s) < smallest) {
                                smallest = new Long(s);
                            }

                            if (Long.parseLong(s) > largest) {
                                largest = new Long(s);
                            }

                            digitsStorage.add(new Double((s)));
                            average = getAverage(digitsStorage);
                        }
                    }

                } else {
                    Long v = 0L;
                    try {
                        if (inputLine!=null){
                            inputLine=inputLine.trim();
                            v = Long.parseLong(inputLine);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (v < smallest) {
                        smallest = v;
                    }

                    if (v > largest) {
                        largest = new Long(v);
                    }
                    digitsStorage.add(new Double((v)));
                    average = getAverage(digitsStorage);
                }
                writeResultToFile();
            }
            writeResultToFile();
            inputLine = bufferedReader.readLine();
        }
        System.exit(1);

    }

    public static void writeResultToFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        if (smallest!=null && largest!=null){
            bufferedWriter.write(smallest.toString()+"\n"+largest.toString()+"\n"+average.toString()+"\n"+arrayToString(digitsStorage));
            bufferedWriter.close();
        }
    }

    public static void getStats(){
        System.out.println("Smallest: " + smallest.toString());
        System.out.println("Largest: " + largest.toString());
        System.out.println("Average: " + average.toString());
        System.out.println("digstorage: " + digitsStorage.toString());
    }

    public static void init(){

        if (digitsStorage==null){
            digitsStorage = new ArrayList<>();
        }

        if (!file.exists()){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                bufferedWriter.write("0\n0\n0\nempty");
                bufferedWriter.close();

                smallest=0L;
                largest=0L;
                average=0D;
                digitsStorage = new ArrayList<>();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                if (bufferedReader.readLine().equals("0") && bufferedReader.readLine().equals("0") && !bufferedReader.readLine().equals("abc") && bufferedReader.equals("empy")){
                    smallest=0L;
                    largest=0L;
                    average=0D;
                    digitsStorage = new ArrayList<>();
                    bufferedReader.close();
                }
                bufferedReader = new BufferedReader(new FileReader(fileName));
                for (int i=0; i<4; i++){
                    String raw = bufferedReader.readLine();
                    if (raw!=null){
                        switch (i){
                            case 0: smallest = Long.parseLong(raw);
                                break;
                            case 1: largest = Long.parseLong(raw);
                                break;
                            case 2: average = Double.parseDouble(raw);
                                break;
                            case 3:
                                if (!raw.equals("empty")){
                                    digitsStorage = (ArrayList<Double>) stringToArray(raw);
                                }
                                break;
                        }
                    } else {
//                        smallest=0L;
//                        largest=0L;
//                        average=0D;
//                        digitsStorage = new ArrayList<>();
//                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static Double getAverage(List<Double> list){
        Double summ = 0D;
        if (list!=null){
            for (Double aDouble : list) {
                summ+=aDouble;
            }
        }
        return summ/list.size();
    }

    public static void setVariable(int lineNumber, String data) throws IOException {
        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(lineNumber - 1, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public static <T> String arrayToString(List<T> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (T t : list) {
            stringBuilder.append(t.toString());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static List stringToArray(String input){
        ArrayList res = new ArrayList<>();
        String[] rawArr = input.split(" ");
        for (String s : rawArr) {
           try {
               res.add(new Double(s));
           } catch (Exception e){}
        }
        return res;
    }

}
