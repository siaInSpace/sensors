package sia20.senosrs.mpu9250;

import java.io.IOException;

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
        gyroscope.configure(MPU9250Gyroscope.SCALE.DOUBLE, MPU9250Gyroscope.FChoice.ONE, 0);
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

    public static void main(String[] args){
        Tester test = new Tester();
        System.out.println("Hello space!");
        //test.test();
        test.standardSetup();
        try {
            while (System.in.available() == 0){
                double temp = test.temp.readTemp();
                double[] gyro = test.gyroscope.readGyro();
                System.out.println("Temp: " + temp);
                System.out.println("Gyro");
                System.out.println("\tx: " + gyro[0]);
                System.out.println("\ty: " + gyro[1]);
                System.out.println("\tz: " + gyro[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
