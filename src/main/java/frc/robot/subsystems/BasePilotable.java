// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BasePilotable extends SubsystemBase {
  private WPI_TalonFX moteurAvantG = new WPI_TalonFX(4);
  private WPI_TalonFX moteurArriereG = new WPI_TalonFX(3);
  private WPI_TalonFX moteurAvantD = new WPI_TalonFX(1);
  private WPI_TalonFX moteurArriereD = new WPI_TalonFX(2);

  private MotorControllerGroup moteursG = new MotorControllerGroup(moteurAvantG, moteurArriereG);
  private MotorControllerGroup moteursD = new MotorControllerGroup(moteurAvantD, moteurArriereD);

  private DifferentialDrive drive = new DifferentialDrive(moteursG, moteursD);

  private Encoder encodeurG = new Encoder(0, 1, true);
  private Encoder encodeurD = new Encoder(2, 3, false);
  private double conversionEncodeur; // trouver le bon port
  private DoubleSolenoid pistonTransmission = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 0);
  private boolean isHighGear = false;
  private PigeonIMU gyro = new PigeonIMU(5);
  private double[] ypr = new double[3];

  /** Creates a new BasePilotable. */
  public BasePilotable() {
    conversionEncodeur = Math.PI * Units.inchesToMeters(6) / (256 * 3 * 54 / 30);
    encodeurG.setDistancePerPulse(conversionEncodeur);
    encodeurD.setDistancePerPulse(conversionEncodeur);
    moteursD.setInverted(false);
    moteursG.setInverted(true);
    setRamp(0);
  }

  public void conduire(double vx, double vz) {
    drive.arcadeDrive(-0.65 * vx, -0.5 * vz);
  }

  public void autoConduire(double voltGauche, double voltDroit) {
    moteursG.setVoltage(voltGauche);
    moteursD.setVoltage(voltDroit);
    drive.feed();
  }

  public void stop() {
    drive.arcadeDrive(0, 0);
  }

  public double getPositionG() {
    return encodeurG.getDistance();
  }

  public double getPositionD() {
    return encodeurD.getDistance();
  }

  public double getPosition() {
    return (getPositionG() + getPositionD()) / 2.0;
  }

  public double getVitesseD() {
    return encodeurD.getRate();
  }

  public double getVitesseG() {
    return encodeurG.getRate();
  }

  public double getVitesse() {
    return (getVitesseD() + getVitesseG()) / 2.0;
  }

  public void setRamp(double ramp){
    

  }

  public void resetEncodeur() {
    encodeurD.reset();
    encodeurG.reset();
  }

  public void resetGyro() {
    gyro.setYaw(0);
  }

  public boolean getIsHighGear() {
    return isHighGear;
  }

  public void highGear() {
    pistonTransmission.set(DoubleSolenoid.Value.kForward);

    isHighGear = true;
  }

  public void lowGear() {
    pistonTransmission.set(DoubleSolenoid.Value.kReverse);

    isHighGear = false;
  }

  public double getAngle() {
    gyro.getYawPitchRoll(ypr);
    return ypr[0];
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}