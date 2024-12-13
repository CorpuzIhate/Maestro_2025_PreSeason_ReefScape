
package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class ArmSub extends SubsystemBase{
    private final CANSparkMax armMotor;
    private final PIDController armController;


    public ArmSub(int armID, PIDController armController){
        this.armMotor = new CANSparkMax(armID, MotorType.kBrushless);
        this.armController = armController;

    }
    public CANSparkMax getMotor(){
        return armMotor;
    }
    public PIDController getPIDController(){
        return armController;
    }
    

}