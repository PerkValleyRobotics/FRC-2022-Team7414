package frc.robot.Subsystems;

import frc.robot.Portmap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Intake {

    PWMVictorSPX intake;
    PWMVictorSPX boosterWheel;

    DoubleSolenoid piston;

    public boolean deployed;

    double bSpeed = .25;
    double iSpeed = .45;

    public Intake(){
        intake = new PWMVictorSPX(Portmap.intake);
        boosterWheel = new PWMVictorSPX(Portmap.boosterWheel);

        intake.set(0);
        boosterWheel.set(0);

        piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Portmap.PCM_intakeExtend, Portmap.PCM_intakeRetract);

        piston.set(Value.kReverse);

        deployed = false;
    }

    public void togglePistons(){
        if(deployed){
            retract();
        } else {
            extend();
        }
    }

    public void extend(){
        piston.set(Value.kForward);
        deployed = true;
    }

    public void retract(){
        piston.set(Value.kReverse);
        deployed = false;
    }

    public void decompress(){
        piston.set(Value.kOff);
    }

    public void allForwards(){
        boosterForwards();
        intakeForwards();
    }

    public void allReverse(){
        boosterReverse();
        intakeReverse();
    }

    public void intakeForwards(){
        intake.set(iSpeed);
    }

    public void boosterForwards(){
        boosterWheel.set(bSpeed);
    }

    public void intakeReverse(){
        intake.set(-iSpeed + .2);
    }

    public void boosterReverse(){
        boosterWheel.set(-bSpeed +.2);
    }

    public void boosterStop(){
        boosterWheel.set(0);
    }
    
    public void intakeStop(){
        intake.set(0);
    }

    public void stop(){
        boosterWheel.set(0);
        intake.set(0);
    }
}
