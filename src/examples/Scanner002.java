/*
package examples;

import simbad.gui.Simbad;
import simbad.sim.*;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

*
 * Created by huma on 08-Nov-16.


public class Scanner002 {

    static public class Robot extends Agent {
        private static final double INITIAL_T_VELOCITY = 1;
        private double prevLifeTime;
        private Point3d currentP = new Point3d();
        int c, c0, c1, c2, c4, cc, cc0, cc1, cc2, cn, cn0, cn1, cn2, X, Z;

        public static void main(String[] args) {
            // request antialising
            System.setProperty("j3d.implicitAntialiasing", "true");
            // create Simbad instance with given environment
            Simbad frame = new Simbad(new MyEnv(), false);
//        frame.setVisible(true);
        }

        RangeSensorBelt sonars;

        CameraSensor camera;

        public Robot(Vector3d position, String name) {
            super(position, name);
            camera = RobotFactory.addCameraSensor(this);
            sonars = RobotFactory.addSonarBeltSensor(this);
        }

*
         * This method is called by the simulator engine on reset.


        @Override
        public void initBehavior() {
            setTranslationalVelocity(INITIAL_T_VELOCITY);
            prevLifeTime = 0;
            c = 0;
            c0 = 0;
            c1 = 0;
            c2 = 0;
            c4 = 0;
            cc = 0;
            cc0 = 0;
            cc1 = 0;
            cc2 = 0;
            cn = Integer.MAX_VALUE;
            cn0 = Integer.MAX_VALUE;
            cn1 = Integer.MAX_VALUE;
            cn2 = Integer.MAX_VALUE;
            X = 10;
            Z = 10;
            try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get("./fileName.txt"));) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

*
         * This method is call cyclically (20 times per second)  by the simulator engine.



        @Override
        public void performBehavior() {
            getCoords(currentP);
            (10,10)  R  | (10,8)  R
            (10,9)   R  | (10,7)  R
            (-10,9)  L  | (-10,7) L
            (-10,8)  L  | (-10,6) L

            (-10,10) L  | (-10,8) L
            (10,10)  R  | (10,8)  R
            (10,9)   R  | (10,7)  R
            (-10,9)  L  | (-10,7) L


            double x = round(currentP.getX());
            double z = round(currentP.getZ());

            if (x == (int) x) System.out.println(currentP + " ==> CLEAR");

            cycle(x, z, 10, 10);
            cycle1(x, z, 10, 8);
            cycle2(x, z, 10, 6);
            cycle3(x, z, 10, 4);
            cycle3(x, z, 10, 2);
            cycle3(x, z, 10, 0);
            cycle3(x, z, 10, -2);
            cycle3(x, z, 10, -4);
            cycle3(x, z, 10, -6);
            cycle3(x, z, 10, -8);
            cycle3(x, z, 10, -10);
        }

        private void cycle(double x, double z, double X, double Z) {
            if (x == X && z == Z) { //1
                if (c == 0) {
                    c++;
                    c2 = 0;
                    prevLifeTime = 0;
                    System.out.println("x == 10 && z == 10");
                    System.out.println("currentP = " + currentP);
                }
                rotateRight();
            }
            if (x == X && z == Z - 1) { //2
                if (c0 == 0) {
                    prevLifeTime = 0;
                    c0++;
                    System.out.println("x == 10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotateRight();
            }
            if (x == -X && z == Z - 1) {//3
                if (c1 == 0) {
                    prevLifeTime = 0;
                    c1++;
                    System.out.println("x == -10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-85.715); //L
            }
            if (x == -X && z == Z - 2) {//4
                if (c2 == 0) {
                    prevLifeTime = 0;
                    c2++;
                    c = 0;
                    c0 = 0;
                    c1 = 0;
                    System.out.println("x == -10 && z == 8");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-85.715); //L
            }
        }

        private void cycle1(double x, double z, double X, double Z) {
            if (x == X && z == Z) { //1
                if (c == 0) {
                    c++;
                    c2 = 0;
                    prevLifeTime = 0;
                    System.out.println("x == 10 && z == 10");
                    System.out.println("currentP = " + currentP);
                }
                rotate(85.715);
                ;
            }
            if (x == X && z == Z - 1) { //2
                if (c0 == 0) {
                    prevLifeTime = 0;
                    c0++;
                    System.out.println("x == 10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotate(85.715);
            }
            if (x == -X && z == Z - 1) {//3
                if (c1 == 0) {
                    prevLifeTime = 0;
                    c1++;
                    System.out.println("x == -10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-85.715); //L
            }
            if (x == -X && z == Z - 2) {//4
                if (c2 == 0) {
                    prevLifeTime = 0;
                    c2++;
                    c = 0;
                    c0 = 0;
                    c1 = 0;
                    System.out.println("x == -10 && z == 8");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-85.715); //L
            }
        }

        private void cycle2(double x, double z, double X, double Z) {
            if (x == X && z == Z) { //1
                if (c == 0) {
                    c++;
                    c2 = 0;
                    prevLifeTime = 0;
                    System.out.println("x == 10 && z == 10");
                    System.out.println("currentP = " + currentP);
                }
                rotate(85.715);
                ;
            }
            if (x == X && z == Z - 1) { //2
                if (c0 == 0) {
                    prevLifeTime = 0;
                    c0++;
                    System.out.println("x == 10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotate(85.715);
            }
            if (x == -X && z == Z - 1) {//3
                if (c1 == 0) {
                    prevLifeTime = 0;
                    c1++;
                    System.out.println("x == -10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-90); //L
            }
            if (x == -X && z == Z - 2) {//4
                if (c2 == 0) {
                    prevLifeTime = 0;
                    c2++;
                    c = 0;
                    c0 = 0;
                    c1 = 0;
                    System.out.println("x == -10 && z == 8");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-90); //L
            }
        }

        private void cycle3(double x, double z, double X, double Z) {
            if (x == X && z == Z) { //1
                if (c == 0) {
                    c++;
                    c2 = 0;
                    prevLifeTime = 0;
                    System.out.println("x == 10 && z == 10");
                    System.out.println("currentP = " + currentP);
                }
                rotate(90);
                ;
            }
            if (x == X && z == Z - 1) { //2
                if (c0 == 0) {
                    prevLifeTime = 0;
                    c0++;
                    System.out.println("x == 10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotate(90);
            }
            if (x == -X && z == Z - 1) {//3
                if (c1 == 0) {
                    prevLifeTime = 0;
                    c1++;
                    System.out.println("x == -10 && z == 9");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-90); //L
            }
            if (x == -X && z == Z - 2) {//4
                if (c2 == 0) {
                    prevLifeTime = 0;
                    c2++;
                    c = 0;
                    c0 = 0;
                    c1 = 0;
                    System.out.println("x == -10 && z == 8");
                    System.out.println("currentP = " + currentP);
                }
                rotate(-90); //L
            }
        }

        private void rotateRight() {
            rotate(90);
//            System.out.println("Robot.rotateRight");
        }

        private void rotateLeft() {
            rotate(-90);
            System.out.println("Robot.rotateLeft");
        }

        private void rotate(double degree) {
            if (prevLifeTime == 0) {
                prevLifeTime = getLifeTime();
                setTranslationalVelocity(0);
                setRotationalVelocity(degreeToRadian(degree));
            } else if (getLifeTime() > prevLifeTime + INITIAL_T_VELOCITY) {
                setRotationalVelocity(0);
                setTranslationalVelocity(INITIAL_T_VELOCITY);
//                prevLifeTime = 0;
            }
        }

        private double degreeToRadian(double degree) {
            return (degree / 180.0) * Math.PI;
        }

*
         * It counts with +0.05 ot -0.05
         * EX:
         * 1.0
         * 1.05 ,1.1 ,1.15 ,1.2 ,1.25 ,1.3 ,1.35 ,1.4 ,1.45 ,1.5


        private double round(double v) {
            return Math.round(v * 100.0) / 100.0;
        }

    }


    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    private static class MyEnv extends EnvironmentDescription {
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
//            add(new Robot(new Vector3d(-10, 0, 10), "robot 1"));
            add(new Robot(new Vector3d(-10, 0, 10), "robot 1"));
        }
    }

}
*/
