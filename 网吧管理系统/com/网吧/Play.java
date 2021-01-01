package 网吧;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Play extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement=null;
	JLabel b1= new JLabel("机器号");
	JLabel b2= new JLabel("卡号");
	JLabel b3= new JLabel("密码");
	JButton b4=new JButton("上机");
	JButton b5=new JButton("退出");
	JPanel p=new JPanel();  //面板对象
	JPanel p1=new JPanel();  //面板对象
	@SuppressWarnings("rawtypes")
	JComboBox hm= new JComboBox();
	JTextField kh=new JTextField(10);
	JPasswordField mm=new JPasswordField(10);
  public Play(){
	  setSize(350,350);
	  setTitle("欢迎上机");
	p.add(b2);
	p.add(kh);
	p.add(b3);
	p.add(mm);
	p1.add(b1);
	p1.add(hm);
	p1.add(b4);
	p1.add(b5);
	this.getContentPane().add(p,BorderLayout.NORTH);
	this.getContentPane().add(p1,BorderLayout.CENTER);
	  init();
  }
@SuppressWarnings("unchecked")
public void init(){
	   b4.addActionListener(this);
	   b5.addActionListener(this);
	   String sql="select 机器号 from 网吧.电脑 where 状态='未使用'";
		  try {
			statement = con.prepareStatement(sql);
			rs=statement.executeQuery(); //查询方法
			while(rs.next()){
				 hm.addItem(rs.getString("机器号"));
			}
			 
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
}
@SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent e) {
	if(e.getSource()==b5){
		DBconnection.Close(con, statement, rs);
		this.setVisible(false);
	}
	if(e.getSource()==b4) {
		if(kh.getText().equals("")||mm.getText().equals("")){
			JOptionPane.showMessageDialog(this, "卡号 密码不能为空");
		}
		else {
			con=DBconnection.getconn();
		try {	
			statement = con.prepareStatement("select * from 用户  where 卡号=?");
			statement.setString(1,kh.getText());
			rs=statement.executeQuery(); //查询方法
		if(rs.next()){
			String sql="select 卡状态 from 网卡 where 卡号=?";
			statement = con.prepareStatement(sql);
			statement.setString(1, kh.getText());
			rs=statement.executeQuery();
			while(rs.next()){
				if(rs.getString("卡状态").equals("上机")){
					JOptionPane.showMessageDialog(this, "该用户已上机！！");
				}
				else{ 
					String sql2="select 密码 from 用户 where 卡号=?";
					statement = con.prepareStatement(sql2);
					statement.setString(1,kh.getText());
					rs=statement.executeQuery(); //查询方法
					while(rs.next()){
					if(rs.getString("密码").equals(mm.getText())){
						String sql6="select 余额 from 网卡 where 卡号=?";
						statement = con.prepareStatement(sql6);
						statement.setString(1,kh.getText());
						rs=statement.executeQuery(); //查询方法
						while(rs.next()){
							if(rs.getInt("余额")>=5){
						JOptionPane.showMessageDialog(this, "上机成功");
						String sql3="insert into 上机情况 (卡号,机器号,上机时间,上机费用) values(?,?,now(),0)";
						statement=con.prepareStatement(sql3);
						statement.setString(1,kh.getText());
						statement.setString(2,hm.getSelectedItem().toString());
						statement.executeUpdate();
						String sql4="update 网卡 set 卡状态='上机',密码=? where 卡号=?";
						statement=con.prepareStatement(sql4);
						statement.setString(1,mm.getText());
						statement.setString(2,kh.getText());
						statement.executeUpdate();
						String sql5="update 电脑 set 状态='使用' where 机器号=?";
						statement=con.prepareStatement(sql5);
						statement.setString(1,hm.getSelectedItem().toString());
						statement.executeUpdate();
							}
							else {
								JOptionPane.showMessageDialog(this, "余额不足5元，请充值！！");
							}
					}
					}
					else {JOptionPane.showMessageDialog(this, "密码错误");}
					
					
					}
					}
			}
				
		}
		else {
			JOptionPane.showMessageDialog(this, "卡号错误");
			
		}	
		}
		
		 catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		}
	}
	
}
}
