// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.AvancerDistanceSimple;
import frc.robot.subsystems.BasePilotable;

public class Balancer extends SequentialCommandGroup {
  

  public Balancer(boolean forward, BasePilotable basePilotable) {
    double distance = 1.4;
    if(!forward){
      distance=-distance;
    }



      
 
    addCommands(
      //Avancer jusqu'à ce qu'on monte sur la balance
      new DetecterSurRampe(forward, basePilotable),

      //Monter 1.25 m sur la balance (point de chute)
      
      
      new AvancerDistanceSimple(distance, basePilotable),
      
      //Stabilise le robot
      new Stabiliser(basePilotable)
    );
  }
}