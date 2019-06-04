import org.springframework.stereotype.Component;

@Component
public class TestSpringImpl implements TestSpring {
    @Override
    public void doSome() {
        System.out.println("do some......");
    }
}
