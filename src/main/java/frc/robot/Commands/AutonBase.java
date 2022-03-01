package frc.robot.Commands;

import frc.robot.Robot;

public class AutonBase {

    private boolean finished;
    public AutonBase() {
        finished = false;
        Robot.drive.resetEncoders();
    }

    public void execute(){
        Robot.drive.drive(0, .2);
        if(Robot.drive.getLeftEncoder() > 1 || Robot.drive.getRightEncoder() > 1){
            finished = true;
        }
    }

    public boolean isFinished(){
        return finished;
    }
}
