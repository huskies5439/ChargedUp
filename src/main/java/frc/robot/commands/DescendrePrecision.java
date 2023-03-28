// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Coude;



public class DescendrePrecision extends CommandBase { //Descendre un peu la cible pour s'ajuster avec la grille puis remonter la cible à sa position initial une fois fini
  double ciblePrecision;
  double cibleInitiale;
  Coude coude;

  public DescendrePrecision(Coude coude) {
    this.coude = coude;
  }

  @Override
  public void initialize() {
    cibleInitiale = coude.getCible();
    if (cibleInitiale > 90) { //Pour ne pas le faire si on est pas dans les airs
      ciblePrecision = cibleInitiale - 15;
      coude.setCible(ciblePrecision);
    }
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    coude.setCible(cibleInitiale);

  }

  @Override
  public boolean isFinished() {

    return false;
  }
}