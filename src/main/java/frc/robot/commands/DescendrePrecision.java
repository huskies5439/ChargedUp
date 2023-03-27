// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Coude;

public class DescendrePrecision extends CommandBase {
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

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    coude.setCible(cibleInitiale);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;
  }
}