package swing;

import deal.New_user;
import user.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class DengLu extends JFrame implements ActionListener {
    private JButton J_button1,J_button2;
    private JTextField text;
    private JPasswordField password;
    private JLabel J_label1,J_label2,J_label3;
    private JPanel J_panel1,J_panel2,J_panel3,J_panel4;
    private Connection con;
    private Statement state;
    private String sql;
    private ResultSet rs;
    public DengLu(String s) {
        super(s);
        J_label2 = new JLabel("账号：");
        J_label3 = new JLabel("密码：");
        J_button1 = new JButton("登录");
        J_button2 = new JButton("注册新用户");
        J_button2.addActionListener(this);
        J_button1.addActionListener(this);
        text = new JTextField(20);
        password = new JPasswordField(20);
        J_label1 = new JLabel("车辆租赁登录界面");
        J_panel1 = new JPanel();//界面按钮对象的实现和监听的添加
        J_panel1.setLayout(new FlowLayout());
        J_panel1.add(J_label2);
        J_panel1.add(text);
        J_panel2 = new JPanel();
        J_panel2.add(J_label3);
        J_panel2.add(password);
        J_panel3 = new JPanel();
        J_panel3.setLayout(new BorderLayout());
        add(J_panel1,BorderLayout.NORTH);
        add(J_panel2,BorderLayout.CENTER);
        J_panel4 = new JPanel();
        J_panel4.setLayout(new FlowLayout());
        J_panel4.add(J_button1);
        J_panel4.add(J_button2);
        add(J_panel4,BorderLayout.SOUTH);
        setSize(380,400);
        setVisible(true);
        this.setLocation(250,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == J_button1) //登录
        {
            String DB_Drive = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";
            String admin = text.getText();
            String pass = password.getText();
            try
            {
                Class.forName(DB_Drive);
                System.out.println("数据库连接中.......");
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                System.out.println("数据库连接成功!!");
                sql = "select * from user where admin='"+admin+"'and password='"+pass+"'";
                rs = state.executeQuery(sql);
                if(rs.next())
                {

                    if(rs.getString("用户").equals("普通用户")) {
                        new Customer(admin);
                        this.dispose();
                    }

                    if(rs.getString("用户").equals("vip用户"))
                    {
                        JOptionPane.showMessageDialog(null,"欢迎你，vip用户,vip全场8折","tip",JOptionPane.INFORMATION_MESSAGE,null);
                        new VipCustomer(admin);
                        this.dispose();
                    }

                    if(rs.getString("用户").equals("管理员"))
                    {
                        JOptionPane.showMessageDialog(null,"欢迎你，管理员","tip",JOptionPane.INFORMATION_MESSAGE,null);
                        new Administrators(admin);
                        this.dispose();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"账户名或密码错误","警告",JOptionPane.INFORMATION_MESSAGE,null);
                }
                rs.close();
                state.close();
                con.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == J_button2)//注册用户
        {
            new New_user();
            dispose();
        }
    }
    public static void main(String []args)
    {
        DengLu dxp = new DengLu("不登陆的都是hack");
    }
}
