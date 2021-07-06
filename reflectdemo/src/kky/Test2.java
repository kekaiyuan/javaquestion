package kky;

/**
 * @author 柯凯元
 * @date 2021/07/06 11:25
 */
public class Test2 {
    private int id;

    private Test2() {
    }

    private Test2(int id) {
        this.id = id;
    }

    private int add(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return "Test2{" +
                "id=" + id +
                '}';
    }

}
