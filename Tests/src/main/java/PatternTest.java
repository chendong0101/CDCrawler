import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chendong on 15-11-11.
 */
public class PatternTest {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("http://odin\\.wandoulabs\\.com.*");
        Matcher m = p.matcher("http://odin.wandoulabs.com");
        if (m.find()) {
            System.out.print("match");
        }
    }
}
