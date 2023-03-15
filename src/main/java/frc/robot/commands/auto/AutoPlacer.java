// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Cible;
import frc.robot.commands.BrasAuto;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoPlacer extends SequentialCommandGroup {
  /** Creates a new AutoPlacer. */
  public AutoPlacer(Echelle echelle, Coude coude, Pince pince) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(pince::fermerPiston),
      new WaitCommand(0.2),
      new BrasAuto(Cible.kHaut, coude, echelle),
      new BrasEnPosition(echelle, coude),
      new InstantCommand(pince::ouvrirPiston),
      new BrasAuto(Cible.kBas, coude, echelle)

    );
  }
}
