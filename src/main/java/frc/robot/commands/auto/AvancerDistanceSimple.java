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

  @Override
  public void initialize() {
    positionDepart = basePilotable.getPosition();
    basePilotable.setBrakeEtRampTeleop(false);
    voltage = BasePilotableConstantes.voltageAvancerLentement*Math.signum(distance);
  }

  @Override
  public void execute() {
    basePilotable.autoConduire(voltage, voltage);
  }

  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();
  }

  @Override
  public boolean isFinished() {
    //Arrêter quand les roues ont tourner la distance demander
    return Math.abs(basePilotable.getPosition() - positionDepart) > Math.abs(distance);
  }
}
