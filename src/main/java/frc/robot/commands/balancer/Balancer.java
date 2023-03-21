// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;
import frc.robot.commands.AvancerDistancePID;
import frc.robot.commands.AvancerDistanceSimple;

public class Balancer extends SequentialCommandGroup {
  
  BasePilotable basePilotable;

  public Balancer(BasePilotable basePilotable, boolean reculer) {
    double voltage = 3;
    addCommands(
      //Grimper sur la balance jusqu'a temp que la balance penche
      new DetecterSurRampe(voltage, reculer, basePilotable),

      //new RunCommand(()-> basePilotable.autoConduire(voltage,voltage)).withTimeout(1.45),
      new AvancerDistanceSimple(1.25, voltage, basePilotable),
      //Avancer jusqu'a ce que la balance se replace au milieu
      //new DetecterPenteDescendante(voltage,basePilotable, reculer),
      //Stabilise le robot
      new Stabiliser(basePilotable)
    );
  }
}