package daos;

import java.sql.*;
import bd.BDSQLServer;
import core.MeuResultSet;
import projetoChat.Sala;
import java.net.*;
import java.io.*;
import  java.util.*;

public class Salas {
    
   protected ArrayList <projetoChat.Sala> salas = new ArrayList();
   protected int qtd = 0;
    
    public Salas() throws Exception
    {
        Sala sala;
        int quantos = getSalasBd().getFetchSize();
        for(int i = 0 ; i<quantos;i++)
        {
            for(int v = 0;v<2 ; v++)
            {
                while(getSalasBd().next())
                {
                sala= new Sala(Integer.parseInt(getSalasBd().getString("codigo")),
                        getSalasBd().getString("nome"),Integer.parseInt(getSalasBd().getString("max")));
                this.adicionarSala(sala);
                }
            }
        }
        
    }
    
    public ArrayList getSalas()
    {
            return this.salas;
    }
    
    public int getQtd()
	{
		return this.qtd;
	}
    
    public int quantos()
    {
        return salas.size();
    }
    
        public void adicionarSala(projetoChat.Sala sala)
	{
            this.salas.add(sala);
            this.qtd++;
	}
        
        public void excluirUsu (projetoChat.Sala sala)
	{
		int n = this.salas.size();


		for(int i = 0; i<n ; i++)
		{
			if(this.salas.get(i) == sala)
			{
			salas.remove(i);
			this.qtd--;
			}
		}
	}
        
        public boolean existe(projetoChat.Sala sala)
	{
		int n = this.salas.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.salas.get(i) == sala)
			return true;
		}
		return false;
	}
        
        public projetoChat.Sala procurar(String nome) throws Exception
	{
		int n = this.salas.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.salas.get(i).getNome() == nome)
                            return this.salas.get(i);
			
		}
                throw new Exception("Não existe nenhuma sala com esse nome");
	}
    
    public static boolean cadastrado (String nome) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM SALAS " +
                  "WHERE NOME = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, nome);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            retorno = resultado.first(); 
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar sala");
        }

        return retorno;
    }

    public static void incluir (projetoChat.Sala sala) throws Exception
    {
        if (sala==null)
            throw new Exception ("Sala nao fornecido");

        try
        {
            String sql;

            sql = "INSERT INTO SALAS " +
                  "(CODIGO,NOME,MAX) " +
                  "VALUES " +
                  "(?,?,?)";

            BDSQLServer.COMANDO.prepareStatement (sql);
            BDSQLServer.COMANDO.setString (2, sala.getNome ());
            BDSQLServer.COMANDO.setInt  (3, sala.getMax ());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao inserir sala");
        }
    }

    public static void excluir (String nome) throws Exception
    {
        if (!cadastrado (nome))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "DELETE FROM SALAS " +
                  "WHERE NOME=?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, nome);

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao excluir sala");
        }
    }

    public static void alterar (Sala sala) throws Exception
    {
        if (sala==null)
            throw new Exception ("Sala nao fornecido");

        if (!cadastrado (sala.getNome()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE SALAS " +
                  "SET NOME=? " +
                  ", MAX=? " +
                  "WHERE CODIGO = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, sala.getNome ());
            BDSQLServer.COMANDO.setFloat  (2, sala.getMax ());
            BDSQLServer.COMANDO.setInt    (3, sala.getMax ());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao atualizar dados de sala");
        }
    }

    

    public static MeuResultSet getSalasBd () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM SALAS";

            BDSQLServer.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar salas");
        }

        return resultado;
    }
}