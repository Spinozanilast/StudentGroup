package Forms;

import Forms.models.GroupModel;
import customComponents.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Start form.
 */
public class StartForm extends JFrame {
    private final Color BACKGROUND_COLOR = new Color(243,243,243);
    private final Color GROUP_PANEL_BACKGROUND_COLOR = new Color(239,255,255);
    private final Color GROUP_NON_EDITABLE_FOREGROUND = new Color(29,105,200);
    private final Color TEXT_BOXES_BACKGROUND_COLOR = new Color(193,228,228, 179);
    private final Font FONT_NON_EDITABLE = new Font("Montserrat", Font.BOLD, 20);
    private final ThreeActionLabelsPanel threeActionLabelsPanel = new ThreeActionLabelsPanel(Color.BLACK, BACKGROUND_COLOR, "Номер группы", "Номер курса", "ФИО старосты");
    private final GroupModel model;
    private JPanel layoutGroupsPanel;
    private AddGroupPanel addGroupPanel;

    public StartForm() {
        model = new GroupModel();
        initFormState();
        initLayoutPanel();
        setStartInputState();
    }

    private void initFormState(){
        setTitle("Студенческая группа");
        ImageIcon icon = new ImageIcon("assets/GroupIcon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        layoutGroupsPanel = new JPanel();
    }

    private void initLayoutPanel(){
        layoutGroupsPanel.setBackground(BACKGROUND_COLOR);
        layoutGroupsPanel.setLayout(new BoxLayout(layoutGroupsPanel, BoxLayout.Y_AXIS));
        layoutGroupsPanel.add(Box.createVerticalStrut(15));
        JScrollPane scrollPane = new JScrollPane(layoutGroupsPanel);
        add(layoutGroupsPanel);
    }

    private void setStartInputState() {
        threeActionLabelsPanel.getLeftLabel().addMouseListener(getLeftLabelMouseListener());
        threeActionLabelsPanel.getCenterLabel().addMouseListener(getCenterLabelMouseListener());
        threeActionLabelsPanel.getRightLabel().addMouseListener(getRightLabelMouseListener());
        addComponent(threeActionLabelsPanel, false, false);
        AddGroupPanel addGroupPanel = new AddGroupPanel(Color.BLACK, GROUP_PANEL_BACKGROUND_COLOR);
        this.addGroupPanel = addGroupPanel;
        addComponentMouseListener(addGroupPanel, getAddGroupPanelMouseListener());
    }

    private MouseAdapter getLabelMouseListener(ThreeActionLabelsPanel.LabelType labelType) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (model.getGroupPanels().isEmpty()) {
                    return;
                }
                layoutGroupsPanel.removeAll();
                layoutGroupsPanel.add(Box.createVerticalStrut(15));
                addComponent(threeActionLabelsPanel, false, false);

                switch (labelType) {
                    case LEFT:
                        InputGroupPanel.sortGroupListByLeftText(model.getGroupPanels(), !threeActionLabelsPanel.isLeftArrowDown());
                        threeActionLabelsPanel.setLeftLabelIcon(threeActionLabelsPanel.isLeftArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                    case CENTER:
                        InputGroupPanel.sortPanelListByCenterText(model.getGroupPanels(), !threeActionLabelsPanel.isCenterArrowDown());
                        threeActionLabelsPanel.setCenterLabelIcon(threeActionLabelsPanel.isCenterArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                    case RIGHT:
                        InputGroupPanel.sortPanelListByRightText(model.getGroupPanels(), !threeActionLabelsPanel.isRightArrowDown());
                        threeActionLabelsPanel.setRightLabelIcon(threeActionLabelsPanel.isRightArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                }

                addAllGroupPanels();
                layoutGroupsPanel.revalidate();
                layoutGroupsPanel.repaint();
            }
        };
    }

    private void addAllGroupPanels() {
        for (InputGroupPanel groupPanel : model.getGroupPanels()) {
            addComponent(groupPanel, true, true);
            JPanel utilitiesPanel = getGroupsUtilitiesPanel(groupPanel);
            addComponent(utilitiesPanel, false, false);
            addComponent(addGroupPanel, false, true);
        }
        layoutGroupsPanel.revalidate();
        layoutGroupsPanel.repaint();
    }

    private MouseAdapter getLeftLabelMouseListener() {
        return getLabelMouseListener(ThreeActionLabelsPanel.LabelType.LEFT);
    }

    private MouseAdapter getCenterLabelMouseListener() {
        return getLabelMouseListener(ThreeActionLabelsPanel.LabelType.CENTER);
    }

    private MouseAdapter getRightLabelMouseListener() {
        return getLabelMouseListener(ThreeActionLabelsPanel.LabelType.RIGHT);
    }

    private MouseAdapter getAddGroupPanelMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InputGroupPanel inputGroupPanel = new InputGroupPanel(Color.BLACK, GROUP_PANEL_BACKGROUND_COLOR, TEXT_BOXES_BACKGROUND_COLOR, "№ Группы", "Курс", "ФИО старосты");
                addComponent(inputGroupPanel, false, false);
                addGroupPanel.repaint();
                layoutGroupsPanel.revalidate();
                HighResolutionImagePanel highResolutionImagePanel = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Tick-Circle.png", 35, 35), 640, 45);
                addComponent(highResolutionImagePanel, false, true);
                safelyDeleteComponent(addGroupPanel);
                highResolutionImagePanel.setFocusable(true);
                highResolutionImagePanel.addMouseListener(getHighResolutionImagePanelMouseListener(inputGroupPanel, highResolutionImagePanel));
                highResolutionImagePanel.addKeyListener(getHighResolutionImagePanelKeyListener(inputGroupPanel, highResolutionImagePanel));
            }
        };
    }

    private MouseAdapter getHighResolutionImagePanelMouseListener(InputGroupPanel inputGroupPanel, HighResolutionImagePanel highResolutionImagePanel) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getResultGroupByInputValidity(inputGroupPanel, highResolutionImagePanel);
            }
        };
    }

    private KeyAdapter getHighResolutionImagePanelKeyListener(InputGroupPanel inputGroupPanel, HighResolutionImagePanel highResolutionImagePanel) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    getResultGroupByInputValidity(inputGroupPanel, highResolutionImagePanel);
                }
            }
        };
    }

    private void getResultGroupByInputValidity(InputGroupPanel inputGroupPanel, HighResolutionImagePanel highResolutionImagePanel) {
        if (!inputGroupPanel.isInputValid()){
            inputGroupPanel.setTextFieldsUnValid();
            return;
        }
        else {
            inputGroupPanel.setTextFieldsValid();
        }
        inputGroupPanel.setPanelNonEditableCustom(GROUP_PANEL_BACKGROUND_COLOR, GROUP_NON_EDITABLE_FOREGROUND, FONT_NON_EDITABLE);
        JPanel utilitiesPanel = getGroupsUtilitiesPanel(inputGroupPanel);
        safelyDeleteComponent(highResolutionImagePanel);
        addComponent(utilitiesPanel, false, false);
        addComponent(this.addGroupPanel, false, true);
        model.addGroupPanel(inputGroupPanel);
    }

    private static JPanel getGroupsUtilitiesPanel(InputGroupPanel inputGroupPanel) {
        JPanel groupPanel = new JPanel();
        groupPanel.setOpaque(false);
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.X_AXIS));
        HighResolutionImagePanel openGroupFormPanel = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Group-Form-Open-Icon.png", 25,24), 30, 45);
        HighResolutionImagePanel editGroupData = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Edit-Group-Icon.png", 25,27), 30, 45);
//        openGroupFormPanel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                GroupForm groupForm = new GroupForm(inputGroupPanel.getTextFieldValues()[0],
//                        inputGroupPanel.getTextFieldValues()[1],
//                        inputGroupPanel.getTextFieldValues()[2]);
//                groupForm.setVisible(true);
//            }
//        });
        editGroupData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //int panelIndex = model.getGroupPanels().indexOf(groupPanel);
            }
        });
        groupPanel.add(openGroupFormPanel);
        groupPanel.add(Box.createHorizontalStrut(100));
        groupPanel.add(editGroupData);
        groupPanel.setMaximumSize(new Dimension(150, 45));
        return groupPanel;
    }

    private void addComponent(Component component, boolean hasVerticalStrutBefore, boolean hasVerticalStrutAfter) {
        if (hasVerticalStrutBefore) {
            layoutGroupsPanel.add(Box.createVerticalStrut(15));
        }
        layoutGroupsPanel.add(component);
        if (hasVerticalStrutAfter) {
            layoutGroupsPanel.add(Box.createVerticalStrut(15));
        }
    }

    private void addComponentMouseListener(AddGroupPanel addGroupPanel,
                                           MouseAdapter mouseAdapter) {
        addComponent(addGroupPanel, false, true);
        addGroupPanel.setMouseClickEvent(mouseAdapter);
    }

    private void safelyDeleteComponent(Component component){
        layoutGroupsPanel.remove(component);
        layoutGroupsPanel.revalidate();
        layoutGroupsPanel.repaint();
    }

    public void clearGroupsList(){
        model.clearGroupPanels();
    }
}
