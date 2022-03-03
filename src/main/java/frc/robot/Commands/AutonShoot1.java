package frc.robot.Commands;

import frc.robot.Robot;

public class AutonShoot1 extends AutonBase{
    
    private int flag = 0;
    private double time;

    private boolean finished;

    private double power;

    public AutonShoot1(double pow) {
        super(pow);
        finished = false;
        Robot.drive.resetEncoders();
        time = System.currentTimeMillis();
        power = pow;
    }

    @Override
    public void execute(){
        if(flag ==0){
            Robot.shooter.spin(power);//.527 higher
            if(System.currentTimeMillis() - time >= 1500){
                flag++;
            }
        } else if (flag == 1){
            Robot.shooter.spin(power);
            Robot.conveyor.allForwards();
            if(System.currentTimeMillis() - time >= 3000){
                Robot.shooter.stop();
                Robot.conveyor.stop();
                flag++;
            }
        }else if (flag >=2){
            Robot.drive.drive(0, .5);
            if(Math.abs(Robot.drive.getLeftEncoder()) > 66){
                finished = true;
            }
        }
    }

    @Override
    public boolean isFinished(){
        return finished;
    }
}
