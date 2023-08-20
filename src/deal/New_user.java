package deal;

import swing.DengLu;
import user.Administrators;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class New_user extends JFrame implements ActionListener {//注册新用户
    private JLabel J_label1,J_label2,J_label3;
    private JTextField J_text;
    private JPasswordField J_pass,J_pass1;
    private JButton J_button,J_buttont;
    private JPanel J_pane1,J_pane2,J_pane3,J_pane4,J_pane5,J_pane;
    Connection con;
    Statement state;
    ResultSet rs;
    String name;
    public New_user()
    {
        J_label1 = new JLabel("账户");
        J_label2 = new JLabel("密码");
        J_label3 = new JLabel("确认");
        J_button = new JButton("确定");
        J_buttont = new JButton("取消");
        J_button.addActionListener(this);
        J_buttont.addActionListener(this);
        J_text = new JTextField(10);
        J_pass = new JPasswordField(10);
        J_pass1 = new JPasswordField(10);
        J_pane1 = new JPanel();
        J_pane1.setLayout(new FlowLayout());
        J_pane1.add(J_label1);
        J_pane1.add(J_text);

        J_pane4 = new JPanel();
        J_pane4.setLayout(new FlowLayout());
        J_pane4.add(J_label2);
        J_pane4.add(J_pass);

        J_pane5 = new JPanel();
        J_pane5.setLayout(new FlowLayout());
        J_pane5.add(J_label3);
        J_pane5.add(J_pass1);

        J_pane2 = new JPanel();
        J_pane2.setLayout(new BorderLayout());
        J_pane2.add(J_pane1,BorderLayout.NORTH);
        J_pane2.add(J_pane4,BorderLayout.CENTER);

        J_pane3 = new JPanel();
        J_pane3.setLayout(new BorderLayout());
        J_pane3.add(J_pane5,BorderLayout.NORTH);

        J_pane = new JPanel(new BorderLayout());
        J_pane.setLayout(new BorderLayout());
        J_pane.add(J_button,BorderLayout.CENTER);
        J_pane.add(J_buttont,BorderLayout.SOUTH);

        add(J_pane2,BorderLayout.BEFORE_FIRST_LINE);
        add(J_pane3,BorderLayout.CENTER);
        add(J_pane,BorderLayout.SOUTH);
        this.setVisible(true);
        this.setSize(500,500);
        this.setLocation(250,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == J_button) {
            String sql;
            String DB_Drive = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";
            String username = J_text.getText();
            String pass = J_pass.getText();
            String pass1 = J_pass1.getText();
            if (pass.equals(pass1)) {
                try {
                    Class.forName(DB_Drive);
                    con = DriverManager.getConnection(DB_URL, "root", "mysql");
                    state = con.createStatement();
                    sql = "insert into user(admin,password,用户,账户余额,已租用车序号) values (\"" + username + "\",\"" + pass + "\",\"普通用户\",10000,0)";
                    state.executeUpdate(sql);
                    state.close();
                    con.close();
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "注册成功", "tip", JOptionPane.INFORMATION_MESSAGE, null);
                new Administrators(name);
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"请确认两次密码输入相同","警告",JOptionPane.INFORMATION_MESSAGE,null);
            }
        }

        if(e.getSource() == J_buttont)
        {
            new DengLu("登录");
            dispose();
        }
    }

    public static void main(String []args)
    {
        new New_user();
    }
}
