package bt02.vn.util;

import java.security.SecureRandom;

/**
 * OtpUtil: sinh ma OTP ngau nhien 6 chu so, dung chung cho ca luong
 * "dang ky + kich hoat tai khoan" va "quen mat khau" (Bai tap 03).
 * Dung SecureRandom thay vi Random thuong de an toan hon (du bai tap
 * hoc phan chu yeu can dung Random la du, nhung SecureRandom khong ton
 * kem gi them ma van dung chuan hon cho 1 chuc nang lien quan bao mat).
 */
public class OtpUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private OtpUtil() {
    }

    /** Sinh 1 ma OTP gom dung 6 chu so, tu "000000" den "999999". */
    public static String generateOtp() {
        int number = RANDOM.nextInt(1_000_000); // 0 .. 999999
        return String.format("%06d", number); // luon du 6 chu so (co so 0 dung truoc neu can)
    }
}
