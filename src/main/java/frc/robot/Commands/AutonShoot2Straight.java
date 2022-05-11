package frc.robot.Commands;

import frc.robot.Robot;

public class AutonShoot2Straight extends AutonBase{
    private int flag = 0;
    private double time;

    private boolean finished;
    private double power;

    public AutonShoot2Straight(double pow) {
        super(pow);
        finished = false;
        Robot.drive.resetEncoders();
        time = System.currentTimeMillis();
        power = pow;

    }

    @Override
    public void execute(){

        if(flag ==0){// drop intake
            Robot.drive.resetEncoders();
            Robot.intake.extend();
            flag++;
        } else if (flag == 1){// drive and pick up ball
            Robot.drive.drive(0, .4);//4
            Robot.intake.allForwards();
            Robot.conveyor.bottomForwards();
            Robot.shooter.PIDShooter(2200);
            if(System.currentTimeMillis()-time >= 2500){
                Robot.conveyor.stop();
                Robot.drive.resetEncoders();
                flag++;
                //Robot.vision.lightOn();
                time = System.currentTimeMillis();
            }
        }else if (flag ==2){//drive back
            Robot.shooter.PIDShooter(2200);

            Robot.drive.backwardsDrive(.17, .3);//.15,4
            Robot.intake.allForwards();
            Robot.conveyor.bottomForwards();
            if(System.currentTimeMillis()-time >= 2500){
                Robot.conveyor.bottomStop();
                flag++;
                time = System.currentTimeMillis();
            }
        }if(flag ==3){ //spin up(already done)
            Robot.shooter.PIDShooter(2200);

            if(System.currentTimeMillis() - time >= 1000){
                Robot.intake.stop();
                flag++;
                
                Robot.drive.resetEncoders();
                time = System.currentTimeMillis();
            }
        } else if (flag == 4){
            Robot.shooter.PIDShooter(2200);
            Robot.conveyor.topForwards();
            if(System.currentTimeMillis() - time >= 2000){
                Robot.intake.stop();
                flag++;
                
                Robot.drive.resetEncoders();
                time = System.currentTimeMillis();
            }
        } else if (flag == 5){
            Robot.shooter.PIDShooter(2200);//2200
            Robot.conveyor.allForwards();
            Robot.conveyor.bottomForwards();
            Robot.intake.allForwards();
            if(System.currentTimeMillis() - time >= 1000){
                Robot.shooter.stop();
                Robot.shooter.PIDShooter(0);
                Robot.conveyor.stop();
                Robot.intake.stop();
                flag++;
                
                Robot.drive.resetEncoders();
                time = System.currentTimeMillis();
            }
        } else if (flag == 6){// drive and pick up ball
            Robot.drive.drive(0, .4);
            if(System.currentTimeMillis()-time >= 2000){
                flag++;
                finished = true;
            }
        }
    }

    @Override
    public boolean isFinished(){
        return finished;
    }
}
