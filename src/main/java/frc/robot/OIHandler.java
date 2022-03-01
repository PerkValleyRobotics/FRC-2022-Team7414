package frc.robot;

import frc.robot.Portmap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class OIHandler {
    Joystick joystick;
    XboxController xbox;
    
    public OIHandler(){
        joystick = new Joystick(Portmap.joystickPort);
        xbox= new XboxController(Portmap.xboxPort);
    }

    public double getJoystickX(){
        return joystick.getX();
    }

    public double getJoystickY(){
        return -1 * joystick.getY();
    }

     public boolean getJoystickButtonPress(int button){
         return joystick.getRawButton(button);
     }

     public boolean onJoystickButtonPress(int button){
         return joystick.getRawButtonPressed(button);
     }

     public boolean onJoystickButtonRelease(int button){
         return joystick.getRawButtonReleased(button);
     }

     public int getJoystickPOV(){
         return joystick.getPOV();
     }

     public boolean getXboxButtonPress(int button){
         return xbox.getRawButton(button);
     }

     public boolean onXboxButtonPress(int button){
         return xbox.getRawButtonPressed(button);
     }

     public double getTrigger(int axis){
        return xbox.getRawAxis(axis);
     }

    public double getSlider(){
        return -1 * joystick.getThrottle();
    }
}
