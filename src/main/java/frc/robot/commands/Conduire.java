// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;

public class Conduire extends CommandBase {

  BasePilotable basePilotable;
  Coude coude;
  DoubleSupplier avancer;
  DoubleSupplier tourner;
  double vx;
  double vz;

  public Conduire(DoubleSupplier avancer, DoubleSupplier tourner, BasePilotable basePilotable, Coude coude) {
    this.avancer = avancer;
    this.tourner = tourner;
    this.basePilotable = basePilotable;
    this.coude = coude;

    addRequirements(basePilotable);
  }
  
  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(true);
  }
  
  @Override
  public void execute() {
    vx = avancer.getAsDouble();
    vz = tourner.getAsDouble();

    /*
    if (coude.getPosition() > 90) {
      vx *= 0.5;
      vz *= 0.3;
    }
    */
    

    basePilotable.conduire(vx, vz);

    //Changer les vitesses
    if(! basePilotable.getIsHighGear() && Math.abs(basePilotable.getVitesse()) > 1.65) {
      basePilotable.highGear();

    }
    else if(basePilotable.getIsHighGear() && Math.abs(basePilotable.getVitesse()) < 1.25) {

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