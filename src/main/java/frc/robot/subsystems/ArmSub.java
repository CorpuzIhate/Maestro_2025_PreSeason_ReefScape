
package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.google.flatbuffers.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;




public class ArmSub extends SubsystemBase{
    private CANSparkMax armMotor = new CANSparkMax(ArmConstants.kArmMotorPort, MotorType.kBrushless);
    private PIDController armController = new PIDController(
        ArmConstants.kP, 
        ArmConstants.kI, 
        ArmConstants.kD);


    public CANSparkMax getMotor(){
        return armMotor;
    }
    public PIDController getPIDController(){
        return armController;
    }
    
    public void setArmSpeed(double speed){
        armMotor.set(speed);
    }

}