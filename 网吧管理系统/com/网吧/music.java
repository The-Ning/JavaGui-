package ����;
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
		setTitle("���������");
		 setSize(500,600);
		 selectAll();
	}
	public  void selectAll() {
		try {
			con=DBconnection.getconn();
		String sql = "select * from ���ֿ� ";
			statement = con.prepareStatement(sql);	
			rs=statement.executeQuery(sql);  //��ѯ����
			DefaultTableModel dtm = new DefaultTableModel();  //���ģ��
			dtm.addColumn("����");
			dtm.addColumn("����");
			//��ͷ����
			
			Object [] rowdate=new Object[3];
			while(rs.next()){
				rowdate[0]=rs.getString("����");
				rowdate[1]=rs.getString("����");
				dtm.addRow(rowdate);
			}
			JTable dateTable =new JTable(dtm);  //�����ģ�͸���������
			JScrollPane gdt =new JScrollPane(dateTable);
			this.getContentPane().add(gdt,BorderLayout.CENTER);
			add(new JScrollPane(gdt)); //������
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

