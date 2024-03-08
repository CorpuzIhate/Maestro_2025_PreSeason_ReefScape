package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.IntakeOutakeSub;


public class IndexerCMD extends Command {
    private final IndexerSub indexerSub;
    private final double indexerSpeed;




    public IndexerCMD(IndexerSub indexerSub, double indexerSpeed ){
        this.indexerSub = indexerSub;
        this.indexerSpeed = indexerSpeed;

        addRequirements(indexerSub);
    }

    @Override
    public void initialize(){
        System.out.println("IntakeCMD started!");
    }

    @Override
    public void execute() {
        indexerSub.setIndexSpeed(indexerSpeed * 0.5);
    }


    @Override
    public boolean isFinished(){
    return false;
    }
    @Override
    public void end(boolean interrupted) {
        indexerSub.setIndexSpeed(0); //defaults end to set motors to 0
    }
}

