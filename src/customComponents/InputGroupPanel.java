package customComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Пользовательская панель, содержащая поля ввода для создания обложки группы.
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
     * Создаёт панель InputGroupPanel с указанными цветами и текстовыми значениями полей для ввода.
     *
     * @param foregroundColor     цвет переднего плана полей ввода.
     * @param backgroundColor     цвет фона панели.
     * @param textBoxesBackground цвет фона полей ввода.
     * @param leftTextBoxStroke   текст по умолчанию для левого поля ввода.
     * @param centerTextBoxStroke текст по умолчанию для центрального поля ввода.
     * @param rightTextBoxStroke  текст по умолчанию для правого поля ввода.
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
     * Устанавливает текст для полей ввода.
     *
     * @param leftTextBoxText   текст для левого поля ввода.
     * @param centerTextBoxText текст для центрального поля ввода.
     * @param rightTextBoxText  текст для правого поля ввода.
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
    /**
     * Устанавливает цвет фона для полей ввода.
     *
     * @param textBoxesBackground цвет фона для полей ввода.
     */
    public void setTextBoxesBackground(Color textBoxesBackground) {
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setBackground(textBoxesBackground);
        }
    }

    /**
     * Устанавливает цвет переднего плана для полей ввода.
     *
     * @param foregroundColor цвет переднего плана для полей ввода.
     */
    public void setTextFieldsForeground(Color foregroundColor) {
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setForeground(foregroundColor);
        }
    }

    /**
     * Устанавливает выравнивание для полей ввода.
     *
     * @param leftTextBoxAlignment   выравнивание для левого поля ввода.
     * @param centerTextBoxAlignment выравнивание для центрального поля ввода.
     * @param rightTextBoxAlignment  выравнивание для правого поля ввода.
     */
    public void setTextBoxesAlignments(int leftTextBoxAlignment, int centerTextBoxAlignment, int rightTextBoxAlignment){
        textFieldsLeftToRight[0].setHorizontalAlignment(leftTextBoxAlignment);
        textFieldsLeftToRight[1].setHorizontalAlignment(centerTextBoxAlignment);
        textFieldsLeftToRight[2].setHorizontalAlignment(rightTextBoxAlignment);
    }

    /**
     * Устанавливает возможность редактирования для полей ввода.
     *
     * @param areEditable true, если поля ввода должны быть редактируемыми, false в противном случае.
     */
    public void setTextBoxesEditable(boolean areEditable){
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setEditable(areEditable);
        }
    }


    /**
     * Устанавливает шрифт для полей ввода.
     *
     * @param font шрифт для полей ввода.
     */
    public void setTextBoxesFont(Font font){
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setFont(font);
        }
    }

    /**
     * Устанавливает возможность ввода текста для полей ввода.
     *
     * @param areEnable true, если поля ввода должны быть доступными для ввода, false в противном случае.
     */
    public void setTextNonEnable(boolean areEnable){
        for (RoundJTextField textField: textFieldsLeftToRight){
            textField.setEnabled(areEnable);
        }
    }

    /**
     * Устанавливает стандартные значения для редактируемой панели.
     */
    public void setPanelEditableStandardValues(){
        setTextBoxesEditable(true);
        setTextBoxesBackground(textBoxesBackground);
        setTextBoxesAlignments(SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER);
        setTextFieldsForeground(foregroundColor);
    }

    /**
     * Устанавливает настраиваемые значения для не редактируемой панели.
     *
     * @param textBoxesBackground цвет фона для полей ввода.
     * @param foregroundColor     цвет переднего плана для полей ввода.
     * @param font                шрифт для полей ввода.
     */
    public void setPanelNonEditableCustom(Color textBoxesBackground, Color foregroundColor, Font font){
        setTextBoxesEditable(false);
        setTextBoxesBackground(textBoxesBackground);
        setTextFieldsForeground(foregroundColor);
        setTextBoxesFont(font);
        setTextBoxesAlignments(SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.RIGHT);
    }

    /**
     * Проверяет, является ли ввод корректным.
     *
     * @return true, если ввод корректен, false в противном случае.
     */
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

        return textsNotEqualInitials && textsNotEmpty && validTextsLength;
    }


    /**
     * Устанавливает поля ввода в внешнее оповещающее некорректное состояние.
     */
    public void setTextFieldsUnValid(){
        setTextBoxesBackground(Color.red);
        setTextFieldsForeground(Color.white);
        isInputValid = false;
    }

    /**
     * Устанавливает поля ввода в корректное состояние.
     */
    public void setTextFieldsValid(){
        setTextBoxesBackground(textBoxesBackground);
        setTextFieldsForeground(foregroundColor);
        isInputValid = true;
    }

    public String[] getTextFieldValues(){
        return new String[] {textFieldsLeftToRight[0].getText(), textFieldsLeftToRight[1].getText(), textFieldsLeftToRight[2].getText()};
    }

    /**
     * Сравнивает данный объект InputGroupPanel с указанным объектом InputGroupPanel для упорядочивания.
     *
     * @param otherGroupPanel объект, с которым сравнивается данный объект.
     * @return отрицательное целое число, ноль или положительное целое число, если этот объект
     *         меньше, равен или больше указанного объекта.
     * @throws NullPointerException если указанный объект равен null.
     * @throws ClassCastException   если тип указанного объекта несовместим
     *                              с типом этого объекта.
     * @apiNote Сильно рекомендуется, но <i>не</i> строго обязательно, чтобы
     * {@code (x.compareTo(y)==0) == (x.equals(y))}. В общем случае, любой класс,
     * реализующий интерфейс {@code Comparable} и нарушающий это условие,
     * должен явно указывать на это. Рекомендуемый язык: "Примечание: этот класс имеет
     * естественный порядок, несовместимый с equals."
     */
    @Override
    public int compareTo(InputGroupPanel otherGroupPanel) {
        var otherLeftLabelText = otherGroupPanel.textFieldsLeftToRight[0].getText();
        var thisLeftLabelText = this.textFieldsLeftToRight[0].getText();
        return otherLeftLabelText.compareTo(thisLeftLabelText);
    }

    /**
     * Сортирует заданный список объектов InputGroupPanel по их левому текстовому полю в порядке возрастания или убывания.
     *
     * @param groupPanelList список объектов InputGroupPanel, которые нужно отсортировать.
     * @param ascending      true для сортировки в порядке возрастания, false для сортировки в порядке убывания.
     */
    public static void sortGroupListByLeftText(List<InputGroupPanel> groupPanelList, boolean ascending){
        if (ascending){
            Collections.sort(groupPanelList);
        }
        else {
            Collections.sort(groupPanelList, Collections.reverseOrder());
        }
    }

    /**
     * Сортирует заданный список объектов InputGroupPanel по текстовому полю с указанным индексом в порядке возрастания или убывания.
     *
     * @param groupPanelList список объектов InputGroupPanel, которые нужно отсортировать.
     * @param textFieldIndex индекс текстового поля, по которому нужно провести сортировку.
     * @param ascending      true для сортировки в порядке возрастания, false для сортировки в порядке убывания.
     */
    public static void sortPanelListByLabelTextField(List<InputGroupPanel> groupPanelList, int textFieldIndex, boolean ascending) {
        if (ascending) {
            Collections.sort(groupPanelList, Comparator.comparing(panel -> panel.textFieldsLeftToRight[textFieldIndex].getText()));
        }
        else {
            Collections.sort(groupPanelList, Comparator.comparing(panel -> panel.textFieldsLeftToRight[textFieldIndex].getText(),Collections.reverseOrder()));
        }
    }

    /**
     * Сортирует заданный список объектов InputGroupPanel по центральному текстовому полю в порядке возрастания или убывания.
     *
     * @param groupPanelList список объектов InputGroupPanel, которые нужно отсортировать.
     * @param ascending      true для сортировки в порядке возрастания, false для сортировки в порядке убывания.
     */
    public static void sortPanelListByCenterText(List<InputGroupPanel> groupPanelList, boolean ascending) {
        sortPanelListByLabelTextField(groupPanelList, 1, ascending);
    }

    /**
     * Сортирует заданный список объектов InputGroupPanel по правому текстовому полю в порядке возрастания или убывания.
     *
     * @param groupPanelList список объектов InputGroupPanel, которые нужно отсортировать.
     * @param ascending      true для сортировки в порядке возрастания, false для сортировки в порядке убывания.
     */
    public static void sortPanelListByRightText(List<InputGroupPanel> groupPanelList, boolean ascending) {
        sortPanelListByLabelTextField(groupPanelList, 2, ascending);
    }
}