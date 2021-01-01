package 网吧;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Newuser extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement=null;
	JLabel b1=new JLabel("卡号");
	JLabel b2=new JLabel("用户名");
	JLabel b3=new JLabel("密码");
	JLabel b4=new JLabel("确认密码");
	JTextField kh=new JTextField(5);
	JTextField name=new JTextField(5);
	JPasswordField password=new JPasswordField(5);
	JPasswordField password1=new JPasswordField(5);
	JButton b5=new JButton("注册");
	JButton b6=new JButton("注销");
	JButton b7=new JButton("修改密码");
	Box basebox,box1,box2;
	JPanel p = new JPanel();
	JPanel p1= new JPanel();
	public Newuser(){
    	setBounds(500,300,400,400);
    	setTitle("新用户注册");
    	setLayout(new FlowLayout());
    	init();
   
    }
 void init(){
	 box1=Box.createVerticalBox();
	 box1.add(b1);
	 box1.add(Box.createVerticalStrut(8));
	 box1.add(b2);
	 box1.add(Box.createVerticalStrut(8));
	 box1.add(b3);
	 box1.add(Box.createVerticalStrut(8));
	 box1.add(b4);
	 box2=Box.createVerticalBox();
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(kh);
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(name);
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(password);
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(password1);
	 basebox=Box.createHorizontalBox();
	 basebox.add(box1);
	 basebox.add(Box.createHorizontalStrut(20));
	 basebox.add(box2);
	 p.add(basebox);
	 p1.add(b5);
	 p1.add(b6);
	 p1.add(b7);
	 this.getContentPane().add(p,BorderLayout.NORTH);
	 this.getContentPane().add(p1,BorderLayout.CENTER);
	 b5.addActionListener(this);
	 b6.addActionListener(this);
	 b7.addActionListener(this);
	 if(this.isVisible()){
		 DBconnection.Close(con, statement, rs);
	 }
    }
 @SuppressWarnings("deprecation")
private void setPassword(){
	 try {
		statement=con.prepareStatement("select 密码  from 用户  where 卡号=?");
	    statement.setString(1, kh.getText());
	    rs=statement.executeQuery();
	    while(rs.next()){
	    	if(rs.getString("密码").equals(password.getText())){
	    	String pass=JOptionPane.showInputDialog(this, "验证成功，请输入新密码");
	    	String pass1=JOptionPane.showInputDialog(this, "再确认密码");
	    	if(pass.equals("")||pass1.equals("")){
	    		JOptionPane.showMessageDialog(this, "您的输入为空，则不修改密码");
	    	}
	    	else if(pass.equals(pass1)){
	    		statement=con.prepareStatement("update 用户  set 密码=? where 卡号=?");
	    		statement.setString(1,pass1);
	    		statement.setString(2,kh.getText());
	    		statement.executeUpdate();
	    		statement=con.prepareStatement("update 网卡  set 密码=? where 卡号=?");
	    		statement.setString(1,pass1);
	    		statement.setString(2,kh.getText());
	    		statement.executeUpdate();  		
	    		JOptionPane.showMessageDialog(this, "密码修改成功");	    			    			      		
	    	}
	    
	    	else{
	    		JOptionPane.showMessageDialog(this, "密码与确认密码不一致");
	    	}
	    }
	    
	    
	    else{
	    	JOptionPane.showMessageDialog(this,"密码错误");
	    }
	 }
	 } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
private boolean isPlay(){
	boolean play=false;
	try {
		statement=con.prepareStatement("select 卡状态 from 网卡  where 卡号=?");
		statement.setString(1, kh.getText());
		rs=statement.executeQuery();
		while(rs.next()){
			if((rs.getString("卡状态").equals("上机"))){
				play=true;
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return play;
}
private void delete(){
	try {
		statement=con.prepareStatement("delete  from 用户 where 卡号=?");
		statement.setString(1, kh.getText());
		statement.executeUpdate();
		statement=con.prepareStatement("delete from 网卡 where 卡号=?");
		statement.setString(1, kh.getText());
		statement.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@SuppressWarnings("deprecation")
private void insert(){
	  try {
		statement=con.prepareStatement("insert into 用户 (卡号,密码,用户名) values(?,?,?)");
	    statement.setString(1, kh.getText());
	    statement.setString(2, password.getText());
	    statement.setString(3, name.getText());
	    statement.executeUpdate();  
	    statement=con.prepareStatement("insert into 网卡 (卡号,用户名,密码,卡状态,余额,会员服务) values(?,?,?,'下机',0,'未开通')");
	    statement.setString(1, kh.getText());
	    statement.setString(2, name.getText());
	    statement.setString(3, password.getText());
	    statement.executeUpdate();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
@SuppressWarnings("deprecation")
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource()==b6){
		if(kh.getText().equals("")||password.getText().equals("")|password1.getText().equals("")||name.getText().equals(""))
		{
		   JOptionPane.showMessageDialog(this, "不能为空!!");
		}
		else{
			try {
				statement=con.prepareStatement("select * from 用户  where 卡号=?");
				statement.setString(1, kh.getText());
				rs=statement.executeQuery();
					if(!rs.next()){
					JOptionPane.showMessageDialog(this, "该账户不存在");
					}
					else {
						if(isPlay()){
							JOptionPane.showMessageDialog(this, "请先下机！！");
						}
						else{
						if(password.getText().equals(password1.getText())){
							String sql="select 余额 from 网卡 where 卡号=?";
							statement=con.prepareStatement(sql);
							statement.setString(1, kh.getText());
							rs=statement.executeQuery();
							while(rs.next()){
								if(rs.getInt("余额")>=0){
									delete();
									JOptionPane.showMessageDialog(this, "注销成功");
								}
							
								else{		
								int n=JOptionPane.showConfirmDialog(this, "请缴完欠款","",JOptionPane.YES_NO_OPTION);
						        if(n==JOptionPane.YES_OPTION){
							    recharge re = new recharge();
							    re.setVisible(true);
						         }
						        }
								
						         }
							
							
						}
						  else {
							JOptionPane.showMessageDialog(this, "确认密码与密码不一样");
						    }
					} 	
					}} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	else if(e.getSource()==b5){
		if(kh.getText().equals("")||password.getText().equals("")|password1.getText().equals("")||name.getText().equals(""))
		{
		   JOptionPane.showMessageDialog(this, "不能为空!!");
		}
		else{
			try {
			statement=con.prepareStatement("select * from 用户  where 卡号=?");
			statement.setString(1, kh.getText());
			rs=statement.executeQuery();
			
				if(rs.next()){
				JOptionPane.showMessageDialog(this, "该账户已注册");
				}
				else {
					if(password.getText().equals(password1.getText())){
						insert();
						JOptionPane.showMessageDialog(this, "注册成功");
						int n=JOptionPane.showConfirmDialog(this, "是否现在充值？","",JOptionPane.YES_NO_OPTION);
					if(n==JOptionPane.YES_OPTION){
						recharge re = new recharge();
						re.setVisible(true);
					}
					}
					else {
						JOptionPane.showMessageDialog(this, "确认密码与密码不一样");
					}
				} 	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	}
	else if(e.getSource()==b7){
		if(kh.getText().equals("")||password.getText().equals("")|password1.getText().equals("")||name.getText().equals(""))
		{
		   JOptionPane.showMessageDialog(this, "不能为空!!");
		}
		else {
			try {
				statement=con.prepareStatement("select * from 用户 where 卡号=?");
			    statement.setString(1, kh.getText());
			    rs=statement.executeQuery();
			    if(rs.next()){
			    	if(password.getText().equals(password1.getText())){
			    		setPassword();
			    	}
			    	else{
			    		JOptionPane.showMessageDialog(this, "确认密码与密码不一致");
			    	}
			    }
			    else{
			    	JOptionPane.showMessageDialog(this, "卡号错误");
			    }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
}
