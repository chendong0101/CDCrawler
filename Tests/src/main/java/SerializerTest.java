import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

/**
 * Created by chendong on 15-12-11.
 */
public class SerializerTest {

    public static class A implements Serializable{
        private int a;
        private String b;
        private BigDecimal c;
        private Date d;

        public A(int a, String b, BigDecimal c, Date d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    static Gson gson = new Gson();
    public static String jsonSerialize(List<A> a) {
        String json = gson.toJson(a);
        return json;
    }

    public static byte[] javaSerialize(List<A> a) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(a);
                return bos.toByteArray();
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        List<A> as = new ArrayList<>();
        for (int i=0; i<200; ++i) {
            as.add(new A(1, "abcd", new BigDecimal(1), cal.getTime()));
        }
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; ++i) {
            SerializerTest.jsonSerialize(as);
        }

        long t2 = System.currentTimeMillis();
        for (int i = 0; i < 10000; ++i) {
            SerializerTest.javaSerialize(as);
        }

        long t3 = System.currentTimeMillis();

        System.out.println("json: " + (t2 - t1));
        System.out.println("java: " + (t3 - t2));

        String s = SerializerTest.jsonSerialize(as);
        byte[] b = SerializerTest.javaSerialize(as);

        System.out.println("json: " + s.getBytes().length);
        System.out.println("java: " + b.length);
    }

}
