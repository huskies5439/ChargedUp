// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Édouard Bussière

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PicoColorSensor;
import frc.robot.PicoColorSensor.RawColor;

///////////////////////Inutiliser pour l'instant///////////////////////

public class CapteurCouleur extends SubsystemBase {
  
  //Capteur de Couleur
  PicoColorSensor capteurCouleur = new PicoColorSensor();
  private final ColorMatch colorMatcher = new ColorMatch();
  private final Color kCouleurCone = new Color(0.359, 0.158, 0.485);
  private final Color kCouleurCube = new Color(0.26, 0.311, 0.429);

  String colorString;
  ColorMatchResult comparaisonCouleur;
  LinearFilter filter = LinearFilter.singlePoleIIR(0.2, 0.02);

  public CapteurCouleur() {
    colorMatcher.addColorMatch(kCouleurCube);
    colorMatcher.addColorMatch(kCouleurCone);
  }

  @Override
  public void periodic() { 
    SmartDashboard.putBoolean("Cone?", isCone());
    SmartDashboard.putBoolean("Cube?", isCube());
  }

  public Color getCouleur() {
    RawColor rawColor = capteurCouleur.getRawColor0();
    double mag = rawColor.red + rawColor.green + rawColor.blue;
    return new Color(rawColor.red/mag, rawColor.green/mag, rawColor.blue/mag);
  }

  public Color comparerCouleur() {
    comparaisonCouleur = colorMatcher.matchClosestColor(getCouleur());
    return comparaisonCouleur.color;
  }

  public double getProximity() {
    return filter.calculate(capteurCouleur.getProximity0());
  }

  public boolean isDetected() {
    return capteurCouleur.getProximity0() > 115;
  }

  public boolean isCone() {
    return comparerCouleur() == kCouleurCone && isDetected();
  }

  public boolean isCube () {
    return comparerCouleur() == kCouleurCube && isDetected();
  }
}