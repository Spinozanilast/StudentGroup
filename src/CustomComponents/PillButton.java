package CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *  Кнопка - фильтр с активированным или нет состоянием.
 *
 * @author Будчанин В.А.
 * @version 1.0
 */
public class PillButton extends RoundedPanel {
    /**
     * Цвет фона при активации.
     */
    private Color activatedBackgroundColor;

    /**
     * Цвет переднего плана при активации.
     */
    private Color activatedforeground;

    /**
     * Текстовая метка.
     */
    JLabel jlbPillText;

    /**
     * Флаг, указывающий, активирован ли элемент.
     */
    boolean isActivated = true;

    /**
     * Конструктор класса PillButton.
     *
     * @param text Текст кнопки.
     * @param activatedBackgroundColor Цвет фона при активации.
     * @param notActivatedBackgroundColor Цвет фона при деактивации.
     * @param isActivatedforeground Цвет текста при активации.
     * @param notActivatedforeground Цвет текста при деактивации.
     */
    public PillButton(String text, Color activatedBackgroundColor, Color notActivatedBackgroundColor,
                      Color isActivatedforeground, Color notActivatedforeground){
        setShady(false);
        this.activatedBackgroundColor = activatedBackgroundColor;
        this.activatedforeground = isActivatedforeground;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isActivated = !isActivated;
                Color backgroundColor = isActivated ? activatedBackgroundColor : notActivatedBackgroundColor;
                Color foreground = isActivated ? activatedforeground : notActivatedforeground;
                setBackground(backgroundColor);
                jlbPillText.setForeground(foreground);
            }
        });
        initView(text);
    }

    /**
     * Метод для установки шрифта метки.
     *
     * @param font Шрифт для установки.
     */
    public void setLblFont(Font font){
        jlbPillText.setFont(font);
    }

    /**
     * Метод для получения состояния активации кнопки.
     *
     * @return Состояние активации кнопки.
     */
    public boolean getPillActivated(){
        return isActivated;
    }

    /**
     * Приватный метод для инициализации вида.
     *
     * @param text Текст для отображения.
     */
    private void initView(String text) {
        setLayout(new BorderLayout());
        jlbPillText = new JLabel(text, JLabel.CENTER);
        jlbPillText.setForeground(activatedforeground);
        setBackground(activatedBackgroundColor);
        add(jlbPillText, BorderLayout.CENTER);
    }

    /**
     * Переопределенный метод для получения цвета текста.
     *
     * @return Цвет текста при активации.
     */
    @Override
    public Color getForeground() {
        return activatedforeground;
    }

    /**
     * Переопределенный метод для установки цвета текста.
     *
     * @param foreground Цвет текста для установки.
     */
    @Override
    public void setForeground(Color foreground) {
        this.activatedforeground = foreground;
    }
}
