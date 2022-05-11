package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import frc.robot.Portmap;

public class Conveyor {

    PWMVictorSPX cBottom;
    PWMVictorSPX cTop;

    double bspeed = -.3;
    double tspeed = .6; //.5

    public Conveyor(){
        cBottom = new PWMVictorSPX(Portmap.conveyorBottom);
        cTop = new PWMVictorSPX(Portmap.conveyorTop);
        cBottom.set(0);
        cTop.set(0);
    }

    public void allForwards(){
        topForwards();
        bottomForwards();
    }

    public void allReverse(){
        topReverse();
        bottomReverse();
    }

    public void topForwards(){
        cTop.set(tspeed);
    }

    public void bottomForwards(){
        cBottom.set(bspeed);
    }

   
    public void topReverse(){
        cTop.set(-tspeed);
    }

    public void bottomReverse(){
        cBottom.set(-bspeed);
    }

    public void topStop(){
        cTop.set(0);
    }

    public void bottomStop(){
        cBottom.set(0);
    }

    public void stop(){
        cTop.set(0);
        cBottom.set(0);
    }
}
