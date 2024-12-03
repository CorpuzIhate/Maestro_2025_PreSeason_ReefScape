package frc.robot.auto.AutosToSelect;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.auto.auto_commands.InitalizeShooterAutoCMD;
import frc.robot.auto.auto_commands.ShootAutoCMD;
public class JustShootAuto extends SequentialCommandGroup {
    public JustShootAuto(RobotContainer robot){ 
        addCommands( new InitalizeShooterAutoCMD(robot.getShooterSub(), 2));
        addCommands(new ShootAutoCMD(robot.getShooterSub(),  1.5, robot.getIndexerSub()));
    }

    
}
