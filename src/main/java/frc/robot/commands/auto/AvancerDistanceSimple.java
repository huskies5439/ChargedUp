// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BasePilotableConstantes;
import frc.robot.subsystems.BasePilotable;

public class AvancerDistanceSimple extends CommandBase {
  double distance;
  BasePilotable basePilotable;
  double positionDepart;
  double voltage;
  
  public AvancerDistanceSimple(double distance, BasePilotable basePilotable) {
    this.distance = distance;
    this.basePilotable = basePilotable;
    addRequirements(basePilotable);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    positionDepart = basePilotable.getPosition();
    basePilotable.setBrakeEtRampTeleop(false);
    voltage = BasePilotableConstantes.voltageAvancerLentement*Math.signum(distance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    basePilotable.autoConduire(voltage, voltage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(basePilotable.getPosition() - positionDepart) > Math.abs(distance);
  }
}
