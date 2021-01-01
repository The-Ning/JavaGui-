package 网吧;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class music extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con =null;
  PreparedStatement statement;
  ResultSet rs;
	public  music(){
		setTitle("网吧曲库表");
		 setSize(500,600);
		 selectAll();
	}
	public  void selectAll() {
		try {
			con=DBconnection.getconn();
		String sql = "select * from 音乐库 ";
			statement = con.prepareStatement(sql);	
			rs=statement.executeQuery(sql);  //查询方法
			DefaultTableModel dtm = new DefaultTableModel();  //表格模型
			dtm.addColumn("歌名");
			dtm.addColumn("歌手");
			//表头部分
			
			Object [] rowdate=new Object[3];
			while(rs.next()){
				rowdate[0]=rs.getString("歌名");
				rowdate[1]=rs.getString("歌手");
				dtm.addRow(rowdate);
			}
			JTable dateTable =new JTable(dtm);  //将表格模型赋给表格对象
			JScrollPane gdt =new JScrollPane(dateTable);
			this.getContentPane().add(gdt,BorderLayout.CENTER);
			add(new JScrollPane(gdt)); //滚动条
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		finally{
			if(con!=null&&statement!=null&&rs!=null)
				DBconnection.Close(con,statement,rs);
		}
	}
}

