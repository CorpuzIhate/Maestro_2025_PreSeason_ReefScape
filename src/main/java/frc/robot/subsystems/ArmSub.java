
package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.google.flatbuffers.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;




public class ArmSub extends SubsystemBase{
    private CANSparkMax armMotor = new CANSparkMax(ArmConstants.kArmMotorPort, MotorType.kBrushless);
    private DutyCycleEncoder m_armEncoder = new DutyCycleEncoder(0);
     ;
    private PIDController armController = new PIDController(
        ArmConstants.kP, 
        ArmConstants.kI, 
        ArmConstants.kD);


    public CANSparkMax getMotor(){
        return armMotor;
    }
        public DutyCycleEncoder getGetArmEncoder(){
        return m_armEncoder;
    }
    public PIDController getPIDController(){
        return armController;
    }
    
    public void setArmSpeed(double speed){
        armMotor.set(speed);
    }

}