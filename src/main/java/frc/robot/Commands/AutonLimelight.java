package frc.robot.Commands;

import frc.robot.Robot;

public class AutonLimelight extends AutonBase {
    private int flag = 0;
    private double time;

    private boolean finished;
    private double power;

    public AutonLimelight(double pow) {
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
            Robot.vision.lightOn();
            if(Math.abs(Robot.vision.getTX()) > 1 && Robot.vision.getTV()>0){
                double normTX = Robot.vision.getTX() / 15;
                if(Math.abs(normTX) > .6){
                    normTX = Math.copySign(.6, normTX);
                }

                double turnSpeed = .327 * Math.tan(1.46 * normTX);

                if(Math.abs(normTX)<.15){
                    turnSpeed = Math.copySign(.15, turnSpeed);
                }
                Robot.drive.backwardsDrive(turnSpeed, .4);//.15,4
            } else {
                Robot.drive.backwardsDrive(0, .4);
            }
            Robot.intake.allForwards();
            Robot.conveyor.bottomForwards();
            if(Robot.vision.getTY()>10.75){//11
                Robot.vision.lightOff();
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
                Robot.conveyor.topStop();
                Robot.drive.resetEncoders();
                time = System.currentTimeMillis();
            }
        } else if (flag == 5){
            Robot.shooter.PIDShooter(2200);
            Robot.conveyor.bottomForwards();
            if(System.currentTimeMillis() - time >= 500){
                Robot.intake.stop();
                flag++;
                
                Robot.drive.resetEncoders();
                time = System.currentTimeMillis();
                System.out.println(Robot.shooter.getVel());
            }
        }else if (flag == 6){
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
        } else if (flag == 7){// drive away
            Robot.intake.retract();
            Robot.drive.drive(0, .4);
            if(System.currentTimeMillis()-time >= 3000){
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
