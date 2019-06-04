import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void test() {
        //读取配置文件，加载里面的配置等
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        //通过getBean得到对象，其中IService就是我们刚刚在application.xml中配置的<bean/>中的id
        TestSpring testSpring = (TestSpring) applicationContext.getBean("testSpringImpl");
        //得到对象，便执行其方法
        testSpring.doSome();
    }
    public static void main(String[] args){
        test();
    }
}
