package sia20.senosrs.mpu9250;

public class Tester {

    private MPU9250 mpu;
    private MPU9250Aceelerometer aceelerometer;
    private MPU9250Gyroscope gyroscope;
    private Tester(){
        this.mpu = new MPU9250();
        this.aceelerometer = new MPU9250Aceelerometer();
        this.gyroscope = new MPU9250Gyroscope();
    }
    private void standardSetup(){
        aceelerometer.configure(MPU9250Aceelerometer.SCALE.SIXTEEN, MPU9250Aceelerometer.FChoice.ZERO, 0);
        gyroscope.configure(MPU9250Gyroscope.SCALE.DOUBLE, MPU9250Gyroscope.FChoice.ONE, 0);
    }

    private void test(){
        this.standardSetup();
        String[] dataLabels = {"AccelXH", "AccelXL", "AccelYH", "AccelYL", "AccelZH", "AccelZL", "TempH", "TempL", "GyroXH", "GyroXL", "GyroYH", "GyroYL", "GyroZH", "GyroZL"};
        String[] dataLabesHalf = {"AccelX", "AccelY", "AccelZ", "Temp", "GyroX", "GyroY", "GyroZ"};
        byte[] data = mpu.readSensors();
        for (int i = 0; i < data.length; i++) {
            System.out.println(dataLabels[i] + ": " + data[i]);
        }
        for (int i = 0; i < dataLabesHalf.length; i++) {
            System.out.println(dataLabesHalf[i] + ": " + ((data[i]<<8 |0xF) & data[i+1]));
        }
    }
    public static void main(String[] args){
        Tester test = new Tester();
        System.out.println("Hello space!");
        test.test();
    }
}
