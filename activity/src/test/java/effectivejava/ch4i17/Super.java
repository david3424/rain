package effectivejava.ch4i17;

public class Super {
    // Broken - constructor invokes an overridable method
    public Super() {
        System.out.println("======1======:");
        overrideMe();
    }
    public void overrideMe() {
        System.out.println("======0======:");
    }
}
