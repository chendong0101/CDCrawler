import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by chendong on 15-11-24.
 */
interface Transportation {
    void run();
    void stop();
}

class Car implements Transportation {
    public void run() {
        System.out.println("Car is running");
    }
    public void stop() {
        run();
        System.out.println("Car stop");
    }
}

class TransportantionFactory {
    public static Transportation getInstance() {
        return new Car();
    }
}

class TransportationProxyFactory {

    public static Transportation create(){
        final Class<?>[] interfaces = new Class[]{Transportation.class};
        final TransportationInvocationHandler handler =
                new TransportationInvocationHandler(TransportantionFactory.getInstance());
        return (Transportation) Proxy.newProxyInstance(
                Transportation.class.getClassLoader(), interfaces, handler);
    }

    public static class TransportationInvocationHandler
            implements InvocationHandler {

        private final Transportation transportation;

        public TransportationInvocationHandler(Transportation transportation) {
            this.transportation = transportation;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("--before ..." + this.getClass());
            Object ret = method.invoke(transportation, args);
            System.out.println("--after ...");
            return ret;
        }

    }
}

public class ProxyTest {
    public static void main(String[] args) {
        Transportation proxyObj = TransportationProxyFactory.create();
        proxyObj.stop();
    }
}
