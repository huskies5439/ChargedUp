// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;

public class BrasEnPosition extends ParallelCommandGroup {
  
  public BrasEnPosition(Echelle echelle, Coude coude) {
    addCommands(
      new WaitUntilCommand(echelle::getCible),
      new WaitUntilCommand(coude::getCible)

    );
  }
}