package user;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import deal.*;

public class Administrators extends JFrame implements ActionListener {//管理员
    public JButton J_button1,J_button2,J_button3,J_button4,J_button5,J_button6,J_button7,J_button8;
    public JTextField J_text1,J_text2,J_text3,J_text4;
    public JTable J_table;
    public JPanel J_panel1,J_panel2,J_panel3,J_panel4,J_panel5;
    public Object columns[][];
    public Connection con;
    public Statement state;
    public String sql;
    public ResultSet rs;
    public String DB_Drive = "com.mysql.jdbc.Driver";
    public String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";
    public String name;
    public Administrators(String s)
    {
        name = s;
        Object titles = new String[]{"用户","admin","password","账户余额","已租用的车序号","序号", "车型","车牌号","租金","是否已被租用"};
        columns = new Object[40][10];
        this.setTitle("管理员后台");
        J_button1 = new JButton("查看所有用户");
        J_button7 = new JButton("查看所有车辆");
        J_button8 = new JButton("退出");
        J_button2 = new JButton("添加车辆");

        J_button1.addActionListener(this);
        J_button2.addActionListener(this);
        J_button7.addActionListener(this);
        J_button8.addActionListener(this);
        J_button3 = new JButton("修改车辆信息");
        J_button3.addActionListener(this);
        J_text1 = new JTextField(3);
        J_text2 = new JTextField(10);
        J_text3 = new JTextField(10);
        J_button4 = new JButton("删除车辆");
        J_button4.addActionListener(this);
        J_text4 = new JTextField(10);
        J_button5 = new JButton("查看营业额");
        J_button5.addActionListener(this);

        J_table = new JTable(columns, (Object[]) titles);
        JScrollPane pane = new JScrollPane(J_table);


        J_panel1 = new JPanel();
        J_panel1.setLayout(new FlowLayout());
        J_panel1.add(J_button1);
        J_panel1.add(J_button5);

        J_panel2 = new JPanel();
        J_panel2.setLayout(new FlowLayout());
        J_panel2.add(J_button7);
        J_panel2.add(J_button2);

        J_panel4 = new JPanel();
        J_panel4.setLayout(new FlowLayout());
        J_panel4.add(J_text1);
        J_panel4.add(J_button4);
        J_panel4.add(J_button3);
        J_panel4.add(J_button8);

        J_panel3 = new JPanel();
        J_panel3.setLayout(new BorderLayout());
        J_panel3.add(J_panel1,BorderLayout.NORTH);
        J_panel3.add(J_panel2,BorderLayout.CENTER);
        J_panel3.add(J_panel4,BorderLayout.SOUTH);


        J_panel5 = new JPanel();
        J_panel5.setLayout(new BorderLayout());
        J_panel5.add(pane,BorderLayout.CENTER);



        add(J_panel3,BorderLayout.AFTER_LINE_ENDS);
        add(J_panel5,BorderLayout.CENTER);

        this.setSize(1000,500);
        this.setVisible(true);
        this.setLocation(250,250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String []args)
    {
        new Administrators("dxp");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == J_button1)//查看所有用户
        {
            int i = 0 ;
            while(i < 40)
            {
                columns[i][0] =" ";
                columns[i][1] =" ";
                columns[i][2] =" ";
                columns[i][3] =" ";
                columns[i][4] =" ";
                columns[i][5] =" ";
                columns[i][6] =" ";
                columns[i][7] =" ";
                columns[i][8] =" ";
                columns[i][9] =" ";
                i++;
            }
            i = 0;
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql = "select * from user  ";
                rs = state.executeQuery(sql);

                while(rs.next())
                {
                    String number = rs.getString("用户");
                    String username = rs.getString("admin");
                    String pass = rs.getString("password");
                    String money = rs.getString("账户余额");
                    String n = rs.getString("已租用车序号");

                    columns[i][0] =number;
                    columns[i][1] =username;
                    columns[i][2] =pass;
                    columns[i][3] =money;
                    columns[i][4] =n;
                    i++;
                }
                state.close();
                rs.close();
                con.close();
                repaint();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == J_button2)//添加车辆
        {
            new CarInput();
        }

        if(e.getSource() == J_button7)//查看所有车辆信息
        {
            int i = 0;
            while(i < 40)
            {
                columns[i][0] = " ";
                columns[i][1] = " ";
                columns[i][2] = " ";
                columns[i][3] = " ";
                columns[i][4] = " ";
                columns[i][5] = " ";
                columns[i][6] = " ";
                columns[i][7] = " ";
                columns[i][8] =" ";
                columns[i][9] =" ";
                i++;
            }
            i = 0;
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql = "select * from car order by 序号";
                rs = state.executeQuery(sql);
                System.out.println("操作成功");
                while(rs.next())
                {
                    String car_number = rs.getString("序号");
                    String car_type = rs.getString("车型");
                    String car_v = rs.getString("车牌号");
                    String car_p = rs.getString("租金");
                    String car_sf = rs.getString("是否已被租用");

                    columns[i][5] = car_number;
                    columns[i][6] = car_type;
                    columns[i][7] = car_v;
                    columns[i][8] = car_p;
                    columns[i][9] = car_sf;
                    i++;
                }
                rs.close();
                state.close();
                con.close();
                repaint();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == J_button3)//修改车辆信息
        {
            new CarUpdate();
        }

        if(e.getSource() == J_button8)//退出
        {
            dispose();
        }

        if(e.getSource() == J_button4)//删除车辆
        {
            String number = J_text1.getText();
            String DB_Drive = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql = "DELETE FROM car WHERE 序号='"+number+"'";
                state.executeUpdate(sql);
                con.close();
                state.close();
                repaint();
                JOptionPane.showMessageDialog(null,"删除成功","tip",JOptionPane.INFORMATION_MESSAGE,null);
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null,"错误操作","警告",JOptionPane.INFORMATION_MESSAGE,null);
                ex.printStackTrace();
            }
        }
        if(e.getSource() == J_button5)//查看营业额
        {
            String DB_Drive = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql = "select * from money";
                rs = state.executeQuery(sql);
                if(rs.next())
                {
                    JOptionPane.showMessageDialog(null,"总盈利为"+rs.getString("profit"),"",JOptionPane.INFORMATION_MESSAGE,null);
                }
                con.close();
                state.close();
                repaint();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null,"错误操作","警告",JOptionPane.INFORMATION_MESSAGE,null);
                ex.printStackTrace();
            }
        }
        //差最后一个盈利额
    }
}
