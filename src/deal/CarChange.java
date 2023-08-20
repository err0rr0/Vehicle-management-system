package deal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CarChange extends JFrame implements ActionListener {//换车

    Connection con;
    String sql;
    Statement state,state1,state2,state3;
    ResultSet rs,rs1;
    String DB_Drive = "com.mysql.jdbc.Driver";
    String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";
    JLabel J_label1,J_label2;
    JPanel J_panel1,J_panel2,J_panel3,J_panel4,J_panel5;
    JButton J_button,J_button1;
    JTextField J_text1,J_text2;
    String name ;
    public CarChange(String s) {
        name = s;
        this.setTitle("换车");
        J_label1 = new JLabel("原来租的车序号");
        J_label2 = new JLabel("需要换车的序号");
        J_text1 = new JTextField(3);
        J_text2 = new JTextField(3);
        J_button = new JButton("确定");
        J_button.addActionListener(this);
        J_button1 = new JButton("退出");
        J_button1.addActionListener(this);

        J_panel1 = new JPanel();
        J_panel1.setLayout(new FlowLayout());
        J_panel1.add(J_label1);
        J_panel1.add(J_text1);

        J_panel2 = new JPanel();
        J_panel2.setLayout(new FlowLayout());
        J_panel2.add(J_label2);
        J_panel2.add(J_text2);

        J_panel3 = new JPanel();
        J_panel3.setLayout(new BorderLayout());

        J_panel4 = new JPanel();
        J_panel4.setLayout(new BorderLayout());
        J_panel4.add(J_panel1,BorderLayout.NORTH);
        J_panel4.add(J_panel2,BorderLayout.CENTER);

        J_panel5 = new JPanel();
        J_panel5.add(J_button);
        J_panel5.add(J_button1);

        add(J_panel3,BorderLayout.NORTH);
        add(J_panel4,BorderLayout.CENTER);
        add(J_panel5,BorderLayout.SOUTH);

        setVisible(true);
        setLocation(250,250);
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String []args)
    {
        new CarChange("lx");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == J_button)
        {
            String get = J_text1.getText();
            String send = J_text2.getText();
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                state1 = con.createStatement();
                state2 = con.createStatement();
                sql="update car set 是否已被租用=\"否\" where 序号='"+get+"'";
                String sql1 = "update car set 是否已被租用=\"是\" where 序号='"+send+"'";
                String sql2 = "update user set 已租用车序号='"+send+"' where admin='"+name+"'";
                String sql3 = "select * from car where 序号='"+send+"'";
                String sql4 = "select * from car where 序号='"+get+"'";
                rs1 = state2.executeQuery(sql3);
                rs = state1.executeQuery(sql4);
                rs.next();rs1.next();
                if(rs.getString("是否已被租用").equals("是") && rs1.getString("是否已被租用").equals("否")) {
                    state.executeUpdate(sql);
                    state.executeUpdate(sql1);
                    state.executeUpdate(sql2);

                    int m = rs1.getInt("租金") - rs.getInt("租金");
                    String sql5 = "update user set 账户余额=账户余额+'" + (-m) + "' where admin='" + name + "'";
                    state.executeUpdate(sql5);
                    JOptionPane.showMessageDialog(null, "修改成功", "tip", JOptionPane.INFORMATION_MESSAGE, null);
                    con.close();
                    state.close();
                    repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"不能换哦，已经被租了","警告",JOptionPane.INFORMATION_MESSAGE,null);
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null,"输入的序号有误或没有输入序号","警告",JOptionPane.INFORMATION_MESSAGE,null);
                ex.printStackTrace();
            }
        }
        if(e.getSource() == J_button1)
        {
            dispose();
        }
    }

}
