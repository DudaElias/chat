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