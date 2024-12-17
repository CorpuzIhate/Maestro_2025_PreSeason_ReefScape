package frc.robot.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.ArmSub;

public class MoveArmCMD extends Command{
    public final ArmSub armSub; 
    public final CANSparkMax armMotor;
    public final PIDController armController;
    public final RelativeEncoder armEncoder;

    private final int setpoint = 300;
    
    public MoveArmCMD(ArmSub armSub){
        this.armSub = armSub;
        armController = armSub.getPIDController();
        armMotor = armSub.getMotor();
        armEncoder = armMotor.getEncoder();
        addRequirements(armSub);
        
    }

    @Override
    public void initialize(){
        armMotor.stopMotor();
        armMotor.set(0);
        
    }

    @Override
    public void execute(){
        double output = armController.calculate(armEncoder.getPosition(), setpoint);
        armSub.setArmSpeed(output);
        
    }
      // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


}