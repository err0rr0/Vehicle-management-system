package deal;
import user.Administrators;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CarInput extends JFrame implements ActionListener{//添加车辆
    JLabel J_label1,J_label2,J_label3,J_label4,J_label5;
    JTextField J_text1,J_text2,J_text3,J_text4,J_text5;
    JButton J_button1,J_button2;
    JTable J_table;
    JPanel J_panel1,J_panel2;
    Connection con;
    Statement state;
    String sql;
    ResultSet rs;

    public CarInput()
    {
        J_label1 = new JLabel("序号");
        J_label2 = new JLabel("车型");
        J_label3 = new JLabel("车牌");
        J_label4 = new JLabel("租金");
        J_label5 = new JLabel("是否已被租用");

        J_text1 = new JTextField(10);
        J_text2 = new JTextField(10);
        J_text3 = new JTextField(10);
        J_text4 = new JTextField(10);
        J_text5 = new JTextField(10);

        J_button1 = new JButton("提交");
        J_button1.addActionListener(this);
        J_button2 = new JButton("返回菜单");
        J_button2.addActionListener(this);

        this.setLayout(new BorderLayout());
        add(new JScrollPane(J_table));

        J_panel1 = new JPanel();
        J_panel1.setLayout(new FlowLayout());
        J_panel1.add(J_label1);
        J_panel1.add(J_text1);
        J_panel1.add(J_label2);
        J_panel1.add(J_text2);
        J_panel1.add(J_label3);
        J_panel1.add(J_text3);
        J_panel1.add(J_label4);
        J_panel1.add(J_text4);
        J_panel1.add(J_label5);
        J_panel1.add(J_text5);

        J_panel2 = new JPanel();
        J_panel2.setLayout(new FlowLayout());
        J_panel2.add(J_button1);
        J_panel2.add(J_button2);
        J_panel1.setLayout(new GridLayout(3,4));
        add(J_panel2,BorderLayout.SOUTH);
        add(J_panel1,BorderLayout.CENTER);
        setVisible(true);
        setLocation(250,250);
        setSize(400,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String []args)
    {
        new CarInput();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == J_button1)
        {
            String number = J_text1.getText();
            String type = J_text2.getText();
            String v = J_text3.getText();
            String rent = J_text4.getText();
            String boo = J_text5.getText();
            String DB_Drive = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";

            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql = "insert into car(序号,车型,车牌号,租金,是否已被租用) values('"+number+"','"+type+"','"+v+"','"+rent+"','"+boo+"')";
                state.executeUpdate(sql);
                con.close();
                state.close();
                repaint();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(null,"添加成功","tip",JOptionPane.INFORMATION_MESSAGE,null);
        }


        if(e.getSource() == J_button2)
        {
            dispose();
        }
    }
}
