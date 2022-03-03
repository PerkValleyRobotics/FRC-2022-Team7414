package frc.robot.Commands;

import frc.robot.Robot;

public class AutonShoot3 extends AutonBase{
    
    private int flag = 0;
    private double time;

    private boolean finished;
    private double power;

    public AutonShoot3(double pow) {
        super(pow);
        finished = false;
        Robot.drive.resetEncoders();
        time = System.currentTimeMillis();
        power = pow;

    }

    @Override
    public void execute(){

        if(flag ==0){//shoot first ball
            Robot.shooter.spin(.527);
            if(System.currentTimeMillis() - time >= 2){
                flag++;
            }
        } else if (flag == 1){
            Robot.shooter.spin(.527);
            Robot.conveyor.allForwards();
            if(System.currentTimeMillis() >= 4){
                flag++;
            }
        }else if (flag ==2){//drive to 1st ball
            Robot.drive.drive(0, .2);
            Robot.intake.allForwards();
            Robot.conveyor.bottomForwards();
            if(Robot.drive.getLeftEncoder() > 1){
                flag++;
            }
        } else if (flag == 3){//turn to 2nd ball

        }
    }

    @Override
    public boolean isFinished(){
        return finished;
    }
}
