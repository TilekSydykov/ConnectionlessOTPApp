package kg.flaterlab.sec;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Formatter;



public class HmacSha1Signature {
    private final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public Date date;

    public long seconds;

    public HmacSha1Signature() {
        this.date = new Date();
    }

    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public String calculateRFC2104HMAC(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        String g = toHexString(mac.doFinal(data.getBytes()));
        return String.valueOf(truncate(g.getBytes(), 5));
    }

    private int DT(byte[] hmac_result) {
        int offset = hmac_result[19] & 0xf;
        int bin_code = (hmac_result[offset] & 0x7f) << 24
                | (hmac_result[offset + 1] & 0xff) << 16
                | (hmac_result[offset + 2] & 0xff) << 8
                | (hmac_result[offset + 3] & 0xff);
        return bin_code;
    }

    private int truncate(byte[] hmac_result, int digits) {
        int Snum = DT(hmac_result);
        return Snum % (int)Math.pow(10, digits);
    }

    public String gen(String userName, String passwordHash) {

        date = new Date();

        long t = date.getTime() / 20000;

        seconds = date.getTime() /2000;

        String time = String.valueOf(t);

        String forHash = userName + passwordHash;

        String result = "";
        try{
            result = calculateRFC2104HMAC(forHash, time);
        }catch (SignatureException e){

        }catch (NoSuchAlgorithmException e){

        }catch (InvalidKeyException e){

        }
        return getPass(result);
    }

    public String getPass(String s){

        if(s.length() < 5){
            s = "0" + s;
        }
        if(s.length() < 5){
            s = s + "0" ;
        }

        String f[] = {"A", "B", "G", "X", "4", "8", "2", "N", "D", "9"};
        String result = "";
        for(int i = 0; i < s.length(); i++){
            result += f[s.charAt(i) - '0'];
        }
        return result;
    }

}

