package frc.robot.Subsystems;

import frc.robot.Portmap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;


public class Shooter {
    CANSparkMax shoot;
    RelativeEncoder enc;
    double power = .54;
    boolean flag = false;
    private SparkMaxPIDController controller;
    double processVariable;

    
    public Shooter(){
        shoot = new CANSparkMax(Portmap.CAN_SHOOT, MotorType.kBrushless);
        enc = shoot.getEncoder();
         //new SparkMaxPIDController(0.005, 0, 0.000001);
   
        PIDSetup();
    }

    public void PIDSetup(){
        shoot.restoreFactoryDefaults();
        controller = shoot.getPIDController();
        double kP, kI, kD, kF, maxOutput, minOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
        kP = .00014;
        kI = 0;
        kD = .0005;
        kF = .00021; //if messing up change this to .2-.24
        maxRPM = 5500;
        maxVel = 5500;
        minVel = -5500;
        maxAcc = 1000;
        maxOutput = 1;
        minOutput = -1;
        controller.setP(kP);
        controller.setI(kI);
        controller.setD(kD);
        controller.setFF(kF);
        controller.setIZone(0);
        controller.setSmartMotionMaxVelocity(maxVel, 0);
        controller.setSmartMotionMinOutputVelocity(minVel, 0);
        controller.setSmartMotionMaxAccel(maxAcc, 0);
        controller.setSmartMotionAllowedClosedLoopError(0, 0);
        shoot.burnFlash();
    }

    public void motionProfile(double setPoint){
        controller.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
        processVariable = getVel();
    }

    public void spin(double speed){
        shoot.set(speed);
        
    }

    public void PIDShooter(double rpm) {
        motionProfile(rpm);
    }

    public void spinVel(double rpm){
        if(!flag){
            power=.5;
            flag=true;
        } else {
            if(getVel() < rpm && power < .7){
                power+=.0005;
            } else {
                power-=.0005;
            }
        }
        System.out.println(power);
        spin(power);
    }

    public void stop(){
        shoot.stopMotor();
    }

    public double getVel(){
        return Math.abs(enc.getVelocity());
    }
}
