// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BasePilotableConstants;
import frc.robot.subsystems.BasePilotable;

public class Conduire extends CommandBase {
BasePilotable basePilotable;
DoubleSupplier avancer;
DoubleSupplier tourner;

  public Conduire(DoubleSupplier avancer, DoubleSupplier tourner, BasePilotable basePilotable) {
    
    this.avancer = avancer;
    this.tourner = tourner;
    this.basePilotable = basePilotable;
    addRequirements(basePilotable);
  }
  
  @Override
  public void initialize() {
    basePilotable.setBrake(false);
    basePilotable.setRamp(BasePilotableConstants.rampTeleop);
  }
  
  @Override
  public void execute() {
    
    basePilotable.conduire(avancer.getAsDouble(), tourner.getAsDouble());

    
    if(! basePilotable.getIsHighGear() && Math.abs(basePilotable.getVitesse())>1.65){
      basePilotable.highGear();

    }
    else if(basePilotable.getIsHighGear() && Math.abs(basePilotable.getVitesse()) <1.25){

      basePilotable.lowGear();
    }
  }
  
  @Override
  public void end(boolean interrupted) {}
  
  @Override
  public boolean isFinished() {
    return false;
  }
}