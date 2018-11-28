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
}