// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  private NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
  private NetworkTable limelight = networkTableInstance.getTable("limelight-huskies");
  private NetworkTableEntry tv = limelight.getEntry("tv");
  private NetworkTableEntry ta = limelight.getEntry("ta");
  private NetworkTableEntry tl = limelight.getEntry("tl"); //Target Latency
  private NetworkTableEntry cl = limelight.getEntry("cl"); //Captured Latency

  private NetworkTableEntry botpose;
  private NetworkTableEntry stream = limelight.getEntry("stream");
  double[] result;
  double[] temp = {0,0,0,0,0,0};
  String alliance;
  
  public Limelight() {

    stream.setNumber(2);
    alliance = "blue";
    botpose = limelight.getEntry("botpose" + alliance);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Rz Camera", Math.toDegrees(getVisionPosition().getRotation().getZ()));
    SmartDashboard.putNumber("Latence", getTotalLatency());
    SmartDashboard.putBoolean("April Tag", getTv());
  }
  public Pose3d getVisionPosition(){
    result = botpose.getDoubleArray(temp);

    Translation3d tran3d = new Translation3d(result[0], result[1], result[2]);

    Rotation3d r3d = new Rotation3d(Math.toRadians(result[3]), Math.toRadians(result[4]), Math.toRadians(result[5]));
    Pose3d p3d = new Pose3d(tran3d, r3d);

    return p3d;
  }

  public double getTotalLatency() {
    return tl.getDouble(0) + cl.getDouble(0);
  }

  public boolean getTv() {
    return tv.getDouble(0) == 1;
  }

  public double getTa() {
    return ta.getDouble(0);
  }

  public void setAlliance(){
    if (DriverStation.getAlliance() == Alliance.Red) {
      alliance = "red";
    }
    else {
      alliance = "blue";
    }
    botpose = limelight.getEntry("botpose_wpi" + alliance);
  }
}