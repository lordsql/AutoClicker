import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JTextField clicksPerSecondField;
    private JRadioButton leftMouseButton;
    private JRadioButton rightMouseButton;
    private JButton startButton;
    private JButton stopButton;
    private JLabel currentClicksLabel;
    private AutoClicker autoClicker;
    private Timer timer;
    private int currentClicks;

    public MainWindow() {
        setTitle("Auto Clicker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        MouseController mouseController = new MouseController();
        autoClicker = new AutoClicker(mouseController);

        createAndConfigureComponents();
        addComponentsToFrame();
        addListeners();
    }

    private void createAndConfigureComponents() {
        clicksPerSecondField = new JTextField("10");
        clicksPerSecondField.setColumns(5);
        leftMouseButton = new JRadioButton("Лево");
        rightMouseButton = new JRadioButton("Право");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(leftMouseButton);
        buttonGroup.add(rightMouseButton);
        leftMouseButton.setSelected(true);
        startButton = new JButton("Старт");
        stopButton = new JButton("Стоп");
        currentClicksLabel = new JLabel("0 клики/с");
    }

    private void addComponentsToFrame() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Кликов в секунду:"), gbc);

        gbc.gridx = 1;
        add(clicksPerSecondField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(leftMouseButton, gbc);

        gbc.gridx = 1;
        add(rightMouseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(startButton, gbc);

        gbc.gridx = 1;
        add(stopButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(currentClicksLabel, gbc);
    }

    private void addListeners() {
        startButton.addActionListener(e -> {
            int clicksPerSecond = Integer.parseInt(clicksPerSecondField.getText());
            autoClicker.setClicksPerSecond(clicksPerSecond);
            autoClicker.setLeftMouseButton(leftMouseButton.isSelected());
            autoClicker.start();
            startTimer();
        });

        stopButton.addActionListener(e -> {
            autoClicker.stop();
            stopTimer();
        });
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            currentClicks = autoClicker.getCurrentClicks();
            currentClicksLabel.setText(currentClicks + " клики/с");
            currentClicks = 0;
        });
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
        currentClicksLabel.setText("0 клики/с");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}

