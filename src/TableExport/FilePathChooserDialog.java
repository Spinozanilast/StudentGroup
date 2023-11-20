package TableExport;

import javax.swing.*;
import java.io.*;
public class FilePathChooserDialog {

    public enum FileType {
        EXCEL, WORD
    }

    public static String createFile(FileType fileType) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\"));
        Action details = fileChooser.getActionMap().get("Go Up");
        details.actionPerformed(null);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith("." + getFileExtension(fileType)) || f.isDirectory();
            }

            public String getDescription() {
                return getFileDescription(fileType);
            }
        });

        int ret = fileChooser.showDialog(null, "Сохранить файл");

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                try (PrintWriter out = new PrintWriter(file.getAbsoluteFile())) {
                    out.print("Test text");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Случились непредвиденные проблемы, быть может файл используется чем-то.");
            }
            return file.getAbsolutePath();
        }
        return null;
    }

    private static String getFileExtension(FileType fileType) {
        return switch (fileType) {
            case EXCEL -> "xlsx";
            case WORD -> "docx";
            default -> throw new IllegalArgumentException("Unsupported file type");
        };
    }

    private static String getFileDescription(FileType fileType) {
        return switch (fileType) {
            case EXCEL -> "Excel Files";
            case WORD -> "Word Files";
            default -> throw new IllegalArgumentException("Unsupported file type");
        };
    }
}
