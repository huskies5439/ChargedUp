// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.blalancer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class Stabiliser extends CommandBase {
  BasePilotable basePilotable;
  double voltage;

  public Stabiliser(BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(false);
  }

  @Override
  public void execute() {
    voltage = basePilotable.voltagePIDBalancer();

    if (basePilotable.isBalancer()) {
      voltage = 0;
    }

    basePilotable.autoConduire(voltage, voltage);
  }
  
  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}