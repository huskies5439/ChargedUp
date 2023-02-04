// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BrasRetractable extends SubsystemBase {
  private WPI_TalonFX moteurBrasRetractable = new WPI_TalonFX(5);

  //Je sais pas trop comment faire pour la suite [P-A]
  ElevatorFeedforward feedforward = new ElevatorFeedforward(Constants.kS, Constants.kG, Constants.kV, Constants.kA);

  /** Creates a new BrasRetractable. */
  
  public BrasRetractable() {
    moteurBrasRetractable.setInverted(false);
    moteurBrasRetractable.setNeutralMode(NeutralMode.Brake);
    moteurBrasRetractable.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
  resetEncodeur();
  }

  public void setVitesse(double vitesse) {
  moteurBrasRetractable.set(vitesse);
  }

  public double getPosition() {
  return moteurBrasRetractable.getSelectedSensorPosition();
  }
  
  public void resetEncodeur() {
    moteurBrasRetractable.setSelectedSensorPosition(0);
  }
  public void stop() {
    moteurBrasRetractable.set(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  SmartDashboard.putNumber("Position BrasRetractable", getPosition());
  }
}