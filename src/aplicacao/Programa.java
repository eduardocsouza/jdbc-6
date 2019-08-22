package aplicacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Programa {

	public static void main(String[] args) {

		Connection conn = null;
		Statement st = null;
		
			try {
				conn = DB.getConnection();
				//setAutoComit: atualização automática desativada. 
				conn.setAutoCommit(false);
				
				st = conn.createStatement();
				
				int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
				
				/*
				int x = 1;				
				if(x < 2) {
					throw new SQLException("Fake erro");
				}
				*/
				int rows2 =  st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 2");
				//comit utilizado para autorizar as alterações feita no banco de dados.
				//obs: Utiliza comit quando a atualização automática é desativada(setAutoComit).
				conn.commit();
				
				System.out.println("rows1 " + rows1);
				System.out.println("rows2 " + rows2);
			}
		
			
			catch(SQLException e) {
				try {
					//rollback desfaz toda a alteração feita.
					//obs: Só é utilizado qando atualização automática é desativada(setAutoComit).
					conn.rollback();				
					throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
				}
				catch(SQLException e1) {
					throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
				}
			}
			finally {
				DB.closeStatement(st);
				DB.closeConnetion();
			}
	}	
}
