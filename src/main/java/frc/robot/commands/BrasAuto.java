// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;

public class BrasAuto extends ParallelCommandGroup {

  public BrasAuto(double[] cible, Coude coude, Echelle echelle) {
    addCommands(
      new InstantCommand(()->coude.setCible(cible[0])),
      new InstantCommand(()->echelle.setCible(cible[1]))
    );
  }
}