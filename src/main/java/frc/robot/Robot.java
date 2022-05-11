package frc.robot;

import frc.robot.*;
import frc.robot.Commands.*;
import frc.robot.Subsystems.*;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
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

  public static SendableChooser<String> autonChooser;
  public static SendableChooser<String> powerChooser;
  public boolean shooterFlag = false;
  AutonBase auton;

  double power = .527;//.3 is TINY shot .52 is big shot
  double rpm = 2200;

  @Override
  public void robotInit() {
    BuiltInAccelerometer acc;
    
    autonChooser = new SendableChooser<String>();
    autonChooser.setDefaultOption("DriveOffLine", "drive");
    autonChooser.addOption("Shoot1LimeLight", "shoot1");
    /*autonChooser.addOption("Shoot2TL", "shoot2curve");
    autonChooser.addOption("Shoot2TR", "shoot2straight");*/
    autonChooser.addOption("Shoot2Limelight","shoot2LimeLight");
    SmartDashboard.putData("AUTON", autonChooser);

    powerChooser = new SendableChooser<String>();
    powerChooser.setDefaultOption("High", "h");
    powerChooser.addOption("Low", "l");
    SmartDashboard.putData("power", powerChooser);


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

    CameraServer.startAutomaticCapture();
  }

  @Override
  public void teleopInit(){
    
  }

  @Override
  public void teleopPeriodic() {

    //reset encoders
      if(oi.getJoystickButtonPress(7)){
        drive.resetEncoders();
      }

    //climb.off();


    //Drive
      if(oi.getJoystickButtonPress(6)){
        drive.backwardsDrive(oi.getJoystickX(), oi.getJoystickY()*.8);
        
      } else if (oi.getTrigger(Portmap.triggerLimelight) > .05){
        //Limelight
        //if()
        vision.lightOn();
        if(Math.abs(vision.getTX()) > 1 && vision.getTV()>0){
          double normTX = vision.getTX() / 15;
          if(Math.abs(normTX) > .6){
            normTX = Math.copySign(.6, normTX);
          }

          double turnSpeed = .327 * Math.tan(1.46 * normTX);

          if(Math.abs(normTX)<.15){
            turnSpeed = Math.copySign(.15, turnSpeed);
          }
          SmartDashboard.putNumber("LimeTune", turnSpeed);
          drive.drive(turnSpeed,oi.getJoystickY()*.8);
        } else {
          drive.drive(oi.getJoystickX(), oi.getJoystickY()*.8);
        }
      } else {
        vision.lightOff();
        drive.drive(oi.getJoystickX(), oi.getJoystickY()*.8);
      }
      

    /*if(oi.getJoystickButtonPress(1f)){
      motor.forwards(.3);
    }else if (oi.getJoystickButtonPress(2)){
      motor.backwards(.3);
    } else{
      motor.stop();
    }*/



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

    
    //Shooter
      if(oi.getXboxButtonPress(8)){
        power = .33; //.327
      }else{
        power = .47; //was .54 good for fender 
        //power = .527;//was.52
        
      }

      if(oi.getXboxButtonPress(Portmap.buttonConveyorReverse) && oi.getTrigger(Portmap.triggerShooter) > .05){
        shooter.spin(-.3);
      } else if (oi.getXboxButtonPress(8) && oi.getTrigger(Portmap.triggerShooter)>.05){
        shooter.PIDShooter(1100);
      }else if(oi.getTrigger(Portmap.triggerShooter)>.05){
        if(!shooterFlag){
        //shooter.spin(power);
        shooter.PIDShooter(rpm);
          
        }
      }
      else {
        shooter.PIDShooter(0);
      } 
     /* else if(oi.getTrigger(Portmap.triggerShooter)<power){
        shooter.spin(oi.getTrigger(3));
      }
      else{
        shooter.spin(power);
      } */
    
    SmartDashboard.putNumber("tX", vision.getTX());
    SmartDashboard.putNumber("tY", vision.getTY());
    SmartDashboard.putNumber("tV", vision.getTV());

    SmartDashboard.putBoolean("intakeExtended", intake.deployed);

    SmartDashboard.putNumber("power", power);

    SmartDashboard.putNumber("LeftEncoder", drive.getLeftEncoder());
    SmartDashboard.putNumber("RightEncoder", drive.getLeftEncoder());
    SmartDashboard.putNumber("Shooter", shooter.getVel());
  }

  @Override
  public void autonomousInit(){
    
    String opt = autonChooser.getSelected();
    double pow = .33; //was .3
    if(powerChooser.getSelected().equals("h")){
      pow = .54; //was .52
    }
    if(opt.equals("shoot1")){
      auton = new AutonLimelight1Ball(pow);
      //auton = new AutonShoot1(pow);
    } /*else if (opt.equals("shoot2curve")){
      auton = new AutonShoot2Curve(pow);
    }else if (opt.equals("shoot2straight")){
      auton = new AutonShoot2Straight(pow);
    }*/ else if (opt.equals("shoot2LimeLight")){
      auton = new AutonLimelight(pow);
    }else {
      auton = new AutonLimelight(pow);
    }
  }

  @Override
  public void autonomousPeriodic(){
    SmartDashboard.putNumber("LeftEncoder", drive.getLeftEncoder());
    SmartDashboard.putNumber("RightEncoder", drive.getLeftEncoder());
    SmartDashboard.putNumber("Shooter", shooter.getVel());
    if(!auton.isFinished()){
      auton.execute();
    }
  }
}