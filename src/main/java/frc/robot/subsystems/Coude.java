// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Cible;
import frc.robot.Constants.CoudeConstants;

public class Coude extends SubsystemBase {

  private WPI_TalonFX moteur = new WPI_TalonFX(6);

  private Encoder encodeur = new Encoder(4, 5);
  private double conversionEncodeur;

  private DigitalInput limitSwitch = new DigitalInput(8);

  private ProfiledPIDController pid;
  private boolean pidCoudeActif;

  

  public Coude() {

    moteur.setNeutralMode(NeutralMode.Brake);

    conversionEncodeur = 360.0/(360.0 * 40.0/14.0); //360 degre, 360 clics d'encodeur par tour,ratio encodeur-coude 40:14.
    encodeur.setDistancePerPulse(conversionEncodeur);
    moteur.setInverted(true);
  
    pid = new ProfiledPIDController(CoudeConstants.kP, 0, 0,
        new TrapezoidProfile.Constraints(CoudeConstants.kMaxVelocity, CoudeConstants.kMaxVelocity));

    pid.setTolerance(CoudeConstants.kPositionTolerance);
  
    pidCoudeActif = false;

    
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Detecteur coude", getLimitSwitch());
    SmartDashboard.putNumber("Vitesse Coude", getVitesse());
    SmartDashboard.putNumber("Distance Coude", getPosition());
  }

  public double getPosition() {
    return (encodeur.getDistance() + CoudeConstants.kOffset);
  }

  public double getVitesse() {
    return encodeur.getRate();
  }
  
  public void resetEncodeur() {
    encodeur.reset();
  }

  public void setVoltage(double voltage) {
    moteur.setVoltage(voltage);
  }

     public void descendre() {
      if (getPosition() > CoudeConstants.kMin){
        setVoltage(-3);
      }
      else{
        stop();
    }
    pidCoudeActif = false;
  }
    public void monter() {
      if (getPosition() < CoudeConstants.kMax) {
        setVoltage(3);
      }
      else {
        stop();
      }
    pidCoudeActif = false;
  }

  public void stop() {
    setVoltage(0);
  }

  public void pidCoude() {
    if(pidCoudeActif) {
      if(pid.getGoal().position == Cible.kBas[0] && getPosition() < 0 && !getLimitSwitch()){
        setVoltage(-1);
      }
      else{
        setVoltage(pid.calculate(getPosition()));
      }
      
    }
  }

  public void setCible(double cible) {
    cible = MathUtil.clamp(cible, CoudeConstants.kMin, CoudeConstants.kMax);
    pid.setGoal(cible);
    pidCoudeActif = true;
  }

  public boolean getLimitSwitch() {
    return !limitSwitch.get();
  }
}