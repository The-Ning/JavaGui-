package 网吧;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class Stop extends JFrame implements ActionListener{
	private static String vipMoney,Money;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement;
	JLabel b1= new JLabel("选择卡号");
	@SuppressWarnings("rawtypes")
	JComboBox kh = new JComboBox();	
	JButton b2= new JButton("确定");
	JButton b3= new JButton("退出");
	JPanel p=new JPanel();  //面板对象
	JPanel p1=new JPanel(); 
	public Stop(){
	 setSize(350,400);
		setTitle("欢迎下机");
		init();	
 }
	@SuppressWarnings("unchecked")
	void init(){
		kh.addItem("卡号");
		this.getKH();
		 this.getContentPane().add(p,BorderLayout.NORTH);
		 this.getContentPane().add(p1,BorderLayout.CENTER);
		p.add(b1);
		p1.add(kh);
		p1.add(b2);
		p1.add(b3);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}
	@SuppressWarnings("unchecked")
	public void getKH(){
		try {
			String sql="select 卡号 from 网卡 where 卡状态='上机'";
			statement=con.prepareStatement(sql);
			rs=statement.executeQuery();
			while(rs.next()){
				kh.addItem(rs.getString("卡号"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isVip(){
		boolean vip=false;
		try {
			String sql="select 会员服务 from 网卡 where 卡号=?";
			statement=con.prepareStatement(sql);
			statement.setString(1, kh.getSelectedItem().toString());
			rs=statement.executeQuery();
			while(rs.next()){
				if(rs.getString("会员服务").equals("已开通")){
					vip=true;
					
				}
				else {vip=false;}
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
				String sql="update 上机情况  set 下机时间=now() where 卡号=? and 上机费用=0";
				statement=con.prepareStatement(sql);
				statement.setString(1, kh.getSelectedItem().toString());
				statement.executeUpdate();
				String sql1="select 机器号,上机时间,下机时间 from 上机情况 where 卡号=? and 上机费用=0";
				statement=con.prepareStatement(sql1);
				statement.setString(1, kh.getSelectedItem().toString());
				rs=statement.executeQuery();
				while(rs.next()){
					JOptionPane.showMessageDialog(this, kh.getSelectedItem().toString()+"下机成功");
					      String sql3="update 电脑 set 状态='未使用' where 机器号=?";
							statement=con.prepareStatement(sql3);
							statement.setString(1,rs.getString("机器号"));
							statement.executeUpdate();
							String sql4="update 网卡 set 卡状态='下机' where 卡号=(select 卡号 from 上机情况 where 机器号=? and 上机费用=0)";
							statement=con.prepareStatement(sql4);
							statement.setString(1,rs.getString("机器号"));
							statement.executeUpdate();
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  	
							Date sj = df.parse(rs.getString("上机时间"));
							Date xj = df.parse(rs.getString("下机时间")); 
							long diff = xj.getTime() - sj.getTime();//这样得到的差值是毫秒级别  
						      long days = diff / (1000 * 60 * 60 * 24);  
						      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60); 
						   if(isVip()){
							   if(hours>1){
								      String sql2="update 上机情况 set 上机费用=?,小时数=? where 卡号=? and 上机费用=0";
								      statement=con.prepareStatement(sql2);
								      statement.setDouble(1, hours*3);
								      statement.setDouble(2, hours);
								      statement.setString(3, kh.getSelectedItem().toString());
								      statement.executeUpdate();
								      vipMoney=Long.toString(hours*3);
								      }
								      else{
								    	  hours=1;
								    	  String sql2="update 上机情况 set 上机费用=?,小时数=? where 卡号=? and 上机费用=0";
									      statement=con.prepareStatement(sql2);
									      statement.setDouble(1, 3);
									      statement.setDouble(2, hours);
									      statement.setString(3, kh.getSelectedItem().toString());
									      statement.executeUpdate();
									       vipMoney="3";
								      }
					      
						   }   
						   else{
							   if(hours>1){
								      String sql2="update 上机情况 set 上机费用=?,小时数=? where 卡号=? and 上机费用=0";
								      statement=con.prepareStatement(sql2);
								      statement.setDouble(1, hours*5);
								      statement.setDouble(2, hours);
								      statement.setString(3, kh.getSelectedItem().toString());
								      statement.executeUpdate();
								 	  Money=Long.toString(hours*5);						     
								      }
								      else{
								    	  hours=1;
								    	  String sql2="update 上机情况 set 上机费用=?,小时数=? where 卡号=? and 上机费用=0";
									      statement=con.prepareStatement(sql2);
									      statement.setDouble(1, 5);
									      statement.setDouble(2, hours);
									      statement.setString(3, kh.getSelectedItem().toString());
									      statement.executeUpdate();
								          Money="5";									     
								      }
						   } 
				            
						     
						      String sql5="update 网卡 set 余额=余额-? where 卡号=?";
						    if(isVip()){
								    statement=con.prepareStatement(sql5);
								    statement.setDouble(1, Double.parseDouble(vipMoney));
								    statement.setString(2, kh.getSelectedItem().toString());
								    statement.executeUpdate();
						    JOptionPane.showMessageDialog(this, "VIP用户"+kh.getSelectedItem().toString()+",上机"+hours+"小时,本次消费"+vipMoney+"元");}
						    else {
						    	statement=con.prepareStatement(sql5);
							    statement.setDouble(1, Double.parseDouble(Money));
							    statement.setString(2, kh.getSelectedItem().toString());
							    statement.executeUpdate();
						    JOptionPane.showMessageDialog(this, "用户"+kh.getSelectedItem().toString()+"本次上机"+",上机"+hours+"小时,本次消费"+Money+"元");}
						    break;
				}
				String sql6="select 余额 from 网卡 where 卡号=?";	
				  statement=con.prepareStatement(sql6);
				  statement.setString(1, kh.getSelectedItem().toString());
				  rs=statement.executeQuery();
				  while(rs.next()){
					  if(rs.getInt("余额")<0){
						  int n=JOptionPane.showConfirmDialog(this,"当前余额已欠费，是否充值？","余额不足", JOptionPane.YES_NO_OPTION);
						  if(n==JOptionPane.YES_OPTION){
							  recharge re =new recharge();
							  re.setVisible(true);
						  }
						  else {
							  JOptionPane.showMessageDialog(this, "您已欠费！！请充值");
						  }
					  }
				  }
			}
			
		 
			catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  	
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.getKH();
		}

	else  if(e.getSource()==b3){
			DBconnection.Close(con, statement, rs);
			this.setVisible(false);
		}
	}
}
