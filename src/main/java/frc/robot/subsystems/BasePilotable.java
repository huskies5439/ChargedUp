// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Gregory Audet

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.PPRamseteCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BasePilotableConstantes;

public class BasePilotable extends SubsystemBase {
  // Moteurs
  private WPI_TalonFX moteurAvantG = new WPI_TalonFX(4);
  private WPI_TalonFX moteurArriereG = new WPI_TalonFX(3);
  private WPI_TalonFX moteurAvantD = new WPI_TalonFX(1);
  private WPI_TalonFX moteurArriereD = new WPI_TalonFX(2);

  private MotorControllerGroup moteursG = new MotorControllerGroup(moteurAvantG, moteurArriereG);
  private MotorControllerGroup moteursD = new MotorControllerGroup(moteurAvantD, moteurArriereD);

  private DifferentialDrive drive = new DifferentialDrive(moteursG, moteursD);

  // Encodeurs
  private Encoder encodeurG = new Encoder(0, 1, false);
  private Encoder encodeurD = new Encoder(2, 3, true);
  private double conversionEncodeur;

  // Gyro
  private PigeonIMU gyro = new PigeonIMU(0);
  private double pitchOffset = 0;
  

  // Odometrie
  private DifferentialDrivePoseEstimator poseEstimator;
  private Field2d field = new Field2d();

  // Pneumatique
  private DoubleSolenoid pistonTransmission = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
  private boolean isHighGear = false;

  

  public BasePilotable() {
    // Reset intiaux
    resetEncodeur();
    resetGyro();

    // Configuration des encodeurs externes

    // Roue de 8 pouces, 256 clics d'encodeur par tour, ratio encodeur-shaft 3:1,
    // ratio shaft-roue 54:30
    conversionEncodeur = Math.PI * Units.inchesToMeters(8) / (256 * 3 * 54 / 30);
    encodeurG.setDistancePerPulse(conversionEncodeur);
    encodeurD.setDistancePerPulse(conversionEncodeur);
    moteursD.setInverted(false);
    moteursG.setInverted(true);

    // Ramp et Break
    setBrakeEtRampTeleop(true);

    // Pose estimateur
    poseEstimator = new DifferentialDrivePoseEstimator(BasePilotableConstantes.kinematics,
        Rotation2d.fromDegrees(getAngle()),
        getPositionG(), getPositionD(), new Pose2d());

    // transmission
    lowGear();

   
  }

  @Override
  public void periodic() {

    poseEstimator.update(Rotation2d.fromDegrees(getAngle()), getPositionG(), getPositionD());

    field.setRobotPose(poseEstimator.getEstimatedPosition());

    SmartDashboard.putNumber("Angle", getAngle());
    SmartDashboard.putNumber("Pitch", getPitch());
    SmartDashboard.putNumber("Position Base", getPosition());

    SmartDashboard.putNumber("Pose Estimator X", poseEstimator.getEstimatedPosition().getX());
    SmartDashboard.putNumber("Pose Estimator Y", poseEstimator.getEstimatedPosition().getY());

    
    SmartDashboard.putData(field);
  }

                /* MÉTHODES */

  // Méthode pour conduire
  public void conduire(double vx, double vz) {
    drive.arcadeDrive(-0.75 * vx, -0.65 * vz);
  }

  public void autoConduire(double voltGauche, double voltDroit) {
    moteursG.setVoltage(voltGauche);
    moteursD.setVoltage(voltDroit);
    drive.feed();
  }

  public void stop() {
    drive.arcadeDrive(0, 0);
  }

  // Encodeur
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

  // Ramp et Brake
  public void setRamp(double ramp) {
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

  public void setBrakeEtRampTeleop(boolean estTeleop) {
    if (estTeleop) {
      setBrake(false);
      setRamp(BasePilotableConstantes.rampTeleop);
    }

    else {
      setBrake(true);
      setRamp(0);
    }
  }

  // Transmission
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

  // Angle
  public double getAngle() {
    return gyro.getYaw();
  }

  public void resetGyro() {
    gyro.setYaw(0);
    pitchOffset = gyro.getPitch();
  }

  public double getPitch() {
    return (gyro.getPitch() - pitchOffset);
  }

  // Odométrie
  public double[] getOdometry() {// seulement utile pour le dash bord
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
    return poseEstimator.getEstimatedPosition();
  }

  public void addVisionMeasurement(Pose2d position, double delaiLimelight) {
    poseEstimator.addVisionMeasurement(position, Timer.getFPGATimestamp() - delaiLimelight);
  }

  public void resetOdometry(Pose2d pose) {
    resetEncodeur();
    resetGyro();
    poseEstimator.resetPosition(Rotation2d.fromDegrees(getAngle()), getPositionG(), getPositionD(), pose);
  }

  public void placerRobotPositionInitial(PathPlannerTrajectory trajectory)
  {
    trajectory = PathPlannerTrajectory.transformTrajectoryForAlliance(trajectory, DriverStation.getAlliance());
    resetOdometry(trajectory.getInitialPose());
  }


  

  // déplacement trajectoire
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getVitesseG(), getVitesseD());
  }

  public PathPlannerTrajectory creerTrajectoire(String trajet, boolean reversed) {
    return PathPlanner.loadPath(trajet, BasePilotableConstantes.maxVitesse, BasePilotableConstantes.maxAcceleration,
        reversed);
  }

  public PathPlannerTrajectory creerTrajectoirePoint(double x, double y, double angle) {
    PathPlannerTrajectory trajectoire = PathPlanner.generatePath(
        new PathConstraints(BasePilotableConstantes.maxVitesse, BasePilotableConstantes.maxAcceleration),
        new PathPoint(getPose().getTranslation(), getPose().getRotation()),
        //new PathPoint(new Translation2d(x - 0.5, y), new Rotation2d(Math.toRadians(angle))),//necessaire ??
        new PathPoint(new Translation2d(x, y), new Rotation2d(Math.toRadians(angle))));
    return trajectoire;
  }

  public Command ramsete(PathPlannerTrajectory trajectoire) {
    PPRamseteCommand ramseteCommand = new PPRamseteCommand(
        trajectoire,
        this::getPose,
        new RamseteController(),
        BasePilotableConstantes.feedforward,
        BasePilotableConstantes.kinematics,
        this::getWheelSpeeds,
        new PIDController(BasePilotableConstantes.kPRamsete, 0, 0),
        new PIDController(BasePilotableConstantes.kPRamsete, 0, 0),
        this::autoConduire,
        true,
        this);

    return ramseteCommand.andThen(() -> autoConduire(0, 0));
  }
}