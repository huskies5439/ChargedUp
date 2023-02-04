// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//Greg

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BasePilotable extends SubsystemBase {
  //Moteurs
  private WPI_TalonFX moteurAvantG = new WPI_TalonFX(4);
  private WPI_TalonFX moteurArriereG = new WPI_TalonFX(3);
  private WPI_TalonFX moteurAvantD = new WPI_TalonFX(1);
  private WPI_TalonFX moteurArriereD = new WPI_TalonFX(2);

  private MotorControllerGroup moteursG = new MotorControllerGroup(moteurAvantG, moteurArriereG);
  private MotorControllerGroup moteursD = new MotorControllerGroup(moteurAvantD, moteurArriereD);

  private DifferentialDrive drive = new DifferentialDrive(moteursG, moteursD);
  
  //Encodeurs
  private Encoder encodeurG = new Encoder(0, 1, true);
  private Encoder encodeurD = new Encoder(2, 3, false);
  private double conversionEncodeur; // trouver le bon port

  //Pneumatique
  private DoubleSolenoid pistonTransmission = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
  private boolean isHighGear = false;

  //Gyro
  private PigeonIMU gyro = new PigeonIMU(5);
  private double pitchOffset;

  //Odometrie
  private DifferentialDriveOdometry odometry;
  
  /** Creates a new BasePilotable. */
  public BasePilotable() {
    //Reset intiaux
    resetEncodeur();
    resetGyro();

    //Configuration des encodeurs externes
    conversionEncodeur = Math.PI * Units.inchesToMeters(6) / (256 * 3 * 54 / 30);
    encodeurG.setDistancePerPulse(conversionEncodeur);
    encodeurD.setDistancePerPulse(conversionEncodeur);
    moteursD.setInverted(false);
    moteursG.setInverted(true);

    //Remp et Break
    setRamp(0);
    setBrake(true);

    //Odometrie
    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getAngle()), getPositionG(), getPositionD());
  }

  @Override
  public void periodic() {
  
    odometry.update(Rotation2d.fromDegrees(getAngle()), getPositionG(), getPositionD());

    SmartDashboard.putNumber("Angle", getAngle());
  }

  //////////////////////////////////////////// MÉTHODES ////////////////////////////////////////////

  //Méthode pour conduire
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

  public void resetEncodeur() {
    encodeurD.reset();
    encodeurG.reset();
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

  //Ramp/Brake
  public void setRamp(double ramp){
    moteurAvantG.configOpenloopRamp(ramp);
    moteurArriereG.configOpenloopRamp(ramp);
    moteurAvantD.configOpenloopRamp(ramp);
    moteurArriereD.configOpenloopRamp(ramp);
  }

  public void setBrake(boolean isBrake) {
    if (isBrake) {
      moteurAvantD.setNeutralMode(NeutralMode.Brake);
      moteurArriereD.setNeutralMode(NeutralMode.Brake);
      moteurAvantG.setNeutralMode(NeutralMode.Brake);
      moteurArriereG.setNeutralMode(NeutralMode.Brake);
    }

    else {
      moteurAvantD.setNeutralMode(NeutralMode.Coast);
      moteurArriereD.setNeutralMode(NeutralMode.Coast);
      moteurAvantG.setNeutralMode(NeutralMode.Coast);
      moteurArriereG.setNeutralMode(NeutralMode.Coast);
    }
  }

  //Transmission
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

  //Angle
  public double getAngle() {
    return gyro.getYaw();
  }

  public void resetGyro() {
    gyro.setYaw(0);
    pitchOffset = gyro.getRoll();
  }

  public double getYaw() {
    return gyro.getYaw();
  }

  public double getPitch() {
    return gyro.getPitch();
    //Si besoin return -(gyro.getPitch() - pitchOffset);
  }

  public double getRoll() {
    return gyro.getRoll();
  }

  public boolean isNotBalance() {
    return Math.abs(getPitch()) >= Constants.kToleranceBalancer; //Depend de comment le gyro est placé dans le robot pour le sens Pitch ou Roll
  }
//Odométrie
 public double[] getOdometry(){
  double[] position = new double[3];
  double x = getPose().getTranslation().getX();
  double y = getPose().getTranslation().getY();
  double theta = getPose().getRotation().getDegrees();
  position[0] = x;
  position[1] = y;
  position[2] = theta;
  return position;
 }

 public Pose2d getPose() {
  return odometry.getPoseMeters();
 }

 public void resetOdometry() {
  resetEncodeur();
  resetGyro();
  odometry.resetPosition(Rotation2d.fromDegrees(getAngle()), getPositionG(), getPositionD(), getPose());
 }
}