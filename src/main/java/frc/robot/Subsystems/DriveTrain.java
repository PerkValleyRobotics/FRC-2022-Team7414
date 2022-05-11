package frc.robot.Subsystems;

import frc.robot.Portmap;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveTrain{ //extends SubsystemBase{
    CANSparkMax left1;
	CANSparkMax left2;
	CANSparkMax right1;
	CANSparkMax right2;

	RelativeEncoder leftEncoder;
	RelativeEncoder rightEncoder;

    MotorControllerGroup left;
	MotorControllerGroup right;

	DifferentialDrive diffDrive;

	double speed = 0;

    public DriveTrain() {

		left1 = new CANSparkMax(Portmap.CAN_left1, MotorType.kBrushless);
		left2 = new CANSparkMax(Portmap.CAN_left2, MotorType.kBrushless);
		right1 = new CANSparkMax(Portmap.CAN_right1, MotorType.kBrushless);
		right2 = new CANSparkMax(Portmap.CAN_right2, MotorType.kBrushless);

		left = new MotorControllerGroup(left1, left2);
		right = new MotorControllerGroup(right1, right2);
		
		right.setInverted(true);

		diffDrive = new DifferentialDrive(left, right);

		leftEncoder = left1.getEncoder();
		rightEncoder = right1.getEncoder();

		leftEncoder.setPosition(0);
		rightEncoder.setPosition(0);

	}

    public void drive(double x, double y){
		
        diffDrive.arcadeDrive(y, x);
	}

	public void backwardsDrive(double x, double y){
		diffDrive.arcadeDrive(-y, x);
	}

	
	public void straightDrive(){
        diffDrive.arcadeDrive(.5, 0);
	}

	public double getLeftEncoder(){
		return leftEncoder.getPosition();
	}

	public double getRightEncoder(){
		return rightEncoder.getPosition();
	}

	public void resetEncoders(){
		rightEncoder.setPosition(0);
		leftEncoder.setPosition(0);
	}

	/*public void initDefaultCommand() {
		setDefaultCommand(new TeleopDrive());
	}*/
}
