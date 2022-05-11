    package frc.robot;

public class Portmap {
    //Controllers and buttons
        //Joystick
            public static int joystickPort = 1;

            public static int buttonIntakeToggle = 5; //top left on joystick
            
            public static int buttonIntakeForwards = 1; //trigger
            public static int buttonIntakeBackwards = 2; //side button

            public static int buttonClimbExtend = 3; // bottom left on joystick


        
        //Xbox
            public static int xboxPort = 0;

            public static int buttonTopConveyor = 6; // right bumper
            public static int buttonBottomConveyor = 5; // left bumper
            public static int buttonConveyorReverse = 7; //back button

            public static int triggerShooter = 3; //right trigger
            public static int triggerLimelight = 2; // left trigger



    //Subsystems
        //Conveyor
            public static int conveyorBottom = 0;//PWM ON ROBORIO
            public static int conveyorTop = 1;

        //Intake
            public static int intake = 2; //PWM on ROBORIO
            public static int boosterWheel = 3;

            public static int PCM_intakeExtend = 7;//PCM 6/7
            public static int PCM_intakeRetract = 6;
            
        //DriveTrain
            public static int CAN_left1 = 1;
            public static int CAN_left2 = 2;
            public static int CAN_right1 = 3;
            public static int CAN_right2 = 4;

        //Climb
            public static int PCM_ClimbExtend = 3;
            public static int PCM_ClimbRetract = 5;
        
        //Shooter
            public static int CAN_SHOOT = 5;

}
