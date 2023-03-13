// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.blalancer;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class Balancer extends CommandBase {
  
  BasePilotable basePilotable;
  double anglePasse;
  double angleActuel;
  double deltaAngle;
  double voltage;
  LinearFilter filtre = LinearFilter.singlePoleIIR(0.1, 0.02);

  public Balancer(double voltage, BasePilotable basePilotable, boolean reculer) {
    this.basePilotable = basePilotable;

    if (reculer == true) {
      voltage *= -1;
    }

    this.voltage = voltage;

    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(false);
    angleActuel = 0;
    anglePasse = 0;
  }

  @Override
  public void execute() {
    angleActuel = Math.abs(basePilotable.getPitch());

    deltaAngle = angleActuel-anglePasse;

    deltaAngle = filtre.calculate(deltaAngle);

    SmartDashboard.putNumber("delta angle", deltaAngle);

    basePilotable.autoConduire(voltage, voltage);

    anglePasse = angleActuel;
  }

  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();
  }

  @Override
  public boolean isFinished() {
    return angleActuel > 12 && deltaAngle < -0.05;
  }
}