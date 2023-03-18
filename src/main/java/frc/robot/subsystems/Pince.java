// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Théo Gilbert

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pince extends SubsystemBase { 
  private DoubleSolenoid pinceGauche = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 6);
  private DoubleSolenoid pinceDroite = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 4);
  private DigitalInput lightBreak = new DigitalInput(7);
  private boolean capteurArmer = true;
  private boolean ouverturePiston;

  public Pince() {
    ouvrir();

  }

  @Override
  public void periodic() {
   // SmartDashboard.putBoolean("armer", getArmer());
    //SmartDashboard.putBoolean("infrarouge", getFaisceau());

  }

  public void ouvrir() {
    pinceGauche.set(Value.kForward);
    pinceDroite.set(Value.kForward);
    ouverturePiston = true;

  }

  public void fermer() {
    pinceGauche.set(Value.kReverse);
    pinceDroite.set(Value.kReverse);
    ouverturePiston = false;

  }

  public boolean getOuverturePiston() {
    return ouverturePiston;

  }

  public void togglePincePiston() {
    if (ouverturePiston) {
      fermer();

    }

    else {
      ouvrir();
      
    }
  }

  //Capteur pas bloqué true capteur bloqué false
  public boolean getFaisceau() {
    return lightBreak.get();
  }

  public boolean getArmer() {
    return capteurArmer;
  }

  public void setArmer(boolean estActif) {
    capteurArmer = estActif;
  }
}