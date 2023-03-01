// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {

  public static final class BasePilotableConstants {
    public static final double rampTeleop = 0.2;

    public static final int kToleranceBalancer = 5;
  }
  
  public static final class EchelleConstants {
    public static final double kSElevator = 0.0;
    public static final double kGElevator = 0.0;
    public static final double kVElevator = 0.0;
    public static final double kAElevator = 0.0;
  
    public static final double kMaxEchelle = 0.58; //en mètre
  }

  public static final class CoudeConstants {
    public static final double kMaxCoude = 135;
    public static final double kMinCoude = -10;
  }

  public static final class Cible {
    //angle du coude en premier, puis hauteur de l'échelle
    public static final double[] kBas = {0,0};
    public static final double[] kMilieu = {20,0.2};
    public static final double[] kHaut = {45,0.4};
  }
}
