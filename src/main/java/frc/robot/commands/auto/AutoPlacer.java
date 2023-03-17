// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Cible;
import frc.robot.commands.BrasAuto;
import frc.robot.commands.BrasEnPosition;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class AutoPlacer extends SequentialCommandGroup {
  
  public AutoPlacer(Echelle echelle, Coude coude, Pince pince) {
    
    addCommands(
      new InstantCommand(pince::fermer),
      new WaitCommand(0.2),
      new BrasAuto(Cible.kHaut, coude, echelle),
      new BrasEnPosition(echelle, coude),
      new InstantCommand(pince::ouvrir),
      new BrasAuto(Cible.kBas, coude, echelle)
    );
  }
}