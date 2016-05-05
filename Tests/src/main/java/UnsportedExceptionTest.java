import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chendong on 14-12-17.
 */
public class UnsportedExceptionTest {
    public static void main(String[] args) {
        Long[] vidA = {10L, 20L};

        List<Long> vidNoException = new ArrayList<>();
        vidNoException.addAll(Arrays.asList(vidA));
        vidNoException.add(30L);
        for (Long l : vidNoException) {
            System.out.println(l);
        }

        try {
            List<Long> vids = Arrays.asList(vidA);
            vids.add(30L);
            for (Long l : vids) {
                System.out.println(l);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
