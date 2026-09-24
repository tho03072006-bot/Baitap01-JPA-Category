package bt02.vn.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * MailService: gui email OTP bang Jakarta Mail (JavaMail).
 *
 * Doc thong tin tai khoan Gmail (dia chi email + App Password) tu file
 * "mail-secrets.properties" nam trong src/main/resources - file nay
 * KHONG duoc commit len Git (da khai bao trong .gitignore) vi chua thong
 * tin dang nhap that. Sinh vien tu dien thong tin cua minh vao file do
 * tren may (xem huong dan chi tiet trong mail-secrets.properties.example).
 *
 * CHE DO DU PHONG (demo mode): neu file cau hinh chua co, hoac 2 dong
 * mail.username/mail.password con de trong (truong hop chua kip tao App
 * Password Gmail), MailService se KHONG gui email that ma in ma OTP ra
 * console log cua Tomcat - nho vay van demo/test duoc TRON VEN luong
 * nghiep vu (dang ky, quen mat khau) ma khong bi chan boi buoc cau hinh
 * email that.
 */
public class MailService {

    private static final String CONFIG_FILE = "mail-secrets.properties";

    private static Properties cachedConfig;
    private static boolean configLoaded = false;

    private MailService() {
    }

    private static synchronized Properties loadConfig() {
        if (!configLoaded) {
            cachedConfig = new Properties();
            // getResourceAsStream doc file trong classpath (sau khi Maven build,
            // file trong src/main/resources se nam o WEB-INF/classes) - KHONG
            // dung duong dan file cung nhu Constants.DIR vi day la file cau
            // hinh di kem code, khong phai du lieu nguoi dung upload.
            try (InputStream in = MailService.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (in != null) {
                    cachedConfig.load(in);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            configLoaded = true;
        }
        return cachedConfig;
    }

    private static boolean isRealSmtpConfigured() {
        Properties p = loadConfig();
        String user = p.getProperty("mail.username");
        String pass = p.getProperty("mail.password");
        return user != null && !user.isBlank() && pass != null && !pass.isBlank();
    }

    /**
     * Gui 1 email chua ma OTP toi dia chi "toEmail".
     * @param toEmail     email nguoi nhan
     * @param otp         ma OTP 6 chu so (xem OtpUtil.generateOtp())
     * @param purposeText mo ta ngan de nguoi doc hieu OTP nay dung de lam
     *                    gi, vd "kich hoat tai khoan" hoac "dat lai mat khau"
     */
    public static void sendOtpEmail(String toEmail, String otp, String purposeText) {
        String subject = "[Baitap02 - WEBPR330479] Ma xac nhan OTP - " + purposeText;
        String body = "Xin chao,\n\n"
                + "Ma OTP de " + purposeText + " cua ban la: " + otp + "\n"
                + "Ma nay co hieu luc trong 10 phut ke tu luc gui.\n\n"
                + "Neu ban khong thuc hien yeu cau nay, vui long bo qua email nay.\n\n"
                + "-- He thong Bai tap 02/03 (WEBPR330479, HCMUTE) --";

        if (!isRealSmtpConfigured()) {
            logToConsole(toEmail, subject, body, otp);
            return;
        }

        Properties fileConfig = loadConfig();
        String host = fileConfig.getProperty("mail.smtp.host", "smtp.gmail.com");
        String port = fileConfig.getProperty("mail.smtp.port", "587");
        String username = fileConfig.getProperty("mail.username");
        String password = fileConfig.getProperty("mail.password");

        Properties smtpProps = new Properties();
        smtpProps.put("mail.smtp.auth", "true");
        smtpProps.put("mail.smtp.starttls.enable", "true");
        smtpProps.put("mail.smtp.host", host);
        smtpProps.put("mail.smtp.port", port);

        Session session = Session.getInstance(smtpProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            Transport.send(message);
            System.out.println("[MailService] Da gui OTP qua email that toi: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Gui that bai (vd sai App Password, chua bat 2-Step Verification...)
            // -> van in OTP ra console de khong lam gian doan viec demo/hoc tap.
            System.out.println("[MailService] GUI EMAIL THAT BAI (xem loi ben tren). In OTP du phong:");
            logToConsole(toEmail, subject, body, otp);
        }
    }

    private static void logToConsole(String toEmail, String subject, String body, String otp) {
        System.out.println("================ [DEMO MODE - CHUA CAU HINH SMTP THAT] ================");
        System.out.println("Chua dien tai khoan Gmail vao mail-secrets.properties (xem file .example)");
        System.out.println("nen OTP duoc in ra day thay vi gui email that:");
        System.out.println("  Nguoi nhan : " + toEmail);
        System.out.println("  Tieu de    : " + subject);
        System.out.println("  Ma OTP     : " + otp);
        System.out.println("  Noi dung   :\n" + body);
        System.out.println("========================================================================");
    }
}
