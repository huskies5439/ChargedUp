// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Cible;
import frc.robot.commands.BrasAutoAvecCheck;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class AutoPlacerSol extends SequentialCommandGroup {
  
  public AutoPlacerSol(Echelle echelle, Coude coude, Pince pince) {
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