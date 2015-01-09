package lejos.ev3.startup;

import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;
import lejos.utility.Delay;

/**
 * Executes the Open Roberta lab user program after it is downloaded.
 * 
 * @author dpyka
 */
public class ORAlauncher {

    private static final String CMD_ORA_RUN = "jrun -jar ";
    private static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";

    private static final String OpenRobertaLabLogo =
        "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f0\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0038\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0038\u0038\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u0038\u0077\u00e0\u00e3\u000e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u0038\u00ff\u00f0\u00e7\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u0038\u00cf\u0039\u00ee\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u0038\u00c7\u00f9\u00ef\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u0038\u00c7\u00f9\u00ef\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u001c\u00c7\u0039\u00e0\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0038\u001c\u00ef\u0079\u00e8\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f0\u000f\u00ff\u00f0\u00ef\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u0007\u0077\u00e0\u00e7\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u003c\u0000\u0000\u0000\u0000\u0000\u00e0\u003f\u0000\u0000\u0000\u0000\u0000\u0080\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u003e\u0000\u0000\u0000\u0000\u0000\u00f0\u00ff\u0003\u0000\u0000\u0000\u0000\u00f0\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u003f\u0000\u0000\u0000\u0000\u0000\u00f0\u00ff\u0007\u0000\u0000\u0000\u0000\u00f8\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00c0\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u001f\u0000\u0000\u0000\u0000\u00fc\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u007f\u007e\u0000\u0000\u0000\u0000\u00e8\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u007f\u00f8\u0000\u0000\u0007\u0000\u00e0\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00fe\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u007f\u00e0\u0001\u00c0\u00c7\u0000\u00e0\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00fe\u001f\u0000\u0000\u0000\u001c\u0000\u0000\u007f\u00c0\u0001\u00d0\u00ef\u0003\u00e0\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u0081\u00ff\u0001\u0063\u0000\u0080\u007f\u0080\u0003\u00dc\u00ff\u0003\u00e0\u000f\u0000\u0000\u0000\u0000\u00f8\u000f\u0078\u00f0\u00ff\u00e3\u00ff\u0003\u005d\u0000\u0080\u003f\u0080\u0003\u00fe\u00ff\u0003\u00e0\u000f\u0000\u0000\u0078\u0000\u00fe\u000f\u00fe\u00f0\u00ff\u00f1\u00ff\u0087\u00d4\u0000\u0080\u003f\u0080\u0003\u00ff\u00ff\u0019\u00f0\u000f\u0000\u0000\u00ff\u0003\u00fe\u008f\u00ff\u00f0\u000f\u0078\u00f0\u008f\u009c\u0000\u0080\u003f\u0080\u0001\u00fe\u00ff\u003f\u00f0\u000f\u0000\u00c0\u00ef\u0007\u00e0\u00cf\u00c7\u00f0\u000f\u001e\u00e0\u009f\u00d4\u0000\u0080\u003f\u0080\u0001\u00fc\u00ff\u007f\u00f0\u000f\u0000\u00f0\u0007\u000f\u00e0\u00ff\u00c0\u00f8\u000f\u000e\u00e0\u001f\u0055\u0000\u0080\u003f\u00e0\u0081\u00ff\u00ff\u007f\u00f0\u00ff\u000f\u00f0\u0003\u000e\u00e0\u007f\u00c0\u00f8\u0007\u000f\u00c0\u001f\u0023\u0000\u0080\u00ff\u00ff\u00c1\u007f\u00c0\u003f\u00f0\u00ff\u001f\u00f8\u0003\u001c\u00e0\u003f\u0000\u00f8\u0007\u0007\u00c0\u001f\u001c\u0000\u0080\u00ff\u007f\u00c0\u003f\u0080\u001f\u00f0\u001f\u003c\u00f8\u0003\u0018\u00e0\u001f\u0000\u00f8\u0007\u0007\u00c0\u001f\u0000\u0000\u00c0\u00ff\u001f\u00e0\u003f\u0000\u001f\u00f0\u000f\u0038\u00fc\u0003\u0038\u00e0\u000f\u0000\u00f8\u0007\u0003\u00e0\u001f\u0000\u0000\u00c0\u003f\u0007\u00e0\u001f\u0000\u00ff\u00f0\u000f\u0070\u00fc\u0003\u0038\u00e0\u000f\u0000\u00fc\u0007\u0000\u00fe\u001f\u0000\u0000\u00c0\u001f\u0006\u0080\u001f\u0000\u00ff\u00f0\u000f\u0070\u00fc\u00e3\u003f\u00e0\u000f\u0000\u00fc\u0007\u0080\u00ff\u001f\u0000\u0000\u00c0\u001f\u000e\u0000\u001f\u0000\u00ff\u00f0\u000f\u0070\u00fc\u00ff\u007f\u00e0\u000f\u0000\u00fc\u0003\u00e0\u00e3\u001f\u0000\u0000\u00c0\u001f\u000e\u00c0\u001f\u0000\u00ff\u00f0\u000f\u0070\u00fc\u00ff\u0001\u00e0\u000f\u0000\u00fc\u0003\u0078\u00e0\u001f\u0000\u0000\u00c0\u001f\u000e\u00e0\u003f\u0000\u007f\u00f0\u000f\u0070\u00fc\u0003\u0000\u00e0\u000f\u0000\u00fc\u0003\u003c\u00e0\u001f\u0000\u0000\u00c0\u001f\u001c\u00e0\u003f\u0080\u001f\u00f0\u000f\u0038\u00fc\u0003\u0000\u00f0\u000f\u0000\u00fc\u0003\u001c\u00e0\u001f\u0000\u0000\u00c0\u001f\u001c\u00c0\u007f\u0080\u003f\u00f0\u000f\u0038\u00fc\u0003\u0040\u00f0\u000f\u0000\u00fe\u0001\u000e\u00e0\u001f\u0000\u0000\u00e0\u001f\u001c\u00ce\u00ff\u00e0\u007f\u00f0\u000f\u001c\u00f8\u0003\u0060\u00f0\u000f\u0000\u00fe\u0001\u000e\u00e0\u001f\u0000\u0000\u00e0\u001f\u00d8\u000f\u00fe\u00ff\u007f\u00f0\u000f\u000e\u00f8\u0007\u0070\u00f0\u000f\u0000\u00fc\u0001\u0006\u00e0\u001f\u0000\u0000\u00e0\u000f\u00f8\u0007\u00fe\u00ff\u003f\u00f8\u000f\u0007\u00f0\u0007\u0070\u00f0\u0007\u0000\u00fc\u0001\u000e\u00e0\u003f\u0000\u0000\u00e0\u000f\u00f8\u0000\u00ff\u00ff\u003b\u00ff\u00cf\u0003\u00f0\u0007\u0038\u00f0\u0007\u0000\u00fc\u0001\u000e\u00e0\u00ff\u0003\u0000\u00e0\u000f\u007c\u0000\u00ff\u00ff\u0003\u00fe\u00ff\u0001\u00e0\u0007\u001e\u00fe\u001f\u0000\u00f8\u0010\u007e\u00fc\u00ff\u0003\u0000\u00fe\u001f\u001e\u0000\u00de\u00ff\u0003\u00fc\u00ff\u0000\u0080\u00ff\u000f\u00fe\u003f\u0000\u00f0\u001f\u00f8\u00ff\u00c1\u0001\u0000\u00fe\u003f\u000e\u0000\u00c8\u00ef\u0003\u00f0\u003f\u0000\u0000\u00fe\u0003\u00fe\u003f\u0000\u00e0\u001f\u00f0\u001f\u0000\u0000\u0000\u00be\u003f\u0000\u0000\u00c0\u00e7\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0010\u0000\u0000\u0080\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u000e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0003\u0000\u000e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0003\u0000\u000e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0003\u0000\u000e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0003\u0000\u000e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0003\u003f\u00ee\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0083\u007f\u00fe\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0083\u0070\u009e\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0003\u007f\u008e\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0083\u007f\u008e\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0083\u0073\u008e\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0083\u0073\u009e\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u00bf\u007f\u00fe\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u003f\u007f\u00ee\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    private static final Image image = new Image(178, 128, Utils.stringToBytes8(OpenRobertaLabLogo));

    private static final GraphicsLCD glcd = LocalEV3.get().getGraphicsLCD();

    /**
     * Creates a new object for launching the Open Roberta Lab user program.
     */
    public ORAlauncher() {
        //
    }

    /**
     * Executes the user's program as a new java process.
     * 
     * @param programName
     *        String Filename without directory, for example Linefollower.jar.
     */
    public void runProgram(String programName) {
        File robertalabFile = new File(ORAlauncher.PROGRAMS_DIRECTORY, programName);
        System.out.println("run: " + CMD_ORA_RUN + robertalabFile.getPath());
        GraphicStartup.menu.suspend();
        exec(CMD_ORA_RUN + robertalabFile.getPath(), ORAlauncher.PROGRAMS_DIRECTORY);
        Delay.msDelay(1000);
        GraphicStartup.menu.suspend(); // debug screen when process is killed
        GraphicStartup.menu.resume();
        redrawIPs();
    }

    /**
     * Manually redraw IP addresses on the screen. Used for restoring the screen after user process is terminated.
     */
    private void redrawIPs() {
        int row = 1;
        for ( String ip : GraphicStartup.ips ) {
            LocalEV3.get().getTextLCD().drawString(ip, 8 - ip.length() / 2, row++);
        }
    }

    /**
     * leJOS method from main menu to run a new process. Modified for Open Roberta
     * Lab. No wrapper for sysout and syserr, no stacktrace displaying.
     * 
     * @param command
     *        The shell commands to launch the new process.
     * @param directory
     *        The programs where all user programs are saved.
     */
    private void exec(String command, String directory) {
        Process program = null;
        try {

            glcd.clear();
            glcd.refresh();
            glcd.setAutoRefresh(false);

            glcd.drawImage(image, 0, 0, 0);
            glcd.refresh();

            program = new ProcessBuilder(command.split(" ")).directory(new File(directory)).start();

            System.out.println("Running an Open Roberta Lab Program!");
            while ( true ) {
                int b = Button.getButtons();
                if ( b == 6 ) {
                    System.out.println("Killing the process");
                    program.destroy();
                    GraphicStartup.resetMotors();
                }
                try {
                    // we do not use lejos wrapper for sysout and syserr
                    // this is an alternative method to check if process is terminated
                    // exitvalue is 143 if process was killed by another one
                    // exitvalue is 0 if process terminates regularly
                    // throws exception if not yet terminated
                    System.out.println("ORA process exitvalue: " + program.exitValue());
                    break;
                } catch ( IllegalThreadStateException e ) {
                    // go on
                }
                Delay.msDelay(200);
            }

            System.out.println("Waiting for process to die");
            program.waitFor();
            System.out.println("Program finished");
            // Turn the LED off, in case left on
            Button.LEDPattern(0);
            glcd.setAutoRefresh(true);
            glcd.clear();
            glcd.refresh();
            glcd.refresh();

            program = null;
        } catch ( Exception e ) {
            System.err.println("Failed to execute ORA program: " + e);
        }
    }

}
