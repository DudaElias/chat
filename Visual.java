package projetoChat;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Visual
{
	protected SalasDisponiveis salasDisp;
	protected Chat ch = null;
	private JFrame meuVisual = new JFrame("Login");
	private JButton1 botaoEnviar = new JButton1("Entrar");
	private JComboBox<String> cbSalas = new JCOmboBox<String>;
	private JLabel titulo = new JLabel();
	private JLabel sala = new JLabel();
	private JLabel nome = new JLabel();
	private JLabel ip = new JLabel();
	private JTextField nomeUsu = new JTextField();
	private JTextField nomeSala = new JTextField();
	private JTextField nomeIp = new JTextField();
    	public Visual() 
	{
        	
    	}
}