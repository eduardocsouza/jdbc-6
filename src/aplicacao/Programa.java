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
				//setAutoComit: atualiza��o autom�tica desativada. 
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
				//comit utilizado para autorizar as altera��es feita no banco de dados.
				//obs: Utiliza comit quando a atualiza��o autom�tica � desativada(setAutoComit).
				conn.commit();
				
				System.out.println("rows1 " + rows1);
				System.out.println("rows2 " + rows2);
			}
		
			
			catch(SQLException e) {
				try {
					//rollback desfaz toda a altera��o feita.
					//obs: S� � utilizado qando atualiza��o autom�tica � desativada(setAutoComit).
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
