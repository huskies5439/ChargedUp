// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class AncrerBalance extends CommandBase {
  /** Creates a new AncrerBalance. */
BasePilotable basePilotable;
DoubleSupplier vx;
DoubleSupplier vz;

  public AncrerBalance(DoubleSupplier vx, DoubleSupplier vz, BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    this.vx = vx;
    this.vz = vz;
    addRequirements(basePilotable);
    // Use addRequirements() here to declare subsystem dependencies.
  }
  
  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(false);
  }
  
  @Override
  public void execute() {
    basePilotable.conduire(0.5*vx.getAsDouble(), 0.8*vz.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}