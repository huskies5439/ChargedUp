// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class DetecterSurRampe extends CommandBase {
  /** Creates a new DetecterSurRampe. */
  BasePilotable basePilotable;
  double voltage;
  boolean reculer;

  boolean detecterSurRampe;

  public DetecterSurRampe(double voltage, boolean reculer, BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    this.voltage = voltage;
    
    if (reculer == true) {
      voltage *= -1;
    }
    addRequirements(basePilotable);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(false);
    detecterSurRampe = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    basePilotable.autoConduire(voltage, voltage);
    SmartDashboard.putBoolean("DetecterSurRampe", detecterSurRampe);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    detecterSurRampe = false;
    SmartDashboard.putBoolean("DetecterSurRampe", detecterSurRampe);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(basePilotable.getPitch()) > 12;
  }
}
