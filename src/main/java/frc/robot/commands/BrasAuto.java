// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BrasConstants;
import frc.robot.subsystems.Bras;


public class BrasAuto extends CommandBase {
  Bras bras;
  double longueur;
  //Trouver la valeur de kP
  ProfiledPIDController pid = new ProfiledPIDController(0, 0, 0,
            //Vitesse et accélération max vraiment faibles pour tester     
            new TrapezoidProfile.Constraints(0.1,0.1));

  public BrasAuto(double longueurCible, Bras bras) {
    this.bras = bras;
    this.longueur = longueurCible;
    addRequirements(bras);
  }

  @Override
  public void initialize() {
    
    longueur = MathUtil.clamp(longueur, 0, BrasConstants.kMaxMat);
    //Mettre une tolérance de 5 mm.
    pid.setTolerance(5);
    //On vérifie si la longueur cible est entre le Max du mat et 0. 
    
    //Utiliser la fonction "clamp" pour ramener la longueur dans cet interval si nécessaire


  }

  @Override
  public void execute() {

    //C'est ici que le calcul doit se faire et être envoyer au moteur !


  }

  @Override
  public void end(boolean interrupted) {
    bras.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
