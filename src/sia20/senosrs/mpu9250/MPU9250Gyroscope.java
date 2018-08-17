package sia20.senosrs.mpu9250;

public class MPU9250Gyroscope extends Sensor{

    private SCALE gScale;
    MPU9250Gyroscope(){
        super(0x68);
    }

    byte[] readSelfTest(){
        return read(0x00, 3);
    }

    void configure(SCALE scale, FChoice fChoice, int dlpfcfg){
        this.gScale = scale;
        byte config = 0x1B;
        config = (byte)(config & scale.getScale());
        config = (byte)(config & fChoice.getConfig());
        write(0x1B, config);
        write(0x1A, (byte)(dlpfcfg & 0x07) );
    }

    byte[] readData(){
        return read(0x43, 6);
    }

    double[] readGyro(){
        byte[] data = readData();
        double[] gyroData = new double[3];
        for (int i = 0; i < gyroData.length; i++) {
            gyroData[i] = CombineBytes.bytesToInt(data[i*2], data[i*2+1]);
            gyroData[i] = gyroData[i]*this.gScale.getValue()/32768.0; //don't know where 32768.0 is from but will test with it.
        }                                                             // 2^15 = 32768, maybe sensitivity = range/resolution(2^16-1)
        return gyroData;
    }
    enum SCALE{
        Quarter(0b00011, 250), HALF(0b01011, 500), FULL(0b10011, 1000), DOUBLE(0b11011, 2000);

        private int gyroFullScale;
        private int value;
        SCALE(int fullScale, int newValue){
            this.gyroFullScale = fullScale;
            this.value = newValue;
        }

        int getScale(){
            return gyroFullScale;
        }

        int getValue() {
            return value;
        }
    }

    enum FChoice{
        //has to be inverse because of how mpu register works
        ZERO(0b11), ONE(0b10), TWO(0b01), THREE(0b00);

        private int config;
        FChoice(int newConfig){
            this.config = newConfig;
        }

        int getConfig(){
            return config;
        }

    }
}
