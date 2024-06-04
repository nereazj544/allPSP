package Actividades.Unidad3.ServidorAritmetico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Cliente extends JFrame {
    private JTextField textFieldOperand1;
    private JTextField textFieldOperand2;
    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private JButton sqrtButton;
    private JLabel resultLabel;

    public Cliente() {
        aplicaion();
    }

    private void aplicaion() {
        setTitle("Calculator Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel operand1Label = new JLabel("Operand 1:");
        textFieldOperand1 = new JTextField();
        JLabel operand2Label = new JLabel("Operand 2:");
        textFieldOperand2 = new JTextField();
        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");
        sqrtButton = new JButton("√");
        resultLabel = new JLabel("");

        panel.add(operand1Label);
        panel.add(textFieldOperand1);
        panel.add(operand2Label);
        panel.add(textFieldOperand2);
        panel.add(addButton);
        panel.add(subtractButton);
        panel.add(multiplyButton);
        panel.add(divideButton);
        panel.add(sqrtButton);
        panel.add(resultLabel);

        add(panel, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation("add");
            }
        });

        subtractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation("subtract");
            }
        });

        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation("multiply");
            }
        });

        divideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation("divide");
            }
        });

        sqrtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation("sqrt");
            }
        });
    }

    protected void performOperation(String operacion) {
try {
            String operand1Str = textFieldOperand1.getText();
            double operand1 = Double.parseDouble(operand1Str);
            double operand2 = 0;

            if (!operacion.equals("sqrt")) {
                String operand2Str = textFieldOperand2.getText();
                operand2 = Double.parseDouble(operand2Str);
            }

            Socket socket = new Socket("localhost", 500);
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            out.writeUTF(operacion);
            out.writeDouble(operand1);
            if (!operacion.equals("sqrt")) {
                out.writeDouble(operand2);
            }

            out.flush();

            String result = in.readUTF();
            resultLabel.setText("Result: " + result);

            socket.close();
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try {
                    Cliente g = new Cliente();
                    g.setVisible(true);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
    }
}
