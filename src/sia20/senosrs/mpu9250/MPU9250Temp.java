package sia20.senosrs.mpu9250;

class MPU9250Temp extends Sensor {
    MPU9250Temp(){
        super(0x68);
    }

    byte[] readRawTemp(){
        return read(0x41, 2);
    }

    double readTemp(){
        byte[] rawTemp = readRawTemp();
        double temp = CombineBytes.bytesToInt(rawTemp[0], rawTemp[1]);
        temp = (temp/333.87) + 21;
        return temp;
    }
}
