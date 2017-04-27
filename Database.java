package vistas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;


public class Database {
	
	private Properties connectionProps;
	private Connection con;
	private Statement st;
	private DatabaseMetaData meta;
	
	public Database(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.connectionProps = new Properties();
			this.connectionProps.put("user", "a01630895");
			this.connectionProps.put("password", "tec0895");
			this.con = DriverManager.getConnection("jdbc:oracle:thin:@info.gda.itesm.mx:1521/alumXDB.gda.itesm.mx",connectionProps);
			this.st = con.createStatement();
			this.meta = con.getMetaData();
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
	
	//select
	private String[][] getQuery(String query, int size){
		String[] temp;
		List<String[]> rslt = new ArrayList<String[]>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(query);
			while(rs.next()){
				temp = new String[size];
				for (int i = 0; i < size; i++) {
					Object obj = rs.getObject(i+1);
					if(obj == null)
						obj = "";
					temp[i] = obj.toString();
					
				}
				rslt.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] result = new String[rslt.size()][size];
		rslt.toArray(result);
		return result;	
	}
	
	//Insert o update directamente
	private void updateValues(String sql){
		try {
			this.st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		} 
	}
	
	//Llamar función sql
	private void callFunction(CallableStatement cs){
		try {
			cs.executeUpdate();
			String msg = cs.getString(1);
			JOptionPane.showMessageDialog(null, msg);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	//Queries
	
	public int getNumAlmacen(String nombre){
		String sql = "select nalmacen from almacen where nombre='" + nombre +"'";
		int rslt = 0;
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next())
				rslt = rs.getInt(1);
			
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
		return rslt;
	}
	
	public String[][] getViewGerenteAlmacenista(){
		String sql = "select * from gerente_almacenistas";
		return getQuery(sql, 4);
	}
	
	public String[][] getViewGerenteAlmacenInsumos(Object nAlmacen){
		String sql = "select nombre, cantnecesaria, cantactual, estado from gerente_almacenes where nalmacen=" + nAlmacen;
		return getQuery(sql,4);
	}
	
	public String[] getViewGerenteAlmacenInfo(Object nAlmacen){
		String sql = "select * from almacen where nalmacen=" + nAlmacen;
		return getQuery(sql,4)[0];
	}
	
	public String[] getViewGerenteAlmacenItems(){
		String sql = "select nombre from almacen";
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next())
				rslt.add(rs.getString(1));
			
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}

	public String[] getGerenteInfo(){
		String sql = "select clave, nombre from gerente";
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next()){
				rslt.add(rs.getObject(1).toString());
				rslt.add(rs.getObject(2).toString());
			}
			
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}
	
	public String[] getViewGerenteInsumoItems(int almacen){
		String sql = "select p.nombre from producto p where not exists(select * from insumo where p.clave = insumo.clave and nalmacen =" + almacen + ")";
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next())
				rslt.add(rs.getString(1));
			
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}
	
	//No me sale todavía xdxd
	public void validateClave(int clave){
		String sql = "select clave from gerente where clave =" + clave;
		String sqlAlmacenista = "select clave from almacenista where clave =" + clave;
		if(clave > 1999)
			sql = sqlAlmacenista;

		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getObject(1)+"e");
			}
			
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
			e.printStackTrace();
		}
	}
	
	//Inserts
	public void insertarAlmacen(int numero, String nombre, String empresa, String direccion){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := insertar_almacen(?,?,?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.VARCHAR);
			
			cs.setString(1, "");
			cs.setInt(2, numero);
			cs.setString(3, nombre);
			cs.setString(4, direccion);
			cs.setString(5, empresa);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	public void insertarAlmacenista(int clave, String nombre, String telefono, String direccion, int claveGerente){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := insertar_almacenista(?,?,?,?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.registerOutParameter(6, Types.INTEGER);
			
			cs.setString(1, "");
			cs.setInt(2, clave);
			cs.setString(3, nombre);
			cs.setString(4, telefono);
			cs.setString(5, direccion);
			cs.setInt(6, claveGerente);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	public void insertarInsumo(int almacen, String producto, int cantnec, int cantactual){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := insert_insumo(?,?,?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.INTEGER);
			
			cs.setString(1, "");
			cs.setInt(2, almacen);
			cs.setString(3, producto);
			cs.setInt(4, cantnec);
			cs.setInt(5, cantactual);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	//Updates
	public void update_producto_nombre(String actual, String nuevo){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := update_nombre_producto(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1, "");
			cs.setString(2, nuevo);
			cs.setString(3, actual);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	public void update_cantidad(String funcion, int almacen, String nombre, int nuevo){
		CallableStatement cs;
		try{
			cs = con.prepareCall("begin ? := update_cant" + funcion + "(?,?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.setString(1, "");
			cs.setInt(2, almacen);
			cs.setString(3,nombre);
			cs.setInt(4, nuevo);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	public void update_almacenista(String funcion, String nombre, String nuevo){
		CallableStatement cs;
		try{
			cs = con.prepareCall("begin ? := update_almacenista_"+funcion+"(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1,"");
			cs.setString(2,nombre);
			cs.setString(3,nuevo);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	public void update_almacen(int nalmacen, String atributo, String nuevo){
		String sql = "update almacen set " + atributo + "='" + nuevo + "' where nalmacen=" + nalmacen;
		this.updateValues(sql);
	}
	
	public void entrada_insumo(int almacen, String nombre, int cambio){
		CallableStatement cs;
		try{
			cs = con.prepareCall("begin ? := entrada_insumo(?,?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, "");
			cs.setInt(2, almacen);
			cs.setString(3,nombre);
			cs.setInt(4,cambio);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	//delete
	public void delete_insumo(int almacen, String nombre){
		CallableStatement cs;
		try{
			cs = con.prepareCall("begin ? := delete_insumo(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1, "");
			cs.setInt(2, almacen);
			cs.setString(3,nombre);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepción " + e.getClass().getName());
		}
	}
	
	public void delete_almacenista(String nombre){
		String sql = "delete from almacenista where nombre=" + nombre;
		this.updateValues(sql);
	}
	
	
	public static void main(String[] args){
		Database db = new Database();
	}
}
