// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Coude;

public class DescendrePrecision extends CommandBase {
  /** Creates a new DescendrePrecision. */
  double ciblePrecision;
  double cibleInitiale;
  Coude coude;
  public DescendrePrecision(Coude coude) {
    this.coude = coude;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    cibleInitiale = coude.getCible();
    ciblePrecision = cibleInitiale-15;
    coude.setCible(ciblePrecision);

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
