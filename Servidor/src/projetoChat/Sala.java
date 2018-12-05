package projetoChat;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
import  java.util.*;

public class Sala //implements Cloneable
{
	protected ArrayList <Usuario> usuarios = new ArrayList();
	protected String nome;
	protected int qtd = 0;
        protected int max = 0;
        protected int codigo;

	public Sala (int codigo, String nome, int max) throws Exception
	{
		if(nome.equals(""))
		throw new Exception ("Nome da Sala ausente");
                this.max = max;
		this.nome = nome;
                this.codigo = codigo;
	}

	public String getNome()
	{
		return this.nome;
	}
        
        public int getMax()
	{
		return this.max;
	}

	public ArrayList getUsuarios()
	{
		return this.usuarios;
	}
        
        public Usuario getUsuario(String nome)throws Exception
        {
            if(nome == "" || nome.equals(""))
                throw new Exception("Nome de usuário ausente");
            
            int n = this.usuarios.size();


		for(int i = 0; i<n ; i++)
		{
			if(this.usuarios.get(i).getNome() == nome)
			{
                return this.usuarios.get(i);
			} else {
 
                }
		}
                
        throw new Exception("Usuário não existente");
            
        }  
         public String getNomeUsu(Usuario usu)throws Exception
        {
            if(usu == null)
                throw new Exception("Nome de usuário ausente");
            
            int n = this.usuarios.size();


		for(int i = 0; i<n ; i++)
		{
			if(this.usuarios.get(i)== usu)
			{
                            return this.usuarios.get(i).getNome();
			} else {
 
                }
		}
                
        throw new Exception("Usuário não existente");
            
        }  
        
        public boolean isCheia()
        {
            return qtd == max;
        }

	public int getQtd()
	{
		return this.qtd;
	}

	public void adicionarUsu(Usuario usu)
	{
		this.usuarios.add(usu);
		this.qtd++;
	}

	public void excluirUsu (Usuario usu)
	{
		int n = this.usuarios.size();


		for(int i = 0; i<n ; i++)
		{
			if(this.usuarios.get(i) == usu)
			{
			this.usuarios.remove(i);
			this.qtd--;
			}
		}
	}

	public boolean existe(Usuario usu)
	{
		int n = this.usuarios.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.usuarios.get(i) == usu)
			return true;
		}
		return false;
	}

	public boolean existe(String nome)
	{
		int n = this.usuarios.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.usuarios.get(i).getNome().equals(nome))
			return true;
		}
		return false;
	}
	public String toString()
	{
		return "Nome :" + this.nome +"\n" + "Possui" +this.qtd+"usuários, sendo o último " + this.usuarios.get(this.usuarios.size()-1);
	}

	public int hashCode ()
	{
		 int ret = 666;

		        ret = 13*ret + Integer.parseInt(this.nome);

		        for (int pos=0; pos<this.usuarios.size(); pos++)
		        {
		            ret = 13*ret + Integer.parseInt((this.usuarios.get(pos)).toString());
		        }
		        ret = 13*ret + this.qtd;

            return ret;
	}

	public boolean equals(Sala outro)
	{
		if(this == outro)
		return true;

		if (outro==null)
		return false;

		if (this.getClass()!=outro.getClass())
		return false;

		Sala sa = (Sala)outro;

		if (this.qtd!=sa.qtd)
			return false;

		if (this.nome != sa.nome)
			return false;

		for (int i=0; i<this.usuarios.size(); i++)
		{
			if (!this.usuarios.get(i).equals(sa.usuarios.get(i)))
				return false;
		}

		return true;
            }
}
        /*
	public Sala (Sala modelo) throws Exception
		{
			if (modelo==null)
			    throw new Exception ("Modelo ausente");
                        
                        Sala sa = (Sala)modelo;
			this.nome  = modelo.nome;
			this.qtd =modelo.qtd;
			int n = this.usuarios.size();
                            for(int i = 0; i<n ; i++)
                            {
                                    (this.usuarios.get(i)= sa.usuarios.get(i);
                            }
		}
		public Object clone ()
		{
			Sala ret=null;
			try
			{
	    		ret = new Sala(this);
			}
			catch (Exception erro)
			{}
			return ret;
		}
}
*/