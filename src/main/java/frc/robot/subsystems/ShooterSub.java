package frc.robot.subsystems;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ShooterConstants;

public class ShooterSub extends SubsystemBase {
   private CANSparkMax upperShooterMotor = new CANSparkMax(ShooterConstants.kUpperShooterMotorID, MotorType.kBrushless);

    private CANSparkMax lowerShooterMotor = new CANSparkMax(ShooterConstants.kLowerShooterPort,MotorType.kBrushless);

    private RelativeEncoder upperShooterMotorEncoder = upperShooterMotor.getEncoder();;
    private RelativeEncoder lowerShooterMotorEncoder = lowerShooterMotor.getEncoder();


    
    public void shooterSub(){
 

    }
    public void setShooterSpeed( double shooterSpeed){

            upperShooterMotor.set(-shooterSpeed);
            lowerShooterMotor.set(-shooterSpeed); // hard negative
        
        
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Upper Shooter Speed", upperShooterMotorEncoder.getVelocity());
        SmartDashboard.putNumber("Lower Shooter Speed", lowerShooterMotorEncoder.getVelocity());

            
    }
}
