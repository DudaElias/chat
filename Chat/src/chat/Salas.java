/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;



public class Salas {
    protected ArrayList <Sala> salas = new ArrayList();
    protected int qtd = 0;
    
    
        public Salas () throws Exception
	{
	
        }
        
        public ArrayList getSalas()
        {
            return salas;
        }

	

	public int getQtd()
	{
		return this.qtd;
	}

	public void adicionarSala(Sala sala)
	{
		this.salas.add(sala);
		this.qtd++;
	}

	public void excluirUsu (Sala sala)
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

	public boolean existe(Sala sala)
	{
		int n = this.salas.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.salas.get(i) == sala)
			return true;
		}
		return false;
	}
        
        public Sala procurar(String nome) throws Exception
	{
		int n = this.salas.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.salas.get(i).nome == nome)
                            return this.salas.get(i);
			
		}
                throw new Exception("Não existe nenhuma sala com esse nome");
	}

	public String toString()
	{
		return "Possui" +this.qtd+"salas, sendo o último " + this.salas.get(this.salas.size()-1);
	}

	public int hashCode ()
	{
		 int ret = 666;

		        for (int pos=0; pos<this.salas.size(); pos++)
		        {
		            ret = 13*ret + Integer.parseInt((this.salas.get(pos)).toString());
		        }
		        ret = 13*ret + this.qtd;

            return ret;
	}

	public boolean equals(Salas outro)
	{
		if(this == outro)
		return true;

		if (outro==null)
		return false;

		if (this.getClass()!=outro.getClass())
		return false;

		Salas sa = (Salas)outro;

		if (this.qtd!=sa.qtd)
			return false;

		for (int i=0; i<this.salas.size(); i++)
		{
			if (!this.salas.get(i).equals(sa.salas.get(i)))
				return false;
		}

		return true;
            }
}
    

