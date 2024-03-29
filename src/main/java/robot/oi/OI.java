package robot.oi;

import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Xbox;
import com.torontocodingcollective.oi.TOi;
import com.torontocodingcollective.oi.TRumbleManager;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TStickPosition;
import com.torontocodingcollective.oi.TToggle;
import com.torontocodingcollective.oi.TTrigger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

/**
 * Driver Controller (inherited from TOi)
 * 
 * Sticks: Right Stick = Drive Stick Left Stick = Drive Stick Right Stick Press
 * = Toggle PIDs Left Stick Press = Toggle Compressor
 * 
 * Buttons: Start Button = Reset Encoders and Gyro Back Button = Cancel any
 * Command
 * 
 * Bumpers/Triggers: Left Bumper = Turbo shift
 * 
 * POV: Any Angle = Rotate to the Pressed Angle
 * 
 */
public class OI extends TOi {

    private TGameController driverController = new TGameController_Xbox(0);
    private TRumbleManager  driverRumble     = new TRumbleManager("Driver", driverController);

    private TGameController operatorController = new TGameController_Xbox(1);
    private TRumbleManager  operatorRumble     = new TRumbleManager("Operator", operatorController);

    private TToggle         compressorToggle = new TToggle(driverController, TStick.LEFT);
    private TToggle         speedPidToggle   = new TToggle(driverController, TStick.RIGHT);

    private DriveSelector   driveSelector    = new DriveSelector();


    /************************
    *         DRIVER        *
    *************************/

    @Override
    public boolean getCancelCommand() {
        return driverController.getButton(TButton.BACK);
    }

    public boolean getCompressorEnabled() {
        return compressorToggle.get();
    }

    @Override
    public TStickPosition getDriveStickPosition(TStick stick) {
        return driverController.getStickPosition(stick);
    }

    @Override
    public boolean getReset() {
        return driverController.getButton(TButton.START);
    }

    public DriveControlType getSelectedDriveType() {
        return driveSelector.getDriveControlType();
    }

    public TStick getSelectedSingleStickSide() {
        return driveSelector.getSingleStickSide();
    }

    @Override
    public boolean getSpeedPidEnabled() {
        return speedPidToggle.get();
    }

    public boolean getTurboOn() {
        return driverController.getButton(TButton.RIGHT_BUMPER);
    }

    public void init() {
        compressorToggle.set(true);
        speedPidToggle.set(false);
    }

    public void setSpeedPidEnabled(boolean state) {
        speedPidToggle.set(state);
    }

    public boolean hatchPickup(){
        return driverController.getButton(TButton.B);
    }

    public boolean hatchPlace(){
        return driverController.getButton(TButton.A);
    }

    /************************
    *       OPERATOR        *
    *************************/

    public boolean elevatorUp() {
		return operatorController.getButton(TButton.RIGHT_BUMPER);
    }

    public boolean elevatorDown() {
		return operatorController.getButton(TButton.LEFT_BUMPER);
    }

    public boolean intakeFwd(){
        return operatorController.getButton(TTrigger.LEFT);
    }

    public boolean intakeBkd(){
        return operatorController.getButton(TTrigger.RIGHT);
    }

    public boolean wristDown(){
        return operatorController.getButton(TButton.A);
    }

    public boolean wristMid(){
        return operatorController.getButton(TButton.X);
    }

    public boolean wristUp(){
        return operatorController.getButton(TButton.Y);
    }

    @Override
    public void updatePeriodic() {

        // Update all Toggles
        compressorToggle.updatePeriodic();
        speedPidToggle.updatePeriodic();
        driverRumble.updatePeriodic();

        // Update all SmartDashboard values
        SmartDashboard.putBoolean("Speed PID Toggle", getSpeedPidEnabled());
        SmartDashboard.putBoolean("Compressor Toggle", getCompressorEnabled());
        SmartDashboard.putString("Driver Controller", driverController.toString());
    }
}
