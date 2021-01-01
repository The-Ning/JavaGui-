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
public class recharge extends JFrame implements ActionListener{
	Connection con = DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement=null;
	int arr[]={5,10,15,20,30,50,100};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel b1 = new JLabel("充值一百元即可成为VIP");
	JButton b2 = new JButton("充值");
	JButton b3 = new JButton("查询余额");
	JButton b4 = new JButton("退出");
	JPanel p = new JPanel();
	JPanel p1 = new JPanel();
	@SuppressWarnings("rawtypes")
	JComboBox hm= new JComboBox();
	@SuppressWarnings("rawtypes")
	JComboBox m= new JComboBox();
  public recharge(){
	  setTitle("欢迎充值");
	  setSize(400,500);
	  init();
  }
  @SuppressWarnings("unchecked")
public void init(){
	  add(b1);
	  this.getContentPane().add(p,BorderLayout.NORTH);	  
	  this.getContentPane().add(p1,BorderLayout.CENTER);
	  p.add(b1);
	  hm.addItem("卡号");
	  for(int i=0;i<arr.length;i++)
	  {
	  m.addItem(arr[i]);
	  }
	  p1.add(hm);
	  p1.add(m);
	  p1.add(b2);
	  p1.add(b3);
	  p1.add(b4);
	  getkh();
	  b2.addActionListener(this);
	  b3.addActionListener(this);
	  b4.addActionListener(this);
  }
   @SuppressWarnings("unchecked")
public void getkh(){
	   try {
		statement=con.prepareStatement("select 卡号 from 用户");
		rs=statement.executeQuery();
		while(rs.next()){
			hm.addItem(rs.getString("卡号"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }
   private boolean isVip(){
	   boolean vip=false;
	   try {
		statement=con.prepareStatement("select 会员服务 from 网卡 where 卡号=?");
		statement.setString(1, hm.getSelectedItem().toString());
		rs=statement.executeQuery();
		while(rs.next()){
			if(rs.getString("会员服务").equals("已开通")){
				vip=true;
			}
			else {
				vip=false;
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return vip;  
   }
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource()==b2){
		try {
			statement=con.prepareStatement("update 网卡 set 余额=余额+? where 卡号=?");
			statement.setInt(1, Integer.parseInt((m.getSelectedItem().toString())));
			statement.setString(2, hm.getSelectedItem().toString());
			statement.executeUpdate();
			int money=Integer.parseInt(m.getSelectedItem().toString());
			if(money==100){
				if(isVip()){
			JOptionPane.showMessageDialog(this, "成功充值"+money+"元，您之前就是VIP");}
				else {
					statement=con.prepareStatement("update 网卡 set 会员服务='已开通' where 卡号=?");
					statement.setString(1, hm.getSelectedItem().toString());
					statement.executeUpdate();
					JOptionPane.showMessageDialog(this, "成功充值"+money+"元，并成为VIP");}
				}
			else {
				JOptionPane.showMessageDialog(this, "成功充值"+money+"元");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	else if(e.getSource()==b3){
		try {
			statement=con.prepareStatement("select 余额 from 网卡 where 卡号=?");
			statement.setString(1, hm.getSelectedItem().toString());
			rs=statement.executeQuery();
			while(rs.next()){
				JOptionPane.showMessageDialog(this, "您的当前余额："+rs.getString("余额"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	else if(e.getSource()==b4){
		DBconnection.Close(con, statement, rs);
		this.setVisible(false);
	}
}
}
