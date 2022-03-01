package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.*;

public class Climber {
    DoubleSolenoid ds;
    public Climber(){
        ds = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Portmap.PCM_ClimbExtend, Portmap.PCM_ClimbRetract);
        ds.set(Value.kReverse);
    }

    public void extend(){
        ds.set(Value.kForward);
    }

    public void retract(){
        ds.set(Value.kReverse);
    }

    public void off(){
        ds.set(Value.kOff);
    }
}
