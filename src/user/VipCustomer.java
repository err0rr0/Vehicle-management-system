package user;

import deal.CarChange;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VipCustomer extends Customer {
    public VipCustomer(String s) {
        super(s);
    }

//JFrame implements ActionListener {//普通用户
//    Connection con;
//    Statement state,state1,state2,state3;
//    String sql;
//    ResultSet rs,rs_user,rs_sum;
//
//    JLabel J_label1,J_label2,J_label3;
//    JTextField J_Text1;
//    JButton J_button1,J_button2,J_button3,J_button4,J_button5,J_button6;
//    JPanel J_panel1,J_panel2,J_panel3,J_panel4,J_panel5;
//    JTable J_table1;
//    Object columns[][];
//    String username ;
//    public VipCustomer(String s)
//    {
//        this.username = s;
//        Object titles = new String[]{"序号", "车型","车牌号","租金","是否已被租用"};
//        columns = new Object[40][6];
//        J_label1 = new JLabel("你敢相信这是汽车租赁系统");
//        J_button1 = new JButton("租车信息浏览");
//        J_label2 = new JLabel("选择所要租的汽车");
//        J_label3 = new JLabel(s+" vip客户你好,欢迎你使用这个系统");
//        J_button2 = new JButton("确定");
//        J_button5 = new JButton("换车");
//        J_button6 = new JButton("查看余额");
//        J_button5.addActionListener(this);
//        J_button6.addActionListener(this);
//        J_button1.addActionListener(this);
//        J_button2.addActionListener(this);
//        J_table1 = new JTable(columns, (Object[]) titles);
//        JScrollPane pane1 = new JScrollPane(J_table1);
//        J_label1.setForeground(Color.BLUE);
//        J_Text1 = new JTextField(3);
//        J_button3 = new JButton("退出");
//        J_button3.addActionListener(this);
//        J_button4 = new JButton("可租用的车俩");
//        J_button4.addActionListener(this);
//        J_panel1 = new JPanel();
//        J_panel2 = new JPanel();
//        J_panel3 = new JPanel();
//        J_panel4 = new JPanel();
//
//        J_panel2.setLayout(new FlowLayout());
//        J_panel2.add(J_label2);
//        J_panel2.add(J_Text1);
//        J_panel2.add(J_button2);
//
//        J_panel5 = new JPanel();
//        J_panel5.add(J_button1);
//        J_panel5.add(J_button6);
//
//        J_panel4.setLayout(new BorderLayout());
//        J_panel4.add(J_button5,BorderLayout.NORTH);
//        J_panel4.add(J_button4,BorderLayout.CENTER);
//        J_panel4.add(J_button3,BorderLayout.SOUTH);
//
//        J_panel3 = new JPanel(new BorderLayout());
//        J_panel3.add(J_panel5,BorderLayout.NORTH);
//        J_panel3.add(J_panel2,BorderLayout.CENTER);
//        J_panel3.add(J_panel4,BorderLayout.SOUTH);
//
//        J_panel1 = new JPanel(new BorderLayout());
//        J_panel1.add(J_label1,BorderLayout.NORTH);
//        J_panel1.add(pane1,BorderLayout.CENTER);
//
//        this.setLayout(new BorderLayout());
//        add(J_panel3,BorderLayout.WEST);
//        add(J_panel1,BorderLayout.CENTER);
//        add(J_label3,BorderLayout.NORTH);
//        this.setSize(1000,500);
//        this.setVisible(true);
//        this.setResizable(false);
//        this.setLocation(250,250);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String DB_Drive = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://127.0.0.1:3306/cars";
        if(e.getSource() == J_button1)//车辆的信息浏览
        {
            int i = 0;
            while(i < 40)
            {
                columns[i][0] = " ";
                columns[i][1] = " ";
                columns[i][2] = " ";
                columns[i][3] = " ";
                columns[i][4] = " ";
                i++;
            }
            i = 0;
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql = "select * from car";
                rs = state.executeQuery(sql);
                System.out.println("操作成功");
                while(rs.next())
                {
                    String car_number = rs.getString("序号");
                    String car_type = rs.getString("车型");
                    String car_v = rs.getString("车牌号");
                    String car_p = rs.getString("租金");
                    String car_sf = rs.getString("是否已被租用");

                    columns[i][0] = car_number;
                    columns[i][1] = car_type;
                    columns[i][2] = car_v;
                    columns[i][3] = car_p;
                    columns[i][4] = car_sf;
                    i++;
                }
                //这里准备租车
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

        if(e.getSource() == J_button2)//选择要租赁的车序号
        {
            try
            {
                String number = J_Text1.getText();
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                state1 = con.createStatement();
                state2 = con.createStatement();
                state3 = con.createStatement();
                String sql_temp = "select * from car where 序号='"+number+"'";
                String sql_sum = "select 账户余额,已租用车序号 from user where admin='"+username+"'";
                String sql_money = "select 租金 from car where 序号='"+number+"' ";
                rs = state.executeQuery(sql_temp);
                rs_user = state1.executeQuery(sql_sum);
                rs_sum = state2.executeQuery(sql_money);

                if(rs.next()&&rs_sum.next()&&rs_user.next())
                {
                    System.out.println(rs_user.getInt("已租用车序号"));
                    if(rs.getString("是否已被租用").equals("否")&& rs_user.getInt("已租用车序号") == 0 ) {
                        String sql1="update user set 账户余额='"+(rs_user.getInt("账户余额")-rs_sum.getInt("租金")*0.8)+"' where admin='"+username+"';";
                        sql = "update car set 是否已被租用='是' where 序号='" + number + "';";
                        String sql2 ="update money set profit=profit+'"+rs_sum.getInt("租金")*0.8+"';";
                        String sql3 ="update user set 已租用车序号='"+number+"' where admin='"+username+"'";
                        state3.executeUpdate(sql);
                        state3.executeUpdate(sql1);
                        state3.executeUpdate(sql2);
                        state3.executeUpdate(sql3);
                        System.out.println("操作成功");
                        rs.close();
                        rs_sum.close();
                        rs_user.close();
                        state.close();
                        state1.close();
                        state2.close();
                        state3.close();
                        con.close();
                        repaint();
                        int i = 0;
                        while(i < 40)
                        {
                            columns[i][0] = " ";
                            columns[i][1] = " ";
                            columns[i][2] = " ";
                            columns[i][3] = " ";
                            columns[i][4] = " ";
                            i++;
                        }
                        i = 0;
                        try
                        {
                            Class.forName(DB_Drive);
                            con = DriverManager.getConnection(DB_URL,"root","mysql");
                            state = con.createStatement();
                            sql="select * from car where 序号='"+number+"';";

                            rs = state.executeQuery(sql);
                            while(rs.next())
                            {
                                String car_number = rs.getString("序号");
                                String car_type = rs.getString("车型");
                                String car_v = rs.getString("车牌号");
                                String car_p = rs.getString("租金");
                                String car_sf = rs.getString("是否已被租用");

                                columns[i][0] = car_number;
                                columns[i][1] = car_type;
                                columns[i][2] = car_v;
                                columns[i][3] = car_p;
                                columns[i][4] = car_sf;
                                i++;
                            }
                            JOptionPane.showMessageDialog(this,"租车成功","tip",JOptionPane.INFORMATION_MESSAGE,null);
                            rs.close();
                            state.close();
                            con.close();
                            repaint();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }


                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"该车可能已被租用或者您可能已经租了一辆车","警告",JOptionPane.INFORMATION_MESSAGE,null);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

        if(e.getSource() == J_button3)//退出
        {
            this.dispose();
        }

        if(e.getSource() == J_button4)//查看可租用的车辆
        {
            int i = 0;
            while(i < 40)
            {
                columns[i][0] = " ";
                columns[i][1] = " ";
                columns[i][2] = " ";
                columns[i][3] = " ";
                columns[i][4] = " ";
                i++;
            }
            i = 0;
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql = "select * from car where 是否已被租用='否'";
                rs = state.executeQuery(sql);
                System.out.println("操作成功");
                while(rs.next())
                {
                    String car_number = rs.getString("序号");
                    String car_type = rs.getString("车型");
                    String car_v = rs.getString("车牌号");
                    String car_p = rs.getString("租金");
                    String car_sf = rs.getString("是否已被租用");

                    columns[i][0] = car_number;
                    columns[i][1] = car_type;
                    columns[i][2] = car_v;
                    columns[i][3] = car_p;
                    columns[i][4] = car_sf;
                    i++;
                }
                //这里准备租车
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
        if(e.getSource() == J_button5)//换车
        {
            new CarChange(username);
        }

        if(e.getSource() == J_button6)//查看余额
        {
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql="select * from user where admin='"+username+"'";
                rs = state.executeQuery(sql);
                rs.next();
                JOptionPane.showMessageDialog(null,"余额为"+rs.getString("账户余额"),"tip",JOptionPane.INFORMATION_MESSAGE,null);
                System.out.println("hello");
                state.close();
                rs.close();
                con.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == J_button7)//查看已租用车辆
        {
            try
            {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL,"root","mysql");
                state = con.createStatement();
                sql="select * from user where admin='"+username+"'";
                rs = state.executeQuery(sql);
                if(rs.next())
                {
                    if(rs.getInt("已租用车序号") == 0)
                    {
                        JOptionPane.showMessageDialog(null,"还没有租车哦","tip",JOptionPane.INFORMATION_MESSAGE,null);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "已租入车序号为：" + rs.getString("已租用车序号"), "tip", JOptionPane.INFORMATION_MESSAGE, null);
                    }
                }
                state.close();
                rs.close();
                con.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == J_button8) {
            try {
                Class.forName(DB_Drive);
                con = DriverManager.getConnection(DB_URL, "root", "mysql");
                state = con.createStatement();
                String sql_n = "update car set 是否已被租用='否' where 序号=(select 已租用车序号 from user where admin='" + username + "')";
                sql = "update user set 已租用车序号=0 where admin='" + username + "'";
                state.executeUpdate(sql_n);
                state.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "还车成功", "tip", JOptionPane.INFORMATION_MESSAGE, null);
                state.close();
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}