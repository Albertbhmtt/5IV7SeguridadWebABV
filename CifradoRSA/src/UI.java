import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.util.Arrays;
import t2rsaui.T2RSAUI;

public class UI extends JFrame {

    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JTextArea decryptResult;

    private T2RSAUI rsa;

    public UI() {
        setTitle("RSA Encryption/Decryption");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.GRAY);

        initComponents();
        initListeners();

        rsa = new T2RSAUI(1024);

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        inputTextArea = new JTextArea(5, 20);
        outputTextArea = new JTextArea(5, 20);
        encryptButton = new JButton("Cifrar");
        decryptButton = new JButton("Descifrar");
        decryptResult = new JTextArea(5, 20);

        // Cambiar el tamaño de los botones
        Font buttonFont = new Font("Arial", Font.PLAIN, 18); // Tamaño de la fuente para los botones
        encryptButton.setFont(buttonFont);
        decryptButton.setFont(buttonFont);

        // Cambiar el color de fondo y texto de los botones
        Color buttonBackgroundColor = new Color(50, 150, 200); // Color personalizado para el fondo de los botones
        Color buttonTextColor = Color.WHITE; // Color de texto para los botones

        encryptButton.setBackground(buttonBackgroundColor);
        encryptButton.setForeground(buttonTextColor);
        decryptButton.setBackground(buttonBackgroundColor);
        decryptButton.setForeground(buttonTextColor);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        panel.add(new JScrollPane(inputTextArea), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        panel.add(new JScrollPane(outputTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.gridwidth = 2;
        panel.add(new JScrollPane(decryptResult), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void initListeners() {
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = inputTextArea.getText();
                BigInteger[] ciphertext = rsa.cifrar(plaintext);
                outputTextArea.setText(arrayToString(ciphertext));
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciphertextText = outputTextArea.getText();
                String decryptedText = rsa.descifrar(stringToArray(ciphertextText));
                decryptResult.setText(decryptedText);
            }
        });
    }

    private String arrayToString(BigInteger[] array) {
        StringBuilder result = new StringBuilder();
        for (BigInteger num : array) {
            result.append(num.toString()).append(" ");
        }
        return result.toString();
    }

    private BigInteger[] stringToArray(String text) {
        String[] parts = text.split("\\s+");
        BigInteger[] result = new BigInteger[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = new BigInteger(parts[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UI().setVisible(true);
            }
        });
    }
}
