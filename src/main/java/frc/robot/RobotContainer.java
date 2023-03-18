// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Cible;
import frc.robot.commands.AvancerDistance;
import frc.robot.commands.AvancerDistanceSimple;
import frc.robot.commands.BrasAuto;
import frc.robot.commands.Conduire;
import frc.robot.commands.HomingBras;
import frc.robot.commands.PincerAuto;
import frc.robot.commands.UpdatePosition;
import frc.robot.commands.auto.AutoCubeRetourCone;
import frc.robot.commands.auto.AutoPlacer;
import frc.robot.commands.auto.AutoReculerRamsete;
import frc.robot.commands.auto.BalancerAuto;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pince;

public class RobotContainer {
  private final BasePilotable basePilotable = new BasePilotable();
  private final Echelle echelle = new Echelle();
  private final Pince pince = new Pince();
  private final Coude coude = new Coude();
  private final Limelight limelight = new Limelight();

  CommandXboxController pilote = new CommandXboxController(0);

  private final SendableChooser<Command> chooser = new SendableChooser<>();
  private final Command autoCubeRetourCone = new AutoCubeRetourCone(basePilotable, echelle, coude, pince);
  private final Command autoReculerRamsete = new AutoReculerRamsete(basePilotable);
  private final Command autoBalancer = new BalancerAuto(basePilotable);
  private final Command autoPlacerCone = new AutoPlacer(true,echelle, coude, pince, basePilotable);
  private final Command autoPlacerCube = new AutoPlacer(false,echelle, coude, pince, basePilotable);
  private final Command avancerDistance = new AvancerDistance(basePilotable, 0);
  private final Command avancerDistanceSimple = new AvancerDistanceSimple(0.3, basePilotable);

  Trigger aimantechelle = new Trigger(echelle::getDetecteurMagnetique);
  Trigger limitSwitchCoude = new Trigger(coude::getLimitSwitch);
  
  public RobotContainer() {

    SmartDashboard.putData(chooser);
    chooser.addOption("CubeRetourCone", autoCubeRetourCone);
    chooser.addOption("ReculerRamsete", autoReculerRamsete);
    chooser.addOption("Balancer", autoBalancer);
    chooser.addOption("Auto Placer Cube", autoPlacerCube);
    chooser.addOption("Auto Placer Cone", autoPlacerCone);
    chooser.addOption("Avancer Distance", avancerDistance);
    chooser.addOption("Avancer Distance Simple", avancerDistanceSimple);

    configureBindings();
    
    basePilotable.setDefaultCommand(new Conduire(pilote::getLeftY,pilote::getRightX, basePilotable));
    pince.setDefaultCommand(new PincerAuto(pince)); 
    echelle.setDefaultCommand(new RunCommand(echelle::pidEchelle, echelle));
    coude.setDefaultCommand(new RunCommand(coude::pidCoude, coude));
    limelight.setDefaultCommand(new UpdatePosition(basePilotable, limelight));
  }

  private void configureBindings() {

    //Bouton pour que le bras soit à la bonne hauteur
    pilote.a().onTrue(new BrasAuto(Cible.kBas, coude, echelle));
    pilote.b().onTrue(new BrasAuto(Cible.kMilieu, coude, echelle));
    pilote.y().onTrue(new BrasAuto(Cible.kHaut, coude, echelle));
    // pilote.a().whileTrue(new Balancer(basePilotable, false));
    // pilote.b().whileTrue(new Balancer(basePilotable, true));

    //Bouger le bras manuellement
    pilote.povUp().whileTrue(new StartEndCommand(echelle::allonger,echelle::stop , echelle));
    pilote.povDown().whileTrue(new StartEndCommand(echelle::retracter,echelle::stop , echelle));
    pilote.povRight().whileTrue(new StartEndCommand(coude::monter, coude::stop, coude));
    pilote.povLeft().whileTrue(new StartEndCommand(coude::descendre, coude::stop, coude));

    //reset encodeur quand l'aimant est activer
    aimantechelle.onTrue(new InstantCommand(echelle::resetEncodeur));
    limitSwitchCoude.onTrue(new InstantCommand(coude::resetEncodeur));

    //pince 
    pilote.rightBumper().onTrue(new InstantCommand(pince::togglePincePiston, pince)); //Pas un toggle car cela désactiverais le PincerAuto qui doit fonctionner en permanence

    //Homing
    pilote.start().onTrue(new HomingBras(echelle, coude));
   
  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}