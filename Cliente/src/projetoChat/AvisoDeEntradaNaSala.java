/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoChat;

public class AvisoDeEntradaNaSala implements Coisa
{
   protected String nome;
   public AvisoDeEntradaNaSala(String nome) throws Exception
   {
       if(nome == null)
           throw new Exception("Nome não inserido corretamente!");
       this.nome = nome;
   }
   
   public String toString()
   {
       return "O usuário " + this.nome + " entrou na sala";
   }
}
