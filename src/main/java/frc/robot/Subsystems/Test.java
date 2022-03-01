package frc.robot.Subsystems;

import frc.robot.*;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;


public class Test{ //extends SubsystemBase{
    PWMVictorSPX motorController;
    public Test(){
        motorController = new PWMVictorSPX(0);
        motorController.set(0);
    }

    public void forwards(double speed){
        motorController.set(speed);
    }
    
    public void backwards(double speed){
        motorController.set(-speed);
    }
    
    public void stop(){
        motorController.set(0);
    }

}