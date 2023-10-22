package customComponents;

import java.awt.*;

public class CreatedGroupPanel extends InputGroupPanel{

    /**
     * Constructs an InputGroupPanel with the specified colors and text strokes.
     *
     * @param foregroundColor     the foreground color of the input fields.
     * @param backgroundColor     the background color of the panel.
     * @param textBoxesBackground the background color of the input fields.
     * @param leftTextBoxStroke   the default text for the left input field.
     * @param centerTextBoxStroke the default text for the center input field.
     * @param rightTextBoxStroke  the default text for the right input field.
     */
    public CreatedGroupPanel(Color foregroundColor, Color backgroundColor, Color textBoxesBackground, String leftTextBoxStroke, String centerTextBoxStroke, String rightTextBoxStroke) {
        super(foregroundColor, backgroundColor, textBoxesBackground, leftTextBoxStroke, centerTextBoxStroke, rightTextBoxStroke);
    }
}
