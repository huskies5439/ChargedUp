// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;


public class BrasAutoAvecCheck extends SequentialCommandGroup {
  public BrasAutoAvecCheck(double[] cible, Echelle echelle, Coude coude) {
   

    addCommands(
      new BrasAuto(cible, echelle, coude),
      new BrasEnPosition(echelle, coude)

    );
  }
}
