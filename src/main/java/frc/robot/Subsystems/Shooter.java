package frc.robot.Subsystems;

import frc.robot.Portmap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Shooter {
    CANSparkMax shoot;
    public Shooter(){
        shoot = new CANSparkMax(Portmap.CAN_SHOOT, MotorType.kBrushless);
    }

    public void spin(double speed){
        shoot.set(speed);
    }

    public void stop(){
        shoot.stopMotor();
    }
}
