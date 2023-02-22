// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Conduire;
import frc.robot.commands.PincerAuto;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Bras;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Pince;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final BasePilotable basePilotable = new BasePilotable();
  private final Bras bras = new Bras();
  private final Pince pince = new Pince();
  private final Coude coude = new Coude();
  CommandXboxController pilote = new CommandXboxController(0);
  
  
  public RobotContainer() {
    
    configureBindings();
    
    basePilotable.setDefaultCommand(new Conduire(pilote::getLeftY,pilote::getRightX, basePilotable));
    //pince.setDefaultCommand(new PincerAuto(pince)); Remettre dans le code quand les capteurs seront posés
    //Placer BrasBackground ici
  }

  private void configureBindings() {
    pilote.povUp().whileTrue(new StartEndCommand(bras::allonger,bras::stop , bras));
    pilote.povDown().whileTrue(new StartEndCommand(bras::retracter,bras::stop , bras));
    pilote.povRight().whileTrue(new StartEndCommand(coude::monter, coude::stop, coude));
    pilote.povLeft().whileTrue(new StartEndCommand(coude::descendre, coude::stop, coude));
    //Sur bouton x : le bras va à 0 m
    //Sur bouton y : le bras va à 30 cm de longueur (0.3 m)


    pilote.rightBumper().onTrue(new InstantCommand(pince::togglePince, pince)); //Pas un toggle car cela désactiverais le PincerAuto qui doit fonctionner en permanence
  }

  public Command getAutonomousCommand() {
    return new RunCommand(() -> basePilotable.autoConduire(5, 5), basePilotable);
  }
}
