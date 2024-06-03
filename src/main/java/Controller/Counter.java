package Controller;

public class Counter implements AutoCloseable{

    public Counter() {
    }

    static int sum;
    {
        sum = 0;
    }

    public void add() {
        sum++;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Count closed");
    }
}
