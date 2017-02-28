package examples;

import simbad.sim.EnvironmentDescription;
import simbad.sim.Robot;
import simbad.sim.Wall;

import javax.vecmath.Vector3d;

/**
 * Created by huma on 04-Nov-16.
 */
public class Scanner001 {

/*
    */
/**
     * Describe the robot
     *//*


    static public class Robot extends Agent {
        private static final double INITIAL_T_VELOCITY = 1;
        private double prevLifeTime;
        private Point3d currentP = new Point3d();
        int c0, c1, c2;

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

        */
/**
         * This method is called by the simulator engine on reset.
         *//*

        @Override
        public void initBehavior() {
            setTranslationalVelocity(INITIAL_T_VELOCITY);
            prevLifeTime = 0;
            c0 = 0;
            c1 = 0;
            c2 = 0;
        }

        */
/**
         * This method is call cyclically (20 times per second)  by the simulator engine.
         *//*


        @Override
        public void performBehavior() {
            getCoords(currentP);

            double x = round(currentP.getX());
            double z = round(currentP.getZ());

            if (x == 10.0 && z == 10.0) {
                System.out.println("currentP = " + currentP);
                rotateRight();
            }
            if (x == 10.0 && z == 9.0) {
                System.out.println("currentP = " + currentP);
                if (c0 == 0) {
                    prevLifeTime = 0;
                    c0++;
                }
                rotateRight();
            }
            if (x == -10 && z == 9) {
                if (c1 == 0) {
                    prevLifeTime = 0;
                    c1++;
                }
                System.out.println("currentP = " + currentP);
                rotateLeft();
            }
            if (z == 8)
                System.out.println("x = " + (Math.round(x) == -10));
            if (Math.round(x) == -10 && z == 8) {
                if (c2 == 0) {
                    prevLifeTime = 0;
                    c2++;
                }
                System.out.println("Math.round(x) == 10 && z == 8 " + currentP);
                rotateLeft();
            }

//            if (z == 9) {
//                rotateRight();
//            }
        }

        private void rotateRight() {
            System.out.println("Robot.rotateRight");
            rotate(90);
        }

        private void rotateLeft() {
            System.out.println("Robot.rotateLeft");
            rotate(-90);
        }

        private void rotate(int degree) {
            if (prevLifeTime == 0) {
                prevLifeTime = getLifeTime();
                setTranslationalVelocity(0);
                setRotationalVelocity(degreeToRadian(degree));
                System.out.println("f1");
            } else if (getLifeTime() > prevLifeTime + INITIAL_T_VELOCITY) {
                setRotationalVelocity(0);
                setTranslationalVelocity(INITIAL_T_VELOCITY);
//                prevLifeTime = 0;
                System.out.println("f2");
            }
        }

        private double degreeToRadian(double degree) {
            return (degree / 180.0) * Math.PI;
//            return 1.5708;
        }

        */
/**
         * It counts with +0.05 ot -0.05
         * EX:
         * 1.0
         * 1.05 ,1.1 ,1.15 ,1.2 ,1.25 ,1.3 ,1.35 ,1.4 ,1.45 ,1.5
         *//*

        private double round(double v) {
            return Math.round(v * 100.0) / 100.0;
        }

    }
*/


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
            add(new Robot(new Vector3d(0, 0, 0), "robot 1"));
        }
    }

}