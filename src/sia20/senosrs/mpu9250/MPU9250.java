package sia20.senosrs.mpu9250;

public class MPU9250 extends Sensor {

    MPU9250(){
        super(0x68);
    }

    byte[] readSensors(){
        return read(0x3B, 14);
    }
}
