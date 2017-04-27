
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Table extends JPanel{

	private static final long serialVersionUID = 1L;
	private String[] columnNames;
	private String[][] data;
	protected JTable table;
	private JScrollPane scrollPane;
	protected int row, column;
	protected String oldValue, newValue;
	private int num;

	public Table(String[] names, String[][] data, int num) {
		setBackground(new Color(255, 255, 255));
		this.setPreferredSize(new Dimension(735,450));
		this.num = num;
		this.columnNames = names;
		this.data = data;
		DefaultTableModel model = new DefaultTableModel(this.data, this.columnNames){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int r, int c) {
				switch(num){
				case 1:
					if(c==3 || c == 0)
						return false;
				case 2:
					if(c==0 || c == 3)
						return false;
				case 3:
					if(c == 1 || c==2 || c== 3)
						return false;
				}
		    	return true;
		    }

		   };
		this.table = new JTable(model);
		this.table.setBounds(0,0,730,360);
		if(this.num == 1)
			this.table.setPreferredScrollableViewportSize(new Dimension(700,300));
		else
			this.table.setPreferredScrollableViewportSize(new Dimension(700,374));
		this.table.setFillsViewportHeight(true);
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setBounds(0, 0, 730, 375);
		scrollPane.setBackground(new Color(255, 255, 255));
		this.add(this.scrollPane);
		this.table.setFont(new Font("Arial", Font.PLAIN, 14));
		this.table.getTableHeader().setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.table.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.table.setRowHeight(table.getRowHeight()+30);
	}
	
	public void removeRow(int rowNum){
		((DefaultTableModel) table.getModel()).removeRow(rowNum);
	}


}
