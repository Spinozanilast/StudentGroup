package customComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A custom panel that contains input fields.
 */
public class InputGroupPanel extends RoundedPanel implements Comparable<InputGroupPanel>{
    private final Dimension STANDARD_SIZE = new Dimension(640, 45);
    private JPanel layoutPanel;
    private Color backgroundColor;
    private Color textBoxesBackground;
    private Color foregroundColor;
    protected RoundJTextField[] textFieldsLeftToRight = new RoundJTextField[3];
    private String leftTextBoxStroke;
    private String centerTextBoxStroke;
    private String rightTextBoxStroke;
    private boolean isInputValid = false;

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
    public InputGroupPanel(Color foregroundColor, Color backgroundColor, Color textBoxesBackground,
                           String leftTextBoxStroke, String centerTextBoxStroke, String rightTextBoxStroke) {
        this.leftTextBoxStroke = leftTextBoxStroke;
        this.centerTextBoxStroke = centerTextBoxStroke;
        this.rightTextBoxStroke = rightTextBoxStroke;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.textBoxesBackground = textBoxesBackground;
        initFormState();
    }

    private void initFormState(){
        setShady(false);
        setMaximumSize(STANDARD_SIZE);
        setBackground(backgroundColor);
        setLabelsTexts(leftTextBoxStroke, centerTextBoxStroke, rightTextBoxStroke);
        setPanelLayout();
        initLabelsViews(textFieldsLeftToRight[0], textFieldsLeftToRight[1], textFieldsLeftToRight[2]);
        initEventsListeners();
        setStrokeSize(0);
    }

    /**
     * Sets the text for the input Text Fields.
     *
     * @param leftTextBoxText   the text for the left input field.
     * @param centerTextBoxText the text for the center input field.
     * @param rightTextBoxText  the text for the right input field.
     */
    public void setLabelsTexts(String leftTextBoxText, String centerTextBoxText, String rightTextBoxText) {
        RoundJTextField leftTextBox = new RoundJTextField(20);
        textFieldsLeftToRight[0] = leftTextBox;
        leftTextBox.setText(leftTextBoxText);
        leftTextBox.setHorizontalAlignment(SwingConstants.CENTER);

        RoundJTextField centerTextBox = new RoundJTextField(10);
        textFieldsLeftToRight[1] = centerTextBox;
        centerTextBox.setText(centerTextBoxText);
        centerTextBox.setHorizontalAlignment(SwingConstants.CENTER);

        RoundJTextField rightTextBox = new RoundJTextField(20);
        textFieldsLeftToRight[2] = rightTextBox;
        rightTextBox.setText(rightTextBoxText);
        rightTextBox.setHorizontalAlignment(SwingConstants.CENTER);
    }
    /**
     * Устанавливает компоновку для панели.
     * Компоненты располагаются вертикально в компоновке Y_AXIS.
     * Панель layoutPanel добавляется в центр панели.
     * Вертикальные пружины добавляются перед и после layoutPanel для отступов.
     * Внутри layoutPanel компоненты располагаются горизонтально в компоновке X_AXIS.
     * Горизонтальные пружины добавляются перед и после каждого компонента для отступов.
     */
    private void setPanelLayout() {
        layoutPanel = new JPanel();
        layoutPanel.setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(5));
        add(layoutPanel, BorderLayout.CENTER);
        add(Box.createVerticalStrut(5));

        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.X_AXIS));
        setUsableElementsLayout();
    }

    private void setUsableElementsLayout() {
        int beforeCenterStrutWidth = 58;
        layoutPanel.add(Box.createHorizontalStrut(5));
        layoutPanel.add(textFieldsLeftToRight[0]);
        layoutPanel.add(Box.createHorizontalStrut(beforeCenterStrutWidth));
        layoutPanel.add(textFieldsLeftToRight[1]);
        layoutPanel.add(Box.createHorizontalStrut(beforeCenterStrutWidth));
        layoutPanel.add(textFieldsLeftToRight[2]);
        layoutPanel.add(Box.createHorizontalStrut(5));
    }

    /**
     * Инициализирует внешний вид текстовых полей.
     * Устанавливает шрифт, цвет фона, цвет переднего плана, границу и размеры для каждого текстового поля.
     *
     * @param textFields текстовые поля, для которых нужно инициализировать внешний вид.
     */
    private void initLabelsViews(JTextField... textFields) {
        for (JTextField textField : textFields) {
            textField.setFont(new Font("Montserrat", Font.ITALIC, 14));
            setBackground(backgroundColor);
            textField.setBackground(textBoxesBackground);
            textField.setForeground(foregroundColor);
            textField.setBorder(null);
            textField.setSize(getWidth(), 32);
        }
    }

    /**
     * Инициализирует слушатели событий для текстовых полей.
     * Добавляет слушатель фокуса к каждому текстовому полю для обработки событий фокуса.
     * Когда текстовое поле получает фокус, стандартный текст очищается.
     * Когда текстовое поле теряет фокус и пустое, восстанавливается стандартный текст.
     */
    private void initEventsListeners() {
        addFocusListener(textFieldsLeftToRight[0], leftTextBoxStroke);
        addFocusListener(textFieldsLeftToRight[1], centerTextBoxStroke);
        addFocusListener(textFieldsLeftToRight[2], rightTextBoxStroke);
    }

    /**
     /**
     * Добавляет слушатель фокуса к указанному текстовому полю.
     * Слушатель фокуса очищает стандартный текст, когда текстовое поле получает фокус.
     * Если текстовое поле теряет фокус и пустое, восстанавливается стандартный текст.
     *
     * @param textField   текстовое поле, к которому нужно добавить слушатель фокуса.
     * @param defaultText стандартный текст для текстового поля.
     */
    private void addFocusListener(JTextField textField, String defaultText) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(defaultText)) {
                    if (!isInputValid){
                      setTextFieldsValid();
                    }
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
                }
            }
        });
    }
    public void setTextBoxesBackground(Color textBoxesBackground) {
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setBackground(textBoxesBackground);
        }
    }

    public void setTextFieldsForeground(Color foregroundColor) {
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setForeground(foregroundColor);
        }
    }

    public void setTextBoxesAlignments(int leftTextBoxAlignment, int centerTextBoxAlignment, int rightTextBoxAlignment){
        textFieldsLeftToRight[0].setHorizontalAlignment(leftTextBoxAlignment);
        textFieldsLeftToRight[1].setHorizontalAlignment(centerTextBoxAlignment);
        textFieldsLeftToRight[2].setHorizontalAlignment(rightTextBoxAlignment);
    }

    public void setTextBoxesEditable(boolean areEditable){
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setEditable(areEditable);
        }
    }


    public void setTextBoxesFont(Font font){
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setFont(font);
        }
    }

    public void setTextNonEnable(boolean areEnable){
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setEnabled(areEnable);
        }
    }

    public void setPanelEditableStandardValues(){
        setTextBoxesEditable(true);
        setTextBoxesBackground(textBoxesBackground);
        setTextBoxesAlignments(SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER);
        setTextFieldsForeground(foregroundColor);
    }
    public void setPanelNonEditableCustom(Color textBoxesBackground, Color foregroundColor, Font font){
        setTextBoxesEditable(false);
        setTextBoxesBackground(textBoxesBackground);
        setTextFieldsForeground(foregroundColor);
        setTextBoxesFont(font);
        setTextBoxesAlignments(SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.RIGHT);
    }

    public boolean isInputValid(){
        boolean validity = true;

        boolean textsNotEqualInitials = true;
        if (textFieldsLeftToRight[0].getText().equals(leftTextBoxStroke) ||
                textFieldsLeftToRight[1].getText().equals(centerTextBoxStroke) ||
                textFieldsLeftToRight[2].getText().equals(rightTextBoxStroke)){
            textsNotEqualInitials = false;
        }

        boolean textsNotEmpty = true;
        for (JTextField textField: textFieldsLeftToRight){
            if (textField.getText().trim().isEmpty()){
                textsNotEmpty = false;
            }
        }

        boolean validTextsLength = true;
        if ((textFieldsLeftToRight[0].getText().length() != 8)
                || textFieldsLeftToRight[1].getText().length() != 1){
            validTextsLength = false;
        }

        validity = textsNotEqualInitials && textsNotEmpty && validTextsLength;
        return validity;
    }

    public void setTextFieldsUnValid(){
        setTextBoxesBackground(Color.red);
        setTextFieldsForeground(Color.white);
        isInputValid = false;
    }

    public void setTextFieldsValid(){
        setTextBoxesBackground(textBoxesBackground);
        setTextFieldsForeground(foregroundColor);
        isInputValid = true;
    }

    /**
     * @param otherGroupPanel the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(InputGroupPanel otherGroupPanel) {
        var otherLeftLabelText = otherGroupPanel.textFieldsLeftToRight[0].getText();
        var thisLeftLabelText = this.textFieldsLeftToRight[0].getText();
        return otherLeftLabelText.compareTo(thisLeftLabelText);
    }

    private static void sortGroupListByLeftLabelText(List<InputGroupPanel> groupPanelList){
        Collections.sort(groupPanelList);
    }

    private static void sortPanelListByLabelText(List<InputGroupPanel> groupPanelList, int textFieldIndex) {
        Collections.sort(groupPanelList, Comparator.comparing(panel -> panel.textFieldsLeftToRight[textFieldIndex].getText()));
    }

    private static void sortPanelListByCenterLabelText(List<InputGroupPanel> groupPanelList) {
        sortPanelListByLabelText(groupPanelList, 1);
    }

    private static void sortPanelListByRightLabelText(List<InputGroupPanel> groupPanelList) {
        sortPanelListByLabelText(groupPanelList, 2);
    }
}