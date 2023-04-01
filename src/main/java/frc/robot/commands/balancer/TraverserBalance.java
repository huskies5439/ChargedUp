// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.AvancerDistanceSimple;
import frc.robot.subsystems.BasePilotable;

public class TraverserBalance extends SequentialCommandGroup {
  

  public TraverserBalance(boolean forward, BasePilotable basePilotable) {
    double distance = 4;
    if(!forward) {
      distance=-distance;
    }

    addCommands(
      //Avancer jusqu'à ce qu'on monte sur la balance
      new DetecterSurRampe(forward, basePilotable),

      //Avancer d'une certaine distance par dessu la balance.
      new AvancerDistanceSimple(distance, basePilotable)
    );
  }
}