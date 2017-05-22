
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Implementación de Base de Datos para un restaurante usando SQL
 * @author Julia Paola Orduño
 * @author Michelle Sagnelli
 * @author Alfonso Contreras
 * @author Juan Carlos Sánchez
 */

public class Database {
	
	private Properties connectionProps;
	private Connection con;
	private Statement st;
	private DatabaseMetaData meta;
	
	/**
	 *Se realiza la conexión con la base de datos de Oracle a través de una librería de Java 
	 */
	
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
	/**
	 * Función privada para hacer selects desde la base de datos, ésta es llamada por otras funciones públicas de esta clase
	 * @param query Query a ejecutar
	 * @param size Cantidad de atributos a obtener
	 * @return Array con los datos
	 */
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
	
	/**
	 * Función privada para obtener selects de un sólo atributo
	 * @param sql
	 * @return Array con los datos
	 */
	private String[] getQuery(String sql){
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next())
				rslt.add(rs.getString(1));
			
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}
	
	/**
	 * Función privada llamada por otras funciones públicas para realizar inserts o updates 
	 * @param sql
	 */
	private void updateValues(String sql){
		try {
			this.st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		} 
	}
	
	/**
	 * Función privada para ejecutar funciones de sql
	 * @param cs Callable Statement que incluye la ejecución de la función y sus parámetros
	 */
	private void callFunction(CallableStatement cs){
		try {
			cs.executeUpdate();
			String msg = cs.getString(1);
			JOptionPane.showMessageDialog(null, msg);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	
	/**
	 * Obtener el número de almacén a partir de su nombre
	 * @param Nombre del almacén
	 * @return Número del almacén
	 */
	public int getNumAlmacen(String nombre){
		String sql = "select nalmacen from almacen where nombre='" + nombre +"'";
		int rslt = 0;
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next())
				rslt = rs.getInt(1);
			
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
		return rslt;
	}
	
	/**
	 * Obtener la tabla de los movimientos de almacenistas a partir de una vista creada en sql
	 * @return Array con los datos
	 */
	public String[][] getViewGerenteMov(){
		String sql = "select * from gerente_movimientos";
		return getQuery(sql,5);
	}
	
	/**
	 * Obtener la tabla de los movimientos de almacenistas a partir de una vista creada en sql
	 * @param sql
	 * @return Array con los datos
	 */
	public String[][] getViewGerenteMov(String sql){
		return getQuery(sql,5);
	}
	
	/**
	 * Obtener todas las tuplas de datos relevantes de los almacenistas a partir de una vista creada en SQL
	 * @return Array con los datos
	 */
	public String[][] getViewGerenteAlmacenista(){
		String sql = "select * from gerente_almacenistas";
		return getQuery(sql, 4);
	}
	
	/**
	 * Obtener los insumos correspondientes a cierto almacén
	 * @param nAlmacen 
	 * @return Array con los datos
	 */
	public String[][] getViewGerenteAlmacenInsumos(Object nAlmacen){
		String sql = "select nombre, cantnecesaria, cantactual, estado from gerente_almacenes where nalmacen=" + nAlmacen;
		return getQuery(sql,4);
	}
	
	/**
	 * Obtener datos de la tabla Producto
	 * @return 
	 */
	public String[][] getViewGerenteInsumos(){
		String sql = "select * from gerente_insumos";
		return getQuery(sql,5);
	}
	
	/**
	 * Obtener datos de la tabla Proveedor
	 * @return Array con los datos
	 */
	public String[][] getViewGerenteProveedores(){
		String sql = "select * from gerente_proveedores";
		return getQuery(sql,3);
	}
	
	/**
	 * Obtener información de determinado almacén
	 * @param nAlmacen
	 * @return Array con los datos
	 */
	public String[] getViewGerenteAlmacenInfo(Object nAlmacen){
		String sql = "select * from almacen where nalmacen=" + nAlmacen;
		return getQuery(sql,4)[0];
	}
	
	/**
	 * Obtener los nombres de todos los almacenes
	 * @return Array con los datos
	 */
	public String[] getViewGerenteAlmacenItems(){
		String sql = "select nombre from almacen";
		return getQuery(sql);
	}
	
	/**
	 * Obtener las fechas de la tabla Movimientos
	 * @return Array con los datos
	 */
	public String[] getViewGerenteFechas(){
		String sql = "select distinct trunc(fecha) from movimientos";
		String[] fechas = getQuery(sql);
		for(int i = 0; i < fechas.length; i++){
			fechas[i] = fechas[i].split("\\s+")[0];
		}
		return fechas;
	}
	
	/**
	 * Obtener el nombre de todos los proveedores
	 * @return Array con los datos
	 */
	public String[] getViewGerenteInsumoItems(){
		String sql = "select nombre from proveedor";
		return getQuery(sql);
	}

	/**
	 * Obtener clave y nombe de determinado gerente
	 * @param clave
	 * @return Array con los datos
	 */
	public String[] getGerenteInfo(String clave){
		String sql = "select clave, nombre from gerente where clave = " + clave;
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next()){
				rslt.add(rs.getObject(1).toString());
				rslt.add(rs.getObject(2).toString());
			}
			
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}
	
	/**
	 * Obtener clave y nombre de determinado almacenista
	 * @param clave
	 * @return Array con los datos
	 */
	public String[] getAlmacenistaInfo(String clave){
		String sql = "select clave, nombre from almacenista where clave = " + clave;
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next()){
				rslt.add(rs.getObject(1).toString());
				rslt.add(rs.getObject(2).toString());
			}
			
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}
	
	/**
	 * Obtener los producto que no están en existencia en cierto almacén
	 * @param almacen
	 * @return Array con los datos
	 */
	public String[] getViewGerenteInsumoItems(int almacen){
		String sql = "select p.nombre from producto p where not exists(select * from insumo where p.clave = insumo.clave and nalmacen =" + almacen + ")";
		return getQuery(sql);
	}
	
	/**
	 * Obtener clave de todos los gerentes
	 * @return Array con los datos
	 */
	public String[] getClavesGerente(){
		String sql = "select clave from gerente";
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next()){
				rslt.add(rs.getObject(1).toString());
			}
			
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}
	
	/**
	 * Obtener clave de todos los almacenistas
	 * @return Array con los datos
	 */
	public String[] getClavesAlmacenista(){
		String sql = "select clave from almacenista";
		List<String> rslt = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.st.executeQuery(sql);
			while(rs.next()){
				rslt.add(rs.getObject(1).toString());
			}
			
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
		String[] result = new String[rslt.size()];
		rslt.toArray(result);
		return result;
	}
	
	/**
	 * Insertar nuevo almacén
	 * @param numero
	 * @param nombre
	 * @param empresa
	 * @param direccion
	 */
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
			cs.setString(4, empresa);
			cs.setString(5, direccion);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Insertar nuevo almacenista
	 * @param clave
	 * @param nombre
	 * @param telefono
	 * @param direccion
	 * @param claveGerente
	 */
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
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Insertar producto nuevo
	 * @param clave
	 * @param nombre
	 * @param tipo
	 * @param unidad
	 * @param contenido
	 * @param proveedor
	 * @param claveGerente
	 */
	public void insertarProducto(int clave, String nombre, String tipo, String unidad, String contenido, String proveedor, int claveGerente){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := insert_producto(?,?,?,?,?,?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.registerOutParameter(8, Types.INTEGER);
			
			cs.setString(1, "");
			cs.setInt(2, clave);
			cs.setString(3, nombre);
			cs.setString(4, tipo);
			cs.setString(5, unidad);
			cs.setString(6, contenido);
			cs.setString(7, proveedor);
			cs.setInt(8, claveGerente);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Insertar nuevo proveedor
	 * @param clave
	 * @param nombre
	 * @param telefono
	 * @param email
	 */
	public void insertarProveedor(int clave, String nombre, String telefono, String email){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := insert_proveedor(?,?,?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.VARCHAR);
			
			cs.setString(1, "");
			cs.setInt(2, clave);
			cs.setString(3, nombre);
			cs.setString(4, telefono);
			cs.setString(5, email);
			this.callFunction(cs);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Insertar tupla a tabla Insumo
	 * @param almacen
	 * @param producto
	 * @param cantnec
	 * @param cantactual
	 */
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
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Cambiar nombre de un producto a partir de su nombre actual
	 * @param actual
	 * @param nuevo
	 */
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
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Cambiar tipo de producto a partir del nombre de éste
	 * @param actual
	 * @param nuevo
	 */
	public void update_producto_tipo(String actual, String nuevo){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := update_tipo_producto(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1, "");
			cs.setString(2, nuevo);
			cs.setString(3, actual);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Insertar tupla a tabla Usuario
	 * @param clave
	 * @param nalmacen
	 */
	public void insert_usario(int clave, int nalmacen){
		String sql = "insert into usuario values(" + clave + "," + nalmacen + ")";
		this.updateValues(sql);
	}
	
	/**
	 * Cambiar el almacén en la tabla Usuario
	 * @param clave
	 * @param nalmacen
	 */
	public void update_usuario(int clave, int nalmacen){
		String sql= "update usuario set almacen=" + nalmacen + "where clave=" +clave;
		this.updateValues(sql);
	}
	
	/**
	 * Eliminar tupla de la tabla usuario 
	 * @param clave
	 */
	public void delete_usuario(int clave){
		String sql = "delete from usuario where clave=" +clave;
		this.updateValues(sql);
	}
	
	/**
	 * Cambiar unidad de determinado producto a partir de su nombre
	 * @param actual
	 * @param nuevo
	 */
	public void update_producto_unidad(String actual, String nuevo){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := update_unidad_producto(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1, "");
			cs.setString(2, nuevo);
			cs.setString(3, actual);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Cambiar contenido de determinado producto a partir de su nombre
	 * @param actual
	 * @param nuevo
	 */
	public void update_producto_contenido(String actual, String nuevo){
		CallableStatement cs;
		try {
			cs = con.prepareCall("begin ? := update_contenido_producto(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1, "");
			cs.setString(2, nuevo);
			cs.setString(3, actual);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Actualizar pre stock o existencia de determinado insumo en determinado almacén
	 * @param funcion
	 * @param almacen
	 * @param nombre
	 * @param nuevo
	 */
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
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Actualizar información de cierto almacenista
	 * @param funcion
	 * @param nombre
	 * @param nuevo
	 */
	public void update_almacenista(String funcion, String nombre, String nuevo){
		CallableStatement cs;
		try{
			cs = con.prepareCall("begin ? := update_almacenista_"+funcion+"(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1,"");
			//cs.setInt(2, clave);
			cs.setString(2,nombre);
			cs.setString(3,nuevo);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Cambiar nombre de almacenista
	 * @param clave
	 * @param nuevo
	 */
	public void update_almacenista_nombre(int clave, String nuevo){
		CallableStatement cs;
		try{
			cs = con.prepareCall("begin ? := update_almacenista_nombre(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.NUMERIC);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1,"");
			cs.setInt(2, clave);
			cs.setString(3,nuevo);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Actualizar información de un proveedor
	 * @param funcion
	 * @param nombre
	 * @param nuevo
	 */
	public void update_proveedor(String funcion, String nombre, String nuevo){
		CallableStatement cs;
		try{
			cs = con.prepareCall("begin ? := update_proveedor_"+funcion+"(?,?); end;");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.setString(1,"");
			cs.setString(2,nombre);
			cs.setString(3,nuevo);
			this.callFunction(cs);
		} catch (SQLException e) {
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Actualizar información de un almacén
	 * @param nalmacen
	 * @param atributo
	 * @param nuevo
	 */
	public void update_almacen(int nalmacen, String atributo, String nuevo){
		String sql = "update almacen set " + atributo + "='" + nuevo + "' where nalmacen=" + nalmacen;
		this.updateValues(sql);
	}
	
	/**
	 * Eliminar tupla de la tabla Almacén
	 * @param nalmacen
	 */
	public void delete_almacen(int nalmacen){
		  String sql = "delete from almacen where nalmacen=" + nalmacen;
		  this.updateValues(sql);
	}
	
	/**
	 * Eliminar insumo de cierto almacén
	 * @param almacen
	 * @param nombre
	 */
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
			System.out.println("Excepcion " + e.getClass().getName());
		}
	}
	
	/**
	 * Eliminar tupla en la tabla Almacenista
	 * @param nombre
	 */
	public void delete_almacenista(String nombre){
		String sql = "delete from almacenista where nombre=" + nombre;
		this.updateValues(sql);
	}
	
	/**
	 * Eliminar tupla de la tabla Producto a partir del nombre de éste
	 * @param nombre
	 */
	public void delete_producto(String nombre){
		String sql = "delete from producto where nombre=" + nombre;
		this.updateValues(sql);
	}
	
	/**
	 * Eliminar tupla de la tabla Proveedor a partir del nombre de éste
	 * @param nombre
	 */
	public void delete_proveedor(String nombre){
		String sql = "delete from proveedor where nombre=" + nombre;
		this.updateValues(sql);
	}
	
	
	public static void main(String[] args){
		Database db = new Database();
		String[][] t = db.getViewGerenteProveedores();
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[i].length; j++) {
				System.out.println(t[i][j]);
			}
			
		}
	}
}
