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
import frc.robot.commands.BrasAuto;
import frc.robot.commands.Conduire;
import frc.robot.commands.DescendrePrecision;
import frc.robot.commands.HomingBras;
import frc.robot.commands.PincerAuto;
import frc.robot.commands.UpdatePosition;
import frc.robot.commands.auto.AutoPlacer;
import frc.robot.commands.auto.AutoPlacerSol;
import frc.robot.commands.auto.AvancerDistanceSimple;
import frc.robot.commands.auto.Balancino;
import frc.robot.commands.auto.JamesBande;
import frc.robot.commands.auto.LaCachette;
import frc.robot.commands.balancer.AncrerBalance;
import frc.robot.commands.balancer.Balancer;
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

  CommandXboxController manette = new CommandXboxController(0);

  private final SendableChooser<Command> chooser = new SendableChooser<>();
  
  //Autonomes de base
  private final Command sortirZoneDevant = new AvancerDistanceSimple(5, basePilotable);
  private final Command balancer = new Balancer(basePilotable);
  private final Command placerCone = new AutoPlacer(true, echelle, coude, pince, basePilotable);
  private final Command placerCube = new AutoPlacer(false, echelle, coude, pince, basePilotable);
  //placer sol
  private final Command placerSol = new AutoPlacerSol(echelle, coude, pince);
  
  //Trajets
  private final Command jamesBande = new JamesBande(echelle, coude, pince, basePilotable);

  //Balancino
  private final Command balancinoCone = new Balancino(true, basePilotable, echelle, coude, pince);
  private final Command balancinoCube = new Balancino(false, basePilotable, echelle, coude, pince);

  private final Command laCachette = new LaCachette(basePilotable, echelle, coude, pince);

  Trigger aimantechelle = new Trigger(echelle::getDetecteurMagnetique);
  Trigger limitSwitchCoude = new Trigger(coude::getLimitSwitch);
  
  public RobotContainer() {

    SmartDashboard.putData(chooser);
    //Autonomes simples
    chooser.addOption("Sortir zone DEVANT", sortirZoneDevant);
    chooser.addOption("Balancer", balancer);
    chooser.addOption("Placer Cone", placerCone);
    chooser.addOption("Placer Cube", placerCube);
    chooser.addOption("Placer Sol" , placerSol);

    //Trajet du centre
    chooser.addOption("Balancino Cone", balancinoCone);
    chooser.addOption("Balancino Cube", balancinoCube);

    //Trajet Bande
    chooser.addOption("James Bande", jamesBande);

    //Trajet Haut
    chooser.addOption("La Cachette", laCachette);

    configureBindings();
    
    basePilotable.setDefaultCommand(new Conduire(manette::getLeftY,manette::getRightX, basePilotable));
    pince.setDefaultCommand(new PincerAuto(pince)); 
    echelle.setDefaultCommand(new RunCommand(echelle::pidEchelle, echelle));
    coude.setDefaultCommand(new RunCommand(coude::pidCoude, coude));
    limelight.setDefaultCommand(new UpdatePosition(basePilotable, limelight));
  }

  private void configureBindings() {

    //Bouton pour que le bras soit à la bonne hauteur
    manette.a().onTrue(new BrasAuto(Cible.kRentrer, echelle, coude));
    manette.b().onTrue(new BrasAuto(Cible.kMilieu, echelle, coude));
    manette.y().onTrue(new BrasAuto(Cible.kHaut, echelle, coude));
    manette.x().onTrue(new BrasAuto(Cible.kSol, echelle, coude));

    manette.leftTrigger().whileTrue(new DescendrePrecision(coude));

    manette.leftBumper().whileTrue(new AncrerBalance(manette::getLeftY, manette::getRightX, basePilotable));

    //Pratique pour débugger le balancement
    //manette.rightTrigger().whileTrue(new Balancer(basePilotable));

    //Bouger le bras manuellement
    manette.povUp().whileTrue(new StartEndCommand(echelle::allonger,echelle::stop , echelle));
    manette.povDown().whileTrue(new StartEndCommand(echelle::retracter,echelle::stop , echelle));
    manette.povRight().whileTrue(new StartEndCommand(coude::monter, coude::stop, coude));
    manette.povLeft().whileTrue(new StartEndCommand(coude::descendre, coude::stop, coude));

    //reset encodeur quand l'aimant est activer
    aimantechelle.onTrue(new InstantCommand(echelle::resetEncodeur));
    limitSwitchCoude.onTrue(new InstantCommand(coude::resetEncodeur));

    //pince 
    manette.rightTrigger().onTrue(new InstantCommand(pince::togglePincePiston, pince)); //Pas un toggle car cela désactiverais le PincerAuto qui doit fonctionner en permanence

    //Homing
    manette.start().onTrue(new HomingBras(echelle, coude));
   
  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}