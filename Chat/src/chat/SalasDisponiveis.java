/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;

public class SalasDisponiveis extends Salas implements Coisa
{
    protected ArrayList <Sala> salas = new ArrayList();
    protected int qtd = 0;
    
    public SalasDisponiveis() throws Exception
    {
         super() ;      
    }
    public void guardar()
    {
        int quantos = super.qtd;
        ArrayList salinhas = super.getSalas();
        for(int i = 0 ; i< quantos;i++)
        {
            if(!((Sala)salinhas.get(i)).isCheia())
            {
                salas.add((Sala)salinhas.get(i));
                qtd++;
            }
        }
    }
    
    public void verificar()
    {
        int quantos = super.qtd;
        ArrayList salinhas = super.getSalas();
        for(int i = 0 ; i< quantos;i++)
        {
            if(((Sala)salinhas.get(i)).isCheia())
            {
                salas.remove((Sala)salinhas.get(i));
                qtd--;
            }
        }
    }
    
    public ArrayList getSalas()
    {
        return salas;
    }
    
    public Sala getSala(String nome) throws Exception
    {
        for(int i = 0; i<salas.size();i++)
        {
            if(salas.get(i).nome == nome)
                return salas.get(i);
        }
        throw new Exception ("Sala não encontrada");
    }
    
    public String toString()
    {
        return "Possui :"+this.qtd+"elementos";
    }
    
    public int hashCode()
    {
        int ret = 666;
        ret = 2*ret + this.qtd;
        for(int i = 0 ; i<this.salas.size(); i++)
        ret = 2*ret + Integer.parseInt(this.salas.get(i).toString());
        
        return ret;
    }
    
    public boolean equals (Object outro)
    {
        if (this==outro)
            return true;

        if (outro==null)
            return false;

        SalasDisponiveis sd = (SalasDisponiveis)outro;

        if (this.qtd!=sd.qtd)
            return false;

        if (this.salas!=sd.salas)
            return false;

        return true;
    }
    
    
}
