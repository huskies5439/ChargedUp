// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.balancer.Balancer;
import frc.robot.subsystems.BasePilotable;

public class BalancerAuto extends SequentialCommandGroup {
  
  public BalancerAuto(BasePilotable basePilotable) {
    addCommands(
      new Balancer(basePilotable, true)
    );
  }
}