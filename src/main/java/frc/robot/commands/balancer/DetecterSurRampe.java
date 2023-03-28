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
  }

  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(false);
  }

  @Override
  public void execute() {
    //Avance
    basePilotable.autoConduire(voltage, voltage);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    //Arrête quand le robot commence à monter sur la pente de la balance 
    return Math.abs(basePilotable.getPitch()) > 12;
  }
}
