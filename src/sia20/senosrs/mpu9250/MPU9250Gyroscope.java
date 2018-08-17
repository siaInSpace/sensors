package sia20.senosrs.mpu9250;

public class MPU9250Gyroscope extends Sensor{

    MPU9250Gyroscope(){
        super(0x68);
    }

    byte[] readSelfTest(){
        return read(0x00, 3);
    }

    void configure(SCALE scale, FChoice fChoice, int dlpfcfg){
        byte config = 0x1B;
        config = (byte)(config & scale.getScale());
        config = (byte)(config & fChoice.getConfig());
        write(0x1B, config);
        write(0x1A, (byte)(dlpfcfg & 0x07) );
    }

    byte[] readData(){
        return read(0x43, 6);
    }

    enum SCALE{
        Quarter(0b00011), HALF(0b01011), FULL(0b10011), DOUBLE(0b11011);

        private int gyroFullScale;
        SCALE(int fullScale){
            this.gyroFullScale = fullScale;
        }

        int getScale(){
            return gyroFullScale;
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
