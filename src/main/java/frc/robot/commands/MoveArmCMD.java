package frc.robot.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.ArmSub;

public class MoveArmCMD extends Command{
    private final ArmSub armSub; 
    private final CANSparkMax armMotor;
    private final PIDController armController;
    private final RelativeEncoder armEncoder;

    private final int setpoint = 100;
    
    MoveArmCMD(ArmSub armSub){
        this.armSub = armSub;
        armController = armSub.getPIDController();
        armMotor = armSub.getMotor();
        armEncoder = armMotor.getEncoder();
        
    }

    @Override
    public void initialize(){
        armMotor.stopMotor();
        armMotor.set(0);
    }

    @Override
    public void execute(){
        double output = armController.calculate(armEncoder.getPosition(), setpoint);
        armMotor.set(output);
        

    }
    @Override
    public boolean isFinished(){
        return false;
    }
}