package sia20.senosrs.mpu9250;

public class MPU9250Aceelerometer extends Sensor{

    MPU9250Aceelerometer(){
        super(0x68);
    }
    byte[] readSelfTest(){
        return read(0x0D, 3);
    }

    void configure(SCALE scale, FChoice fChoice, int dlpfcfg){
        byte configOne = (byte)(scale.getScale()<<2);
        byte configTwo = (byte)((0x0F & fChoice.getConfig()) & dlpfcfg);
        write(0x1C, configOne);
        write(0x1D, configTwo);
    }

    byte[] readData() {
        return read(0x3B, 6);
    }

    enum FChoice{
        ZERO(0b1111), ONE(0b0111);
        private int config;
        FChoice(int newFullScale){
            this.config = newFullScale;
        }
        int getConfig(){
            return config;
        }
    }

    enum SCALE{
        TWO(0b00), FOUR(0b01), EIGTH(0b10), SIXTEEN(0b11);

        private int fullScale;
        SCALE(int newFullScale){
            this.fullScale = newFullScale;
        }

        int getScale(){
            return fullScale;
        }
    }
}
