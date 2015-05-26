package org.david.java.objectmethod.equals;// Attempting to add a value component to Point - Pages 37 - 38

public class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    // Broken - violates symmetry! 破坏了对称性 一边是类型不对，一般是忽略了颜色比较
  /*  @Override public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        return super.equals(o) && ((ColorPoint) o).color == color;
    }*/

    // Broken - violates transitivity! 排除混合比较的颜色比较，满足对称性 但是破坏了传递性
  @Override public boolean equals(Object o) {
      if (!(o instanceof Point))
          return false;

      // If o is a normal Point, do a color-blind comparison
      if (!(o instanceof ColorPoint))
          return o.equals(this);

      // o is a ColorPoint; do a full comparison
      return super.equals(o) && ((ColorPoint)o).color == color;
  }

    public static void main(String[] args) {
        // First equals function violates symmetry
        Point p11 = new Point(1, 2);
        Point p22 = new Point(3, 4);
        System.out.println(p11.equals(p22) + "" + p22.equals(p11));
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);
        System.out.println(p11.equals(cp) + " " + cp.equals(p11));

        // Second equals function violates transitivity
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
        System.out.printf("%s %s %s%n",
                          p1.equals(p2), p2.equals(p3), p1.equals(p3));
    }
}
