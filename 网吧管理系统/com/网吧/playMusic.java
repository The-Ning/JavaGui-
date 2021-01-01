package 网吧;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class playMusic extends JFrame implements ActionListener,Runnable{
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement;
	@SuppressWarnings("rawtypes")
	JComboBox name = new JComboBox();
	JButton b1= new JButton("播放");
	JButton b2= new JButton("QQ音乐");
	JPanel p=new JPanel();
	JPanel p1=new JPanel();
	@SuppressWarnings("unchecked")
	public playMusic(){
		setSize(400,400);
		setLayout(new BorderLayout());
		setTitle("VIP用户点歌台");
		try {
			statement=con.prepareStatement("select 歌名 from 音乐库");
			rs=statement.executeQuery();
			while(rs.next()){
				name.addItem(rs.getString("歌名"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.add(name);
		p.add(b1);
		p.add(b2);
		this.getContentPane().add(p,BorderLayout.NORTH);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	@SuppressWarnings("static-access")
	public void run(){	
		try {	
			File file = new File("E:\\Music\\"+name.getSelectedItem()+".mp3");
			FileInputStream fis= new FileInputStream(file);	
			BufferedInputStream stream = new BufferedInputStream(fis);
			Player player = new Player(stream);
			player.play();
			Thread zn = new Thread();
			zn.sleep(5000);
		} 
		 catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}
	public void actionPerformed(ActionEvent e) {
		Thread zn=new Thread(this);
		if(e.getSource()==b1){	
				zn.start();
		}
		else if(e.getSource()==b2){
			  //运行可执行文件
			  Runtime run =Runtime.getRuntime();
			  File f = new File("E:/QQ音乐/QQMusic/QQMusic.exe");
			  try {
				  run.exec(f.getAbsolutePath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			DBconnection.Close(con, statement, rs);
			
		}
	}
 
}
