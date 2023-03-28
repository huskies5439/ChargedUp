// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class Stabiliser extends CommandBase {
  BasePilotable basePilotable;
  double voltage;

  PIDController pidBalancer;

  public Stabiliser(BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {
    pidBalancer = new PIDController(-0.085, 0, 0);
    basePilotable.setBrakeEtRampTeleop(false);

     // pid balancer
     pidBalancer.setSetpoint(0);
     pidBalancer.setTolerance(2.5);
  }

  @Override
  public void execute() {

    //Rouller pour se restabiliser si la balance bouge
    voltage = pidBalancer.calculate(basePilotable.getPitch(), 0);


    //Si on est droit, ne pas bouger
    if (pidBalancer.atSetpoint()) {
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