package algernon.subsumption.demo;

import algernon.subsumption.Behavior;
import algernon.subsumption.BehaviorBasedAgent;
import algernon.subsumption.Sensors;
import simbad.gui.Simbad;
import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.util.stream.IntStream;

/**
 * Created by YourPc on 11/30/2016.
 */
public class TwoRobotsDemo {

    public static void main(String[] args) {
        TwoRobotsDemo demo = new TwoRobotsDemo();
        demo.runDemo();
    }

    public void runDemo() {
        EnvironmentDescription environmentDescription = new MyEnv();

        BehaviorBasedAgent redAgent1 = new BehaviorBasedAgent(new Vector3d(4, 0,
                4), "Red Agent 1", 12, true);
        BehaviorBasedAgent redAgent2 = new BehaviorBasedAgent(new Vector3d(4, 0,
                -4), "Red Agent 2", 12, true);

        Color3f red = new Color3f(0.8f, 0, 0);
        redAgent1.setColor(red);
        redAgent2.setColor(red);

        initBehaviorBasedAgent(redAgent1);
        initBehaviorBasedAgent(redAgent2);

        environmentDescription.add(redAgent1);
        environmentDescription.add(redAgent2);

        Simbad frame = new Simbad(environmentDescription, false);
        System.out.println("frame: " + frame);
    }

    /**
     * @return
     */
    private void initBehaviorBasedAgent(BehaviorBasedAgent behaviorBasedAgent) {
        Sensors sensors = behaviorBasedAgent.getSensors();
        Behavior[] behaviors = {
                new Avoidance(sensors),
//                new LightSeeking(sensors),
//                new Wandering(sensors),
                new StraightLine(sensors)
        };
        boolean subsumes[][] = {
                {false, true, true, true},
//                {false, false, true, true},
//                {false, false, false, true},
                {false, false, false, false}
        };
        behaviorBasedAgent.initBehaviors(behaviors, subsumes);
    }

    /**
     * Describe the environment
     */
    static public class MyEnv extends EnvironmentDescription {
        public MyEnv() {
            light1IsOn = false;
            light2IsOn = true;
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
            add(new Box(new Vector3d(-3, 0, -3), new Vector3f(1, 1, 1), this));
            add(new Arch(new Vector3d(3, 0, -3), this));

            IntStream.range(-10, 10)
                    .forEach(value -> {
                        //horizontal
                        add(new Line(new Vector3d(value, 0, -10),
                                20, this, new Color3f(0.8f, 0, 0)));
                        //vertical
                        Line line = new Line(new Vector3d(-10, 0, value),
                                20, this, new Color3f(0.8f, 0, 0));
                        line.rotate90(1);
                        add(line);
                    });


//            light1SetPosition(6, .7f, 5);
            ambientLightColor = darkgray;
            light1Color = white;
            light2Color = white;
            wallColor = blue;
            archColor = green;
            boxColor = red;
            floorColor = white;
            backgroundColor = black;
        }
    }
}