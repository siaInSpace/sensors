package sia20.senosrs.mpu9250;

import java.io.*;
import java.util.ArrayList;

public class Tester {

    private MPU9250 mpu;
    private MPU9250Aceelerometer accelerometer;
    private MPU9250Gyroscope gyroscope;
    private MPU9250Temp temp;
    private Tester(){
        this.mpu = new MPU9250();
        this.accelerometer = new MPU9250Aceelerometer();
        this.gyroscope = new MPU9250Gyroscope();
        this.temp = new MPU9250Temp();
    }

    private void standardSetup(){
        accelerometer.configure(MPU9250Aceelerometer.SCALE.SIXTEEN, MPU9250Aceelerometer.FChoice.ZERO, 0);
        gyroscope.configure(MPU9250Gyroscope.SCALE.DOUBLE, MPU9250Gyroscope.FChoice.THREE, 4);
    }

    private void test(){
        //String[] dataLabels = {"AccelXH", "AccelXL", "AccelYH", "AccelYL", "AccelZH", "AccelZL", "TempH", "TempL", "GyroXH", "GyroXL", "GyroYH", "GyroYL", "GyroZH", "GyroZL"};
        /*for (int i = 0; i < data.length; i++) {
            System.out.println(dataLabels[i] + ": " + data[i]);
        }*/
        String[] dataLabelsHalf = {"AccelX", "AccelY", "AccelZ", "Temp", "GyroX", "GyroY", "GyroZ"};
        byte[] data = mpu.readSensors();
        for (int i = 0; i < dataLabelsHalf.length; i++) {
            System.out.println(dataLabelsHalf[i] + ": " + ((data[i] << 8 | 0xF) & data[i + 1]));
        }
    }

    void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Double[][] read(){
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        long timeZero = System.nanoTime();
        try {
            while (System.in.available() == 0){
                ArrayList<Double> tempData = new ArrayList<>();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                double newTemp = temp.readTemp();
                double[] gyro = gyroscope.readGyro();
                tempData.add((double)(System.nanoTime()-timeZero));
                for (double tempGyro : gyro) {
                    tempData.add(tempGyro);
                }
                System.out.println("Temp: " + temp);
                System.out.println("Gyro");
                System.out.println("\tx: " + gyro[0]);
                System.out.println("\ty: " + gyro[1]);
                System.out.println("\tz: " + gyro[2]);
                sleep(20);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double[][] doneData = data.toArray(new Double[data.size()][]);
        return doneData;
    }
    private void save(Double[][] data){
        FileWriter fw = null;
        try {
            fw = new FileWriter("./data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Double[] oneRead : data) {
            for (Double oneData: oneRead) {
                try {
                    fw.write(oneData.toString() + ", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args){

        final DHT11 dht = new DHT11();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dht.getData(7);
        }
            System.out.println("Done!!");
        /*
        Tester test = new Tester();
        System.out.println("Hello space!");
        //test.test();
        test.standardSetup();
        Double[][] data = test.read();
        */
    }
}
