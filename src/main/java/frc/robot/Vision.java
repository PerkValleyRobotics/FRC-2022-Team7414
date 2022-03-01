package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision{

  NetworkTable lime;
  
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry tv;

  public Vision() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault(); 
    lime = inst.getTable("limelight");

    tx = lime.getEntry("tx");
    ty = lime.getEntry("ty");
    tv = lime.getEntry("tv");
    
    lightOff();
  }

  public double getTX(){
    return tx.getDouble(0.0);
  }

  public double getTY(){
    return ty.getDouble(0.0);
  }

  public double getTV(){
    return tv.getDouble(0.0);
  }

  public void lightOn(){
    lime.getEntry("ledMode").setNumber(3);
  }

  public void lightOff(){
    lime.getEntry("ledMode").setNumber(1);
  }
}