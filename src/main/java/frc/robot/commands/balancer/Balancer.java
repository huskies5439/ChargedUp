// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.AvancerDistanceSimple;
import frc.robot.subsystems.BasePilotable;

public class Balancer extends SequentialCommandGroup {
  

  public Balancer(BasePilotable basePilotable) {
    double voltage = 3;
    addCommands(
      //Avancer jusqu'à ce qu'on monte sur la balance
      new DetecterSurRampe(voltage, basePilotable),

     //Monter 1.25 m sur la balance (point de chute)
      new AvancerDistanceSimple(1.25, voltage, basePilotable),
      
      //Stabilise le robot
      new Stabiliser(basePilotable)
    );
  }
}