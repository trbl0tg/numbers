package app;

import java.io.*;

public class Main {

    // TODO: 8/6/19 Implement an avg functionality. Doc the code.

    public static String fileName = "db.txt";
    public static File file = new File(fileName);

    public static Long smallest;
    public static Long largest;
    public static Double average;

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
                }
                writeResultToFile();
            }
            inputLine = bufferedReader.readLine();
        }
        System.exit(1);

    }

    public static void writeResultToFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        if (smallest!=null && largest!=null){
            bufferedWriter.write(smallest.toString()+"\n"+largest.toString()+"\n"+average.toString());
            bufferedWriter.close();
        }
    }

    public static void getStats(){
        System.out.println("Smallest: " + smallest.toString());
        System.out.println("Largest: " + largest.toString());
        System.out.println("Average: " + average.toString());
    }

    public static void init(){
        if (!file.exists()){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                bufferedWriter.write("0\n0\n0");
                bufferedWriter.close();

                smallest=0L;
                largest=0L;
                average=0D;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                for (int i=0; i<3; i++){
                    String raw = bufferedReader.readLine();
                    if (raw!=null){
                        switch (i){
                            case 0: smallest = Long.parseLong(raw);
                                break;
                            case 1: largest = Long.parseLong(raw);
                                break;
                            case 2: average = Double.parseDouble(raw);
                        }
                    } else {
                        smallest=0L;
                        largest=0L;
                        average=0D;
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
