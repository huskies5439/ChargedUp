// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class DetecterSurRampe extends CommandBase {
  
  BasePilotable basePilotable;
  double voltage;
 
  public DetecterSurRampe(BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    
    addRequirements(basePilotable);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    basePilotable.autoConduire(voltage, voltage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(basePilotable.getPitch()) > 12;
  }
}
