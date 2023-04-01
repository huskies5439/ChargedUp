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
  }
  
  @Override
  public void initialize() {
    //Se mettre en brake pour ne pas gisser de la balance
    basePilotable.setBrakeEtRampTeleop(false);
  }
  
  @Override
  public void execute() {
    //Baisser la vitesse selon la valeur donner pour se stabiliser en autonome
    basePilotable.conduire(0.5*vx.getAsDouble(), 0.65*vz.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}