// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Cible;
import frc.robot.commands.BrasAutoAvecCheck;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoPlacerSol extends SequentialCommandGroup {
  /** Creates a new AutoPlacerSol. */
  public AutoPlacerSol(Echelle echelle, Coude coude, Pince pince) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      //Fermé la pince
      new InstantCommand(pince::fermer).andThen(new WaitCommand(0.2)),
      
      //Lever le Coude
      new BrasAutoAvecCheck(Cible.kSol, echelle, coude),

      //Ouvrir la pince
      new InstantCommand(pince::ouvrir).andThen(new WaitCommand(0.2)),

      //baisser le coude
      new BrasAutoAvecCheck(Cible.kRentrer, echelle, coude)
    );
  }
}
