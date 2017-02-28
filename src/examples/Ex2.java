package examples;

import simbad.demo.BumpersDemo;
import simbad.gui.Simbad;
import simbad.sim.*;

import javax.vecmath.Vector3d;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by huma on 31-Oct-16.
 */
public class Ex2 {

    public static void main(String[] args) {
        // request antialising
        System.setProperty("j3d.implicitAntialiasing", "true");
        // create Simbad instance with given environment
//        Simbad frame = new Simbad(new ExplorationDemo(), false);
        Simbad frame = new Simbad(new BumpersDemo(), false);
    }


    /**
     * Describe the robot
     */
    static public class Robot extends Agent {
        private static final int INITIAL_T_VELOCITY = 1;

        RangeSensorBelt sonars;
        CameraSensor camera;
        private double currentX;
        private double currentZ;
        private double previousX = -10.5;
        private double previousZ = 10.5;
        private double nextX;
        private double nextZ;
        private double i = 10;
        int delay = 0;
        int currentEqualsPrevious = 4;
        double prevLifeTime;

        boolean[][] scannedPixels = new boolean[211][211];


        public Robot(Vector3d position, String name) {
            super(position, name);
            camera = RobotFactory.addCameraSensor(this);
            sonars = RobotFactory.addSonarBeltSensor(this);
//            i = 10;

        }


        /**
         * This method is called by the simulator engine on reset.
         */
        @Override
        public void initBehavior() {
            i = 10;
            prevLifeTime = 0;
        }

        /**
         * This method is call cyclically (20 times per second)  by the simulator engine.
         */
        @Override
        public void performBehavior() {
            currentX = getCurrentPostion().getX();
            currentZ = getCurrentPostion().getZ();
            if (currentX < 10.55) {
                scannedPixels[mapX(currentX)][mapZ(currentZ)] = true;
            }
            setTranslationalVelocity(INITIAL_T_VELOCITY);
            setRotationalVelocity(0);
            if (currentX > 0) {
                rotate(90, true);
                setTranslationalVelocity(0);
                setRotationalVelocity(0);
                SimpleAgent.collisionDetected = true;
            }
            previousX = currentX;
            previousZ = currentZ;

            log();
        }

        private void log() {
            if (currentX < 10.6) {
                try {
                    FileWriter file = new FileWriter("log.txt");
                    for (int i = 0; i < scannedPixels[0].length; i++) {
                        file.write(Arrays.toString(scannedPixels[i]) + "\n");
                    }
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


     /*   private void rotateWheel(double degree, double z) {
            if (m(Math.abs(currentX), 10)) {

                System.out.println("currentX --> " + currentX);
                if (isEqual(this.currentZ, z) && !(getLifeTime() > prevLifeTime + INITIAL_T_VELOCITY)) {
                    System.out.println("IN=================");
                    setTranslationalVelocity(0);
                    setRotationalVelocity(degreeToRadian(degree));
                    prevLifeTime = getLifeTime();
                    i = 9;
                }
                if (getLifeTime() > prevLifeTime + INITIAL_T_VELOCITY) {
                    System.out.println("getLifeTime() > prevLifeTime + INITIAL_T_VELOCITY");
                    setTranslationalVelocity(INITIAL_T_VELOCITY);
                    setRotationalVelocity(0);
                }
            }
        }*/

        private void rotate(int degree, boolean p) {
            if (p) {
                if (prevLifeTime == 0) {
                    prevLifeTime = getLifeTime();
                    setTranslationalVelocity(0);
                    setRotationalVelocity(degreeToRadian(degree));
                } else if (getLifeTime() > prevLifeTime + INITIAL_T_VELOCITY) {
                    setTranslationalVelocity(INITIAL_T_VELOCITY);
                    setRotationalVelocity(0);
//                time = 0;
                }
            }
        }

        private boolean m(double x, double i) {
            double errorMargin = .05;
            return x >= i - errorMargin && x <= i + errorMargin;
        }

        private boolean isEqual(double axes, double value) {
            double errorMargin = .01;
            return axes >= value - errorMargin && axes <= value + errorMargin;
        }

        void performAction() {
            setTranslationalVelocity(INITIAL_T_VELOCITY);
            setRotationalVelocity(0);
            int i = getDirection();
            boolean perform = false;
            boolean[] surroundingPixels = new boolean[3];
            boolean collision = collisionDetected();
            String[] arr = {"up", "down", "right", "left", "current = previous"};
            System.out.println(arr[i]);
                       /*{scannedPixels[mapX(currentX) - 1][mapZ(currentZ)],
                        scannedPixels[mapX(currentX) + 1][mapZ(currentZ)],
                        scannedPixels[mapX(currentX)][mapZ(currentZ) - 1],
                        scannedPixels[mapX(currentX)][mapZ(currentZ) + 1]}*/

            /*    switch (i) {
                    case (0):
                        perform = collision | surroundingPixels[0];
                        break;
                    case (1):
                        perform = collision | surroundingPixels[1];
                        break;
                    case (2):
                        perform = collision | surroundingPixels[2];
                        break;
                    case (3):
                        perform = collision | surroundingPixels[3];
                        break;
                }*/
        }

        private int getDirection() {
            int i;
            System.out.println("curr: " + currentX + "  prev: " + previousX);
            if (currentX - previousX < 0)
                i = 0;
            else if (currentX - previousX > 0)
                i = 1;
            else if (currentX - previousX == 0)
                i = 4;
            else if (currentZ - previousZ < 0) i = 2;
            else if (currentZ - previousZ < 0) i = 3;
            else if (currentZ - previousZ == 0)
                i = 4;
            else i = currentEqualsPrevious;
            currentEqualsPrevious = i;
            return i;
        }

        int mapX(double position) {
            return (int) (105 + position * 10);

        }

        int mapZ(double position) {
            return (int) (105 - position * 10);

        }


        private double degreeToRadian(double degree) {
            return (degree / 180) * Math.PI;
        }
//        private void rotateWheelT(double degree, double time) {
//            if (getLifeTime() > time && getLifeTime() < time + INITIAL_T_VELOCITY) {
//                setTranslationalVelocity(0);
//                setRotationalVelocity(degreeToRadian(degree));
//            } else if (getLifeTime() > time + INITIAL_T_VELOCITY) {
//                // progress at 1.0 m/s
//                setTranslationalVelocity(INITIAL_T_VELOCITY);
//                setRotationalVelocity(0);
//            }
//        }
    }


    /**
     * Describe the environment
     */
    private static class MyEnv extends EnvironmentDescription {
        static Robot robot;

        MyEnv() {
            light1IsOn = true;
            light2IsOn = false;
            worldSize = 25;
            Wall w1 = new Wall(new Vector3d(11, 0, 0), 22, 1, this);
            w1.rotate90(1);
            add(w1);
            Wall w2 = new Wall(new Vector3d(-11, 0, 0), 22, 1, this);
            w2.rotate90(1);
            add(w2);
            Wall w3 = new Wall(new Vector3d(0, 0, 11), 22, 1, this);
            add(w3);
            Wall w4 = new Wall(new Vector3d(0, 0, -11), 22, 1, this);
            add(w4);
//            add(new Box(new Vector3d(-3, 0, -3), new Vector3f(1, 1, 1), this));
//            add(new Arch(new Vector3d(3, 0, -3), this));
            robot = new Robot(new Vector3d(-10.5, 0, 10.5), "robot 1");
            add(robot);
        }
        
        

    public static void main(String[] args) {
        // request antialising
        System.out.println(System.setProperty("java.library.path", "/home/bassel/Desktop"));
        System.setProperty("j3d.implicitAntialiasing", "true");
        // create Simbad instance with given environment
        Simbad frame = new Simbad(new MyEnv(), false);
//        frame.setVisible(true);
    }
    }

}