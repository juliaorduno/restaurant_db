
create or replace function update_nombre_producto(nuevo varchar, viejo varchar)
	return varchar is
	begin
		update producto
			set nombre = nuevo 
			where nombre = viejo;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_nombre_producto;

	create or replace function update_unidad_producto(nuevo varchar, viejo varchar)
	return varchar is
	begin
		update producto
			set unidad = nuevo 
			where  nombre = viejo;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_unidad_producto;

	create or replace function update_contenido_producto(nuevo varchar, viejo varchar)
	return varchar is
	begin
		update producto
			set contenido = nuevo 
			where nombre = viejo;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_contenido_producto;

create or replace function update_cantactual(almacen int, nproducto varchar, cantnueva int)
	return varchar is
	x int;
	y int;
	begin
		select clave into x from 
			producto
			where nombre = nproducto;
		select clave into y from insumo where clave = x AND nalmacen = almacen;
		update insumo
			set cantactual = cantnueva
			where clave = y AND nalmacen = almacen;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_cantactual;

create or replace function update_cantnecesaria(almacen int, nproducto varchar, cantnueva int)
	return varchar is
	x int;
	y int;
	begin
		select clave into x from 
			producto
			where nombre = nproducto;
		select clave into y from insumo where clave = x AND nalmacen = almacen;
		update insumo
			set cantnecesaria = cantnueva
			where clave = y AND nalmacen = almacen;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_cantnecesaria;



 create or replace function insertar_almacen(numero int, nombre varchar, empresa varchar, direccion varchar)
 	return varchar is
 	begin
		insert into almacen values(numero, nombre, empresa, direccion);
		return 'ok';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end;


create or replace function insert_insumo(almacen int, nproducto varchar, cantnec int, cantact int)
	return varchar is
	x int;
	begin 
		select clave into x from 
			producto
			where nombre = nproducto;
		insert into insumo values(x, almacen, cantact, cantnec);
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end insert_insumo;

	create or replace function delete_insumo(almacen int, nproducto varchar)
	return varchar is
	x int;
	begin
		select clave into x from 
			producto
			where nombre = nproducto;
		delete from insumo 
			where nalmacen = almacen AND clave = x;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end delete_insumo;

create or replace function entrada_insumo(almacen int, nproducto varchar, aumento int)
	return varchar is
	x int;
	y int;
	begin
		select clave into x from 
			producto
			where nombre = nproducto;
		select clave into y from insumo where clave = x AND nalmacen = almacen;
		update insumo
			set cantactual = cantactual+aumento;
			where clave = y;
		return 'OK';
		exception
			when no_data_found then
				insert into insumo values (x,almacen,aumento,0);
			when others then
				delete from insumo where clave = x and nalmacen = almacen;
			return dbms_utility.format_error_stack;
	end entrada_insumo;

 create or replace function insertar_almacenista(clave int, nombre varchar, telefono varchar, direccion varchar, gerente int)
 	return varchar is
 	begin
		insert into almacenista values(clave, nombre, telefono, direccion, gerente);
		return 'ok';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end insertar_almacenista;

create or replace function update_almacenista_telefono(nombreAlm varchar, nuevo varchar)
	return varchar is
	begin
		update almacenista
			set nombre = nuevo 
			where nombre = viejo;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_almacenista_nombre;

create or replace function update_almacenista_direccion(nombreAlm varchar, nuevo varchar)
	return varchar is
	begin
		update almacenista
			set direccion = nuevo 
			where nombre = nombreAlm;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_almacenista_direccion;

create or replace function delete_almacenista(nombreAlm varchar)
	return varchar is
	begin
		delete from almacenista
			where nombre = nombreAlm; 
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end delete_almacenista;

create or replace function delete_producto(nombreProd varchar)
	return varchar is
	begin
		delete from producto
			where nombre = nombreProd; 
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end delete_producto;

create or replace function update_almacen_direccion(num varchar, nuevo varchar)
	return varchar is
	begin
		update almacen
			set direccion = nuevo 
			where nAlmacen = num;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_almacen_direccion;

create or replace function update_almacen_empresa(num varchar, nuevo varchar)
	return varchar is
	begin
		update almacen
			set empresa = nuevo 
			where nAlmacen = num;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_almacen_empresa;

create or replace function update_almacen_nombre(num varchar, nuevo varchar)
	return varchar is
	begin
		update almacen
			set nombre = nuevo 
			where nAlmacen = num;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_almacen_nombre;

create or replace view gerente_insumos as
  	select p.nombre as producto, p.tipo as tipo, p.unidad as unidad, p.contenido as contenido, o.nombre as proveedor
  	from producto p, proveedor o
 	where p.claveproveedor = o.clave
	order by p.nombre;

create or replace function update_proveedor_telefono(nproveedor varchar, nuevo varchar)
	return varchar is
	begin
		update proveedor
			set telefono = nuevo
			where nombre = nproveedor;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_proveedor_telefono;

	create or replace function update_proveedor_email(nproveedor varchar, nuevo varchar)
	return varchar is
	begin
		update proveedor
			set email = nuevo
			where nombre = nproveedor;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_proveedor_email;

create or replace function update_proveedor_nombre(nproveedor varchar, nuevo varchar)
	return varchar is
	begin
		update proveedor
			set nombre = nuevo
			where nombre = nproveedor;
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end update_proveedor_nombre;

	create or replace function insert_producto(claveP int, nproducto varchar, tip varchar, un varchar, cont varchar, prov varchar, ger int)
	return varchar is
	x int;
	begin
		select clave into x from 
			proveedor
			where nombre = prov;
		insert into producto values(claveP, nproducto, tip, x, ger, un, cont);
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end insert_producto;

create or replace function insert_proveedor(claveP int, nombreP varchar, tel varchar, mail varchar)
	return varchar is
	begin
		insert into proveedor values(claveP, nombreP, tel, mail);
		return 'OK';
		exception
			when others then
				return dbms_utility.format_error_stack;
	end insert_proveedor;

create or replace trigger movement
		after update or insert of cantactual on insumo
		for each row
	declare
		 user int;
		 alm int;
		 prod int;
		 diff int;
		 fech date;
	begin
		 alm := :new.nalmacen;
		 prod := :new.clave;
		 select sysdate into fech FROM DUAL;
		 diff := :new.cantactual - :old.cantactual;
		 select clave into user from usuario u
		 where u.almacen = alm;
		 insert into movimientos values(user, alm, prod, diff, fech);
		 exception
			 when others then
			 	if SQLCODE = -1400 THEN
			 		insert into movimientos values(user, alm, prod, :new.cantactual, fech);
			 	else
					dbms_output.put_line(dbms_utility.format_error_stack);	
				end if;	 	
	end;

create or replace view gerente_almacenes as
   select nalmacen, nombre, cantnecesaria, cantactual, 'Urgente' as estado
   from insumo natural join producto
   where cantactual <= cantnecesaria*0.1
   union
    select nalmacen, nombre, cantnecesaria, cantactual, 'Bajo' as estado
    from insumo natural join producto
    where cantactual > cantnecesaria*0.1 and cantactual <= cantnecesaria*0.5
    union
   select nalmacen, nombre, cantnecesaria, cantactual, 'Suficiente' as estado
   from insumo natural join producto
   where cantactual > cantnecesaria*0.5
   order by nombre asc;


