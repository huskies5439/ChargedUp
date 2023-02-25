// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Conduire;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
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
  private final Echelle echelle = new Echelle();
  private final Pince pince = new Pince();
  private final Coude coude = new Coude();
  CommandXboxController pilote = new CommandXboxController(0);
  Trigger aimantechelle = new Trigger(echelle::getDetecteurMagnetique);
  
  public RobotContainer() {
    
    configureBindings();
    
    basePilotable.setDefaultCommand(new Conduire(pilote::getLeftY,pilote::getRightX, basePilotable));
    //pince.setDefaultCommand(new PincerAuto(pince)); Remettre dans le code quand les capteurs seront posés
    echelle.setDefaultCommand(new RunCommand(echelle::pidEchelle, echelle));
    coude.setDefaultCommand(new RunCommand(coude::pidCoude, coude));
  }

  private void configureBindings() {
    pilote.povUp().whileTrue(new StartEndCommand(echelle::allonger,echelle::stop , echelle));
    pilote.povDown().whileTrue(new StartEndCommand(echelle::retracter,echelle::stop , echelle));
   pilote.povRight().whileTrue(new StartEndCommand(coude::monter, coude::stop, coude));
    pilote.povLeft().whileTrue(new StartEndCommand(coude::descendre, coude::stop, coude));
    //Sur bouton x : le bras va à 0 m
    //Sur bouton y : le bras va à 30 cm de longueur (0.3 m)
    pilote.x().onTrue(new InstantCommand(()->echelle.setCible(0)));
    pilote.y().onTrue(new InstantCommand(()->echelle.setCible(0.3)));//ne requiert pas l'echelle pas grave????
    //sur bouton a : le coude va à 0 degré
    //sur bouton b : le coude va à 90 degré
    pilote.a().onTrue(new InstantCommand(()->coude.setCible(0)));
    pilote.b().onTrue(new InstantCommand(()->coude.setCible(45)));

    //pilote.a().ontrue(conditionalcommand(commandeOnTrue : cube, commandOnFalse : cone, conditiontrue))

    //reset encodeur quand l'aimant est activer
   aimantechelle.onTrue(new InstantCommand(echelle::resetEncodeur));

    pilote.rightBumper().onTrue(new InstantCommand(pince::togglePince, pince)); //Pas un toggle car cela désactiverais le PincerAuto qui doit fonctionner en permanence
  }

  public Command getAutonomousCommand() {
    return new RunCommand(() -> basePilotable.autoConduire(5, 5), basePilotable);
  }
}
