// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Cible;
import frc.robot.Constants.EchelleConstantes;

public class Echelle extends SubsystemBase {
  private WPI_TalonFX moteur = new WPI_TalonFX(5);
  private double conversionEncodeur;
  private DigitalInput detecteurMagnetique = new DigitalInput(9);
  private ProfiledPIDController pid;
  boolean pidEchelleActif;

  public Echelle() {

    moteur.setInverted(false);
    moteur.setNeutralMode(NeutralMode.Brake);
    moteur.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    resetEncodeur();

    /*1 tour de falcon = 2048 clic. Pignon 14 dents sur le falcon fait tourner gear 40 dents. La gear 40 dents est solidaire d'une
    gear 14 dents (même vitesse). La gear 14 dents fait tourner une gear 60 dents. La gear 60 dents est solidaire d'une roue dentée
    de 16 dents qui fait tourner la chaine 25. Chaque maille de la chaine fait 0.25 pouces*/
    conversionEncodeur = (1.0/2048)*(14.0/40)*(14.0/60)*(16.0)*Units.inchesToMeters(0.25);

    pid = new ProfiledPIDController(EchelleConstantes.kP, 0, 0,
            //Vitesse et accélération max vraiment faibles pour tester     
            new TrapezoidProfile.Constraints(EchelleConstantes.kMaxVelocity, EchelleConstantes.kMaxAcceleration));
            pid.setTolerance(EchelleConstantes.kPositionTolerance);

    pidEchelleActif = false;

    limiteCourant(false); 
 }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Position Mat", getPosition());
  }

  //Encodeur Mât
  public double getPosition() {
    return moteur.getSelectedSensorPosition()*conversionEncodeur;
  }

  public double getVitesse() {
    return moteur.getSelectedSensorVelocity()*conversionEncodeur*10; //x10 car les encodeur des falcon donne des click par 100 ms.
  }
  
  public void resetEncodeur() {
    moteur.setSelectedSensorPosition(0);
  }

  //Mouvement de l'échelle sans PID
  public void setVoltage(double voltage) {
    moteur.setVoltage(voltage);
  }

  public void stop() {
    setVoltage(0);
  }

  public void allonger() {
   
    if (getPosition()< EchelleConstantes.kMaxEchelle) {
      setVoltage(3);
    }

    else {
      stop();
    }

    pidEchelleActif = false;
  }

  public void retracter() {
    if (getPosition() > 0) {
      setVoltage(-3);
    }

    else {
      stop();
    }

    pidEchelleActif = false;
  }

  //Mouvement de l'échelle PID
  public void pidEchelle() {
    if (pidEchelleActif) {
      if (pid.getGoal().position==Cible.kHaut[1] && getPosition() > 0.9*Cible.kHaut[1]) {
        limiteCourant(true);
      }

      else {
        limiteCourant(false);
      }

      double voltage = pid.calculate(getPosition());
      setVoltage(voltage);

    }
  }

  //Changer la cible
  public void setCible(double cible) {
    cible = MathUtil.clamp(cible, 0, EchelleConstantes.kMaxEchelle);
    pid.setGoal(cible);
    pidEchelleActif = true;
  }

  public boolean getCible() {
    return pid.atGoal();
  }

  public boolean getDetecteurMagnetique() {
    return !detecteurMagnetique.get();
  }

  //Pour ne pas briser l'échelle quand on applique une force au bras pour le tenir en place au sol
  public void limiteCourant(boolean active) {
    if(active) {
      moteur.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 15, 20, 1));
    }

    else {//Les valeurs ne changent rien car la limite n'est pas enable
      moteur.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 30, 30, 1));
    }
  }
}