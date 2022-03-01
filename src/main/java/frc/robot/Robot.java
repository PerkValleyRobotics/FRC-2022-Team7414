package frc.robot;

import frc.robot.*;
import frc.robot.Commands.AutonBase;
import frc.robot.Subsystems.*;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.cameraserver.CameraServer;


public class Robot extends TimedRobot {
  
  public static Compressor compressor;

  public static DriveTrain drive;
  public static OIHandler oi;
  public static Test motor;

  public static Vision vision;
  public static CameraServer server;

  public static Shooter shooter;
  public static Intake intake;
  public static Conveyor conveyor;

  public static Climber climb;

  public static boolean enableCompressor = false;

  AutonBase auton;

double power = .6;//.38 is TINY shot .6 is big shot
  

  @Override
  public void robotInit() {
    oi = new OIHandler();
    
    drive = new DriveTrain(); 

    intake = new Intake();
    conveyor = new Conveyor();
    shooter = new Shooter();
    
    climb = new Climber();

    //motor = new Test();
    vision = new Vision();

    vision.lightOff();
    
    climb.retract();
    intake.retract();
  }

  @Override
  public void teleopInit(){
    
  }

  @Override
  public void teleopPeriodic() {
    //climb.off();


    //Drive
      if(!oi.getJoystickButtonPress(6)){
        drive.drive(oi.getJoystickX(), oi.getJoystickY());
      } else {
        drive.backwardsDrive(oi.getJoystickX(), oi.getJoystickY());
      }
      

    /*if(oi.getJoystickButtonPress(1)){
      motor.forwards(.3);
    }else if (oi.getJoystickButtonPress(2)){
      motor.backwards(.3);
    } else{
      motor.stop();
    }*/

    //SmartDashboard.putNumber("LeftEncoder", Liam.getLeftEncoder());


    //Climb
      if(oi.onJoystickButtonPress(Portmap.buttonClimbExtend)){
        climb.extend();
        SmartDashboard.putBoolean("ClimbExtended: ", true);
      } 
      if (oi.onJoystickButtonRelease(Portmap.buttonClimbExtend)) {
        climb.retract();
        SmartDashboard.putBoolean("ClimbExtended: ", false);
      }

    //Conveyor
      //top wheel
      if(oi.getXboxButtonPress(Portmap.buttonTopConveyor)){ 
        if(!oi.getXboxButtonPress(Portmap.buttonConveyorReverse)){ //checks if not reverse button pressed
          conveyor.topForwards();
        } else {
          conveyor.topReverse();
        }
        
      } else {
        conveyor.topStop();
      }
      //bottom wheel
      if (oi.getXboxButtonPress(Portmap.buttonBottomConveyor)){
        if(!oi.getXboxButtonPress(Portmap.buttonConveyorReverse)){
          conveyor.bottomForwards();
          intake.boosterForwards();
        } else {
          conveyor.bottomReverse();
          intake.boosterReverse();
        }
        
      } else {
        conveyor.bottomStop();
      }
    
    //Intake
      //Pistons
        if(oi.onJoystickButtonPress(Portmap.buttonIntakeToggle)){
          intake.togglePistons();
        }

        /*if(oi.onJoystickButtonRelease(Portmap.buttonIntakeToggle) && intake.deployed){
          intake.decompress();
        } */
      
      //Motors
        if(oi.getJoystickButtonPress(Portmap.buttonIntakeForwards)){
          intake.allForwards();
        } else if(oi.getJoystickButtonPress(Portmap.buttonIntakeBackwards)){
          intake.allReverse();
        } else {
          intake.intakeStop();
        }
        //turns off booster wheel if not needed
        if(!oi.getXboxButtonPress(Portmap.buttonBottomConveyor) && !oi.getJoystickButtonPress(Portmap.buttonIntakeForwards)){
          intake.boosterStop();
        }

    //Limelight
      if(oi.getTrigger(Portmap.triggerLimelight) > .05){
        vision.lightOn();
         //change to regression model
      } else {
        vision.lightOff();
      }
    //Shooter
      if(oi.getXboxButtonPress(8)){
        power=.32;
      }else{
        power = .527;
      }
      
      if(oi.getXboxButtonPress(Portmap.buttonConveyorReverse) && oi.getTrigger(Portmap.triggerShooter) > .05){
        shooter.spin(-.3);
      }else if(oi.getTrigger(Portmap.triggerShooter)<power){
        shooter.spin(oi.getTrigger(3));
      }
      else{
        shooter.spin(power);
      }
    
    

    SmartDashboard.putNumber("tX", vision.getTX());
    SmartDashboard.putNumber("tY", vision.getTY());
    SmartDashboard.putNumber("tV", vision.getTV());

    SmartDashboard.putBoolean("intakeExtended", intake.deployed);

    SmartDashboard.putNumber("power", power);
  }

  @Override
  public void autonomousInit(){
    auton = new AutonBase();
  }

  @Override
  public void autonomousPeriodic(){
    if(!auton.isFinished()){
      auton.execute();
    }
  }
}
