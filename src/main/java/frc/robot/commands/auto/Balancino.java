// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TournerPID;
import frc.robot.commands.balancer.Balancer;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Balancino extends SequentialCommandGroup {
  BasePilotable basePilotable;
  public Balancino(BasePilotable basePilotable, Echelle echelle, Coude coude, Pince pince) {
    addCommands(
      new AutoPlacer(true, echelle, coude, pince, basePilotable),
      new TournerPID(180, basePilotable),
      new Balancer(basePilotable, false)
    );
  }
}
