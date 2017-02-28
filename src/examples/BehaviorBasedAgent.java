package examples;

import static java.lang.Math.PI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import simbad.gui.Simbad;
import simbad.sim.*;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * Created by YourPc on 11/25/2016.
 */
public class BehaviorBasedAgent {

  /**
   * Describe the robot
   */
  static public class Robot extends Agent {

    int inf = 1000 * 1000 * 1000;
    RangeSensorBelt sonars, bumpers;

    int offset = 9;
    boolean[][] blocked;

    public Robot(Vector3d position, String name) {
      super(position, name);

      // Add sonars
      sonars = RobotFactory.addSonarBeltSensor(this);

      // Add bumpers
      bumpers = RobotFactory.addBumperBeltSensor(this, 8);

      // maps
      initBehavior();
    }

    /**
     * This method is called by the simulator engine on reset.
     */
    @Override
    public void initBehavior() {
      x2 = 0;
      z2 = 0;
      pos = 0;
      X = 4;
      Z = -4;
      state = RobotState.SEARCHING;
      blocked = new boolean[20][20];
    }

    boolean valid(double x, double z) {
      return (-8 <= x && x <= 8 && -8 <= z && z <= 8) && false == blocked[offset + (int) x][offset + (int) z];
    }

    double distance(double x, double z, double x2, double z2) {
      double dx = x - x2;
      double dz = z - z2;
      return Math.sqrt(dx * dx + dz * dz);
    }

    enum RobotState {
      SEARCHING, MOVING, COLLISION, REST;
    }

    RobotState state = RobotState.SEARCHING;

    int pos = 0;
    int[] dx = {1, 0, -1, 0};
    int[] dz = {0, 1, 0, -1};

    double X, Z, x2, z2;

    /**
     * This method is call cyclically (20 times per second) by the simulator
     * engine.
     */
    @Override
    public void performBehavior() {
      Vector3d position = getCurrentPostion();
      double x = position.getX();
      double z = position.getZ();

      System.out.println(blocked[offset + (int) x][offset + (int) z] + " ");

      switch (state) {
        case SEARCHING:
          System.out.println("SEARCHING");

          if (Math.abs(X - x) < 1e-3 && Math.abs(Z - z) < 1e-3) {
            state = state.REST;
            break;
          } else

            choose:
            for (int i = 0; i < 4; ++i) {
              double a = x + dx[i];
              double b = z + dz[i];
              System.out.println("finding dist");
              if (valid(a, b) && distance(a, b, X, Z) < distance(x, z, X, Z)) {

                if (pos != i) {
                  System.out.println("found");
                  double theta = Math.PI / 2;

                  theta *= pos - i;

                  rotateY(theta);
                  pos = i;

                }
                x2 = a;
                z2 = b;
                state = state.MOVING;
                break choose;
              }
            }

          break;

        case MOVING:
          if (bumpers.oneHasHit()) {
            setTranslationalVelocity(0);
            state = state.COLLISION;
            break;
          }

          System.out.println("I'm moving");

          System.out.println("x = " + x + " x2 = " + x2);

          // already there
          if (Math.abs(x2 - x) < 1e-6 && Math.abs(z2 - z) < 1e-6) {
            setTranslationalVelocity(0);
            state = state.SEARCHING;
            break;
          }

          setTranslationalVelocity(1);
          break;

        case COLLISION:
          System.out.println("COLLISION!!!");
          int xPos = (int) x;
          int zPos = (int) z;
          blocked[xPos + offset][zPos + offset] = true;
          setTranslationalVelocity(-3.5);
          state = state.SEARCHING;
          break;

        case REST:
          System.out.println("I'm taking a rest");
          setTranslationalVelocity(0);
          break;
      }
    }

    /**
     * point
     */
    public static class Point implements Comparable<Point> {

      int x, y;
      int g, h;

      public Point(int x, int y) {
        this.x = x;
        this.y = y;
      }

      public Point(int x, int y, int g, int h) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
      }

      @Override
      public int compareTo(Point t) {
//        if (g == t.g && t.h == h)
//          return 0;
//        else if (g + h < t.g + t.h)
//          return -1;
//        else
//          return 1;
        return h < t.h ? -1 : 1;
      }

      @Override
      public boolean equals(Object o) {
        Point p = (Point) o;
        boolean same = true;
        same &= x == p.x;
        same &= y == p.y;
        return same;
      }

      @Override
      public int hashCode() {
        return (x + 256) + (y + 256) * 1024;
      }

    }

    Point bfs(Point cur, Point target) {
      Queue<Point> q = new LinkedList<>();
      Set<Point> s = new HashSet<>();

      q.offer(cur);
      s.add(cur);

      while (!q.isEmpty()) {
        Point p = q.poll();

        for (int i = 0; i < dx.length; ++i) {
          int a = p.x + dx[i];
          int b = p.y;// + dy[i];
          Point c = new Point(a, b);

          if (a == target.x && target.y == b)
            return p;

          if (s.contains(c) /* dist[a + offset][b + offset] == inf || */)
            continue;

          q.offer(c);
          s.add(c);
        }
      }

      return cur;
    }

    /**
     * Describe the environment
     */
    private static class MyEnv extends EnvironmentDescription {

      MyEnv() {
        light1IsOn = true;
        light2IsOn = false;
        Wall w1 = new Wall(new Vector3d(9, 0, 0), 19, 1, this);
        w1.rotate90(1);
        add(w1);
        Wall w2 = new Wall(new Vector3d(-9, 0, 0), 19, 2, this);
        w2.rotate90(1);
        add(w2);
        Wall w3 = new Wall(new Vector3d(0, 0, 9), 19, 1, this);
        add(w3);
        Wall w4 = new Wall(new Vector3d(0, 0, -9), 19, 2, this);
        add(w4);
        Box b1 = new Box(new Vector3d(-3, 0, -3), new Vector3f(1, 1, 1),
                this);
        add(b1);
        add(new Arch(new Vector3d(3, 0, -3), this));
        add(new Robot(new Vector3d(0, 0, 0), "robot 1"));

      }
    }

    public static void main(String[] args) {
      // request antialising
      System.setProperty("j3d.implicitAntialiasing", "true");
      // create Simbad instance with given environment
      Simbad frame = new Simbad(new MyEnv(), false);
    }

  }
}
