import net.dongliu.requests.Requests;
import net.dongliu.requests.Response;

/**
 * Created by chendong on 15-3-7.
 */
public class RequestsTest {
    public static void main(String[] args) {
        String url = "https://github.com/xiaxiaocao/requests";
        Response<String> resp = Requests.get(url).text();
        System.out.println(resp.getStatusCode());
        System.out.println(resp.getBody());
    }
}
