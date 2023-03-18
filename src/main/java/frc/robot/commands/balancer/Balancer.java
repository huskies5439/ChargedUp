// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;

public class Balancer extends SequentialCommandGroup {
  
  BasePilotable basePilotable;

  public Balancer(BasePilotable basePilotable, boolean reculer) {

    addCommands(
      //Grimper sur la balance jusqu'a temp que la balance penche
      new DetecterPente(5, basePilotable,reculer),
      //Avancer Un peu pour stabiliser la balance
      new RunCommand(()-> basePilotable.autoConduire(4,4)).withTimeout(0.15),
      //Avancer jusqu'a ce que la balance se replace au milieu
      new DetecterPente(3,basePilotable, reculer),
      //Stabilise le robot
      new Stabiliser(basePilotable)
    );
  }
}