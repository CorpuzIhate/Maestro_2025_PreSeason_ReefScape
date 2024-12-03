package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;




public class SwerveSub extends SubsystemBase {
    public final SwerveModule frontRight = new SwerveModule(
            DriveConstants.kFrontRightDriveMotorPort, // 7
            DriveConstants.kFrontRightTurningMotorPort,
            DriveConstants.kFrontRightDriveEncoderReversed,
            DriveConstants.kFrontRightTurningEncoderReversed,
            DriveConstants.kFrontRightDriveAbsoluteEncoderPort,
            DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontRightDriveAbsoluteEncoderReversed);

    public final SwerveModule frontLeft = new SwerveModule(
            DriveConstants.kFrontLeftDriveMotorPort,
            DriveConstants.kFrontLeftTurningMotorPort,
            DriveConstants.kFrontLeftDriveEncoderReversed,
            DriveConstants.kFrontLeftTurningEncoderReversed,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderReversed);

    public final SwerveModule backRight = new SwerveModule(
            DriveConstants.kBackRightDriveMotorPort,
            DriveConstants.kBackRightTurningMotorPort,
            DriveConstants.kBackRightDriveEncoderReversed,
            DriveConstants.kBackRightTurningEncoderReversed,
            DriveConstants.kBackRightDriveAbsoluteEncoderPort,
            DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackRightDriveAbsoluteEncoderReversed);

    public final SwerveModule backLeft = new SwerveModule(
            DriveConstants.kBackLeftDriveMotorPort,
            DriveConstants.kBackLeftTurningMotorPort,
            DriveConstants.kBackLeftDriveEncoderReversed,
            DriveConstants.kBackLeftTurningEncoderReversed,
            DriveConstants.kBackLeftDriveAbsoluteEncoderPort,
            DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackLeftDriveAbsoluteEncoderReversed);



    private final SwerveModule swerveModules[] = new SwerveModule[]{
        frontLeft,frontRight,
        backLeft, backRight
    };




    
    private final AHRS gyro = new AHRS(SerialPort.Port.kUSB1);
    private final SwerveDriveOdometry odometer = new SwerveDriveOdometry(DriveConstants.kDriveKinematics,
     getRotation2d(), getModulePositionsAuto());




    public SwerveSub(){



 new Thread(() -> {  /// try catch function is a fancy if else statement
        try{              // it tries to run a thread of resseting the gryo but if it exception e happens it stops 
            Thread.sleep(1000);
        }catch (Exception e){
        }
        }).start();

        zeroHeading();
       AutoBuilder.configureHolonomic(
            this::getPose, // Robot pose supplier
            this::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
            this::getSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            this::driveRobotRelative, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
            new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
                    new PIDConstants(1.0, 0.0, 0.0), // Translation PID constants
                    new PIDConstants(1.0, 0.0, 0.0), // Rotation PID constants
                    2.5, // Max module speed, in m/s
                    0.46, // Drive base radius in meters. Distance from robot center to furthest module.
                    new ReplanningConfig() // Default path replanning config. See the API for the options here
            ),
            () -> {
              // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            this // Reference to this subsystem to set requirements
    );  
        

    
    }
    @Override
    public void periodic(){
 

        SmartDashboard.putNumber("robot Heading", getHeading());



        SmartDashboard.putNumber("SwerveModuleTurningPostions [" + 1 + "]" ,  frontLeft.getTurningPositon());
        SmartDashboard.putNumber("SwerveModuleTurningPostions [" + 2 + "]" ,  frontRight.getTurningPositon());
        SmartDashboard.putNumber("SwerveModuleTurningPostions [" + 3 + "]" ,  backLeft.getTurningPositon());
        SmartDashboard.putNumber("SwerveModuleTurningPostions [" + 4 + "]" ,  backRight.getTurningPositon());





      




        frontLeft.sendToDashboard();
        frontRight.sendToDashboard();
        backLeft.sendToDashboard();
        backRight.sendToDashboard();
    }

     public ChassisSpeeds getSpeeds() {
         return DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates()); 
    }


 




    public void setModuleStates(SwerveModuleState[] desiredStates){

        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
    // proportaionally decreases the change the speeds so driver always has control of robot
        frontRight.setDesiredState(desiredStates[0]);        
        frontLeft.setDesiredState(desiredStates[1]); //1 
        backRight.setDesiredState(desiredStates[2]); //2                     
        backLeft.setDesiredState(desiredStates[3]); // 3

    
    }


    // get positions
        public SwerveModulePosition[] getModulePositionsAuto() {
        SwerveModulePosition[] positions = new SwerveModulePosition[swerveModules.length];
        for (int i = 0; i < swerveModules.length; i++) {
        positions[i] = swerveModules[i].getSwerveModulePosition();
        }
        return positions;
    }

    public void zeroHeading(){
        gyro.reset();
    }

    public double getHeading(){
        return Math.IEEEremainder(-gyro.getAngle(), 360); //puts the value between 0 and 360 because gryo is naturally continous
    }

    public Rotation2d getRotation2d(){
        return Rotation2d.fromDegrees(getHeading());
    } // converts into Rotation2d
     public Pose2d getPose(){

        return odometer.getPoseMeters();

    }
    public void resetOdometry(Pose2d pose){
        odometer.resetPosition(getRotation2d(), getModulePositionsAuto(), pose);
    }
    

      public SwerveModuleState[] getModuleStates() {
        SwerveModuleState[] states = new SwerveModuleState[swerveModules.length];
        for (int i = 0; i < swerveModules.length; i++) {
          states[i] = swerveModules[i].getState();
        }
        return states;
      }


      public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds){
        ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);

        SwerveModuleState[] targetStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(targetSpeeds);
        setModuleStates(targetStates);
    }




    public void stopModules(){
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();

    }






}