// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Limelight;

public class UpdatePosition extends CommandBase {
  BasePilotable basePilotable;
  Limelight limelight;
  public UpdatePosition(BasePilotable basePilotable, Limelight limelight) {
    this.basePilotable= basePilotable;
    this.limelight = limelight;
    addRequirements(limelight);
  }

  @Override
  public void initialize() {
    limelight.setAlliance();
  }

  @Override
  public void execute() {
    //Ajuster notre position avec la limelight si on voit un April tag qui prend plus de 0.2% de la vision
    if(limelight.getTv() && limelight.getTa() > 0.12){
      basePilotable.addVisionMeasurement(limelight.getVisionPosition().toPose2d(), limelight.getTotalLatency()/1000.0);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}