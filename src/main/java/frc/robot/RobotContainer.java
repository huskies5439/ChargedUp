// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Conduire;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.BrasRetractable;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final BasePilotable basePilotable = new BasePilotable();
  private final BrasRetractable Bras = new BrasRetractable();
  CommandXboxController pilote = new CommandXboxController(0);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    basePilotable.setDefaultCommand(new Conduire(pilote::getLeftY,pilote::getRightX, basePilotable));
  }

  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
   pilote.a().onTrue(new StartEndCommand(Bras::allonger,Bras::stop , Bras));
   pilote.b().onTrue(new StartEndCommand(Bras::retracter,Bras::stop , Bras));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new RunCommand(() -> basePilotable.autoConduire(5, 5), basePilotable);
  }
}
