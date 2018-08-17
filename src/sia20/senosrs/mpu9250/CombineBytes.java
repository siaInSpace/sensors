package sia20.senosrs.mpu9250;

public class CombineBytes {
    static int bytesToInt(byte MSB, byte LSB){
        return  ((MSB<<8 | 0xF) & LSB);
    }
}
