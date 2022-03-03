package frc.robot.Commands;

import frc.robot.Robot;

public class AutonBase {
    //6.6 per foot for encoders
    private boolean finished;
    private double power;
    public AutonBase(double pow) {
        finished = false;
        Robot.drive.resetEncoders();
        power = pow;
    }

    public void execute(){
        Robot.drive.drive(0, .4);
        if(Math.abs(Robot.drive.getLeftEncoder()) > 66){
            finished = true;
        }
    }

    public boolean isFinished(){
        return finished;
    }
}
