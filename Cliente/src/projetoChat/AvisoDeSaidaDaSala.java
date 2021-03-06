/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoChat;

public class AvisoDeSaidaDaSala implements Coisa
{
    protected String nome;
   public AvisoDeSaidaDaSala(String nome) throws Exception
   {
       if(nome == null)
           throw new Exception("Nome não inserido corretamente!");
       this.nome = nome;
   }
   
   public String toString()
   {
       return "O usuário " + this.nome + " saiu da sala";
   }
   public int hashCode()
   {
       int ret = 666;
       ret = ret*2 + Integer.parseInt(nome);
       return ret;
   }
   
   public boolean equals(Object obj)
   {
       if(this == obj)
           return true;
       if(obj == null)
           return false;
       if (this.getClass()!=obj.getClass())
		return false;
       AvisoDeSaidaDaSala aviso = (AvisoDeSaidaDaSala)obj;       
       
       if(!aviso.nome.equals(this.nome))
           return false;
       return true;
   }
   
}