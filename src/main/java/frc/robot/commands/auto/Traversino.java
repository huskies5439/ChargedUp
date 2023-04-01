// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.balancer.Balancer;
import frc.robot.commands.balancer.TraverserBalance;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class Traversino extends SequentialCommandGroup {
  
  public Traversino(boolean cone, BasePilotable basePilotable, Echelle echelle, Coude coude, Pince pince) {
    addCommands(
      /*new AutoPlacer(cone, echelle, coude, pince, basePilotable), */
      new TournerPID(180, basePilotable),
      new TraverserBalance(true, basePilotable),
      new WaitCommand(0.5),
      new TournerPID(0, basePilotable),
      new Balancer(true, basePilotable)
    );
  }
}
