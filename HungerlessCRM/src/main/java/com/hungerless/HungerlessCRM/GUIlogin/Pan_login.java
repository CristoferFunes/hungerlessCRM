package com.hungerless.HungerlessCRM.GUIlogin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import com.hungerless.HungerlessCRM.Pair;
import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.Fra_main;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Tex_TextArea;
import com.hungerless.HungerlessCRM.login.AuthAPI;
import com.hungerless.HungerlessCRM.login.CreateAPI;
import com.hungerless.HungerlessCRM.login.User;

public class Pan_login extends JPanel
{
	private static final long serialVersionUID = 647691969139310658L;

	JPanel header;
	JPanel body;
	JPanel footer;
	OptionButton login;
	OptionButton newAccount;
	AuthAPI result;
	JLabel status;
	Matcher matcher;
	Pattern pattern;
	
	Pan_login()
	{
		this.setPreferredSize(GraphicConstants.getLoginSize());
		this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		this.setBackground(GraphicConstants.getWorkPanelColor());
		loginGUI();
	}
	
	private void loginGUI()
	{
		drop();	
		header = new JPanel();
		body = new JPanel();
		footer = new JPanel();
		
		//Header
		header.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0],GraphicConstants.getLoginSizeXY()[1]/4));
		header.setBackground(null);
		header.setLayout(new FlowLayout(FlowLayout.LEADING,30, ((GraphicConstants.getLoginSizeXY()[1]/4) - 36)/2 ));
		
		JLabel title = new JLabel("Hungerless CRM");
		title.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 36));
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font(null,Font.BOLD,28));
		
		header.add(title);
		
		//Body
		body.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0],(GraphicConstants.getLoginSizeXY()[1]/4)*2));
		body.setBackground(null);
		body.setLayout(new FlowLayout(FlowLayout.LEADING,30, (((GraphicConstants.getLoginSizeXY()[1]/4)*2)-(28*4))/5));
		
		JLabel userLabel = new JLabel("User");
		userLabel.setForeground(Color.WHITE);
		userLabel.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		userLabel.setHorizontalAlignment(JLabel.CENTER);
		userLabel.setFont(new Font(null,Font.PLAIN,20));
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		passwordLabel.setHorizontalAlignment(JLabel.CENTER);
		passwordLabel.setFont(new Font(null,Font.PLAIN,20));
		
		Tex_TextArea userText = new Tex_TextArea(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28), "");
		JPasswordField userPassword = new JPasswordField();
		userPassword.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		userPassword.setBackground(GraphicConstants.getTextAreaColor());
		userPassword.addKeyListener(new KeyAdapter()
		{
			@Override
            public void keyPressed(KeyEvent e) 
			{
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                	login(userText.getText(), String.valueOf(userPassword.getPassword()));
                }
            }
		});
		
		body.add(userLabel);
		body.add(userText);
		body.add(passwordLabel);
		body.add(userPassword);
		
		//Footer
		
		footer.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0],GraphicConstants.getLoginSizeXY()[1]/4));
		footer.setBackground(null);
		footer.setLayout(new FlowLayout(FlowLayout.LEADING, 30 , 15));
		
		login = new OptionButton(true, "Login", () -> login(userText.getText(), String.valueOf(userPassword.getPassword()) ));	
		JLabel division = new JLabel();
		division.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getButtonSizeXY()[0]*2)- (30*4), 1));
		
		newAccount = new OptionButton(true, "Crear", () -> createGUI());
		status = new JLabel();
		status.setForeground(Color.red);
		status.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - 60, 30));
		status.setHorizontalAlignment(JLabel.CENTER);
		
		footer.add(login);
		footer.add(division);
		footer.add(newAccount);
		footer.add(status);
		
		//Panel		
		
		this.add(header);
		this.add(body);
		this.add(footer);
		
		revalidate();
		repaint();
	}
	
	private void createGUI()
	{
		drop();
		
		header = new JPanel();
		body = new JPanel();
		footer = new JPanel();
		
		//Header
		header.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0],GraphicConstants.getLoginSizeXY()[1]/4));
		header.setBackground(null);
		header.setLayout(new FlowLayout(FlowLayout.LEADING,30, ((GraphicConstants.getLoginSizeXY()[1]/4) - 36)/2 ));
		
		JLabel title = new JLabel("Crear cuenta");
		title.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 36));
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font(null,Font.BOLD,28));
		
		header.add(title);
		
		//Body
		body.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0],(GraphicConstants.getLoginSizeXY()[1]/4)*2));
		body.setBackground(null);
		body.setLayout(new FlowLayout(FlowLayout.LEADING,30, (((GraphicConstants.getLoginSizeXY()[1]/4)*2)-(28*6))/7));
		
		JLabel userLabel = new JLabel("User");
		userLabel.setForeground(Color.WHITE);
		userLabel.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		userLabel.setHorizontalAlignment(JLabel.CENTER);
		userLabel.setFont(new Font(null,Font.PLAIN,20));
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		passwordLabel.setHorizontalAlignment(JLabel.CENTER);
		passwordLabel.setFont(new Font(null,Font.PLAIN,20));
		
		JLabel passwordConfirmationLabel = new JLabel("Confirm Password");
		passwordConfirmationLabel.setForeground(Color.WHITE);
		passwordConfirmationLabel.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		passwordConfirmationLabel.setHorizontalAlignment(JLabel.CENTER);
		passwordConfirmationLabel.setFont(new Font(null,Font.PLAIN,20));
		
		Tex_TextArea userText = new Tex_TextArea(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28), "");
		JPasswordField userPassword = new JPasswordField();
		userPassword.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		userPassword.setBackground(GraphicConstants.getTextAreaColor());
		JPasswordField userPasswordConfirmation = new JPasswordField();
		userPasswordConfirmation.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getMargins()*4), 28));
		userPasswordConfirmation.setBackground(GraphicConstants.getTextAreaColor());
				
		body.add(userLabel);
		body.add(userText);
		body.add(passwordLabel);
		body.add(userPassword);
		body.add(passwordConfirmationLabel);
		body.add(userPasswordConfirmation);
		
		//Footer
		footer.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0],GraphicConstants.getLoginSizeXY()[1]/4));
		footer.setBackground(null);
		footer.setLayout(new FlowLayout(FlowLayout.LEADING, 30 , 15));
		
		login = new OptionButton(true, "Crear", () -> create(userText.getText(), String.valueOf(userPassword.getPassword()), String.valueOf(userPasswordConfirmation.getPassword()) ));	
		JLabel division = new JLabel();
		division.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - (GraphicConstants.getButtonSizeXY()[0]*2)- (30*4), 1));
		
		newAccount = new OptionButton(true, "Regresar", () -> loginGUI());
		status = new JLabel();
		status.setForeground(Color.red);
		status.setPreferredSize(new Dimension(GraphicConstants.getLoginSizeXY()[0] - 60, 30));
		status.setHorizontalAlignment(JLabel.CENTER);
		
		footer.add(newAccount);
		footer.add(division);
		footer.add(login);
		footer.add(status);
		
		//Panel
		this.add(header);
		this.add(body);
		this.add(footer);
		
		revalidate();
		repaint();
	}
	
	private void drop()
	{
		try
		{
			this.remove(header);
			this.remove(body);
			this.remove(footer);
		}
		catch(NullPointerException e)
		{
			return;
		}

	}
	
	private void login(String user, String pass)
	{
		login.setEnabled(false);
		newAccount.setEnabled(false);
		status.setText("");
		
		new Thread(() -> 
		{
			result = new AuthAPI(user, pass);
			if(result.get().isEmpty()) 
			{
				login.setEnabled(true);
				newAccount.setEnabled(true);
				status.setText("Nombre de usuario no existe");
				return;
			}
			if(!((User) result.get().get(0)).isAuth())
			{
				login.setEnabled(true);
				newAccount.setEnabled(true);
				status.setText("Usuario o clave incorrectos");
				return;
			}
			StateControl.setCurrentUser((User) result.get().get(0));
			SwingUtilities.windowForComponent(this).dispose();
			new Fra_main();
		}).start();
	}
	
	private void create(String user, String pass, String pass2)
	{
		pattern = Pattern.compile("^.{8,}$");
		matcher = pattern.matcher(pass);
		
		status.setText("");
		if(user.equals(""))
		{
			status.setText("Usuario vacio");
			return;
		}
		if(!matcher.matches())
		{
			status.setText("Clave minimo 8 caracteres");
			return;
		}
		if(!pass.equals(pass2))
		{
			status.setText("Claves no coinciden");
			return;
		}
		login.setEnabled(false);
		newAccount.setEnabled(false);
		
		new Thread(() -> 
		{
			CreateAPI newUser = new CreateAPI(user, pass);
			if(!newUser.get().isEmpty()) 
			{
				login.setEnabled(true);
				newAccount.setEnabled(true);
				status.setText("Nombre de usuario ya existe");
				return;
			}
			if(newUser.post(new Pair<String, Object>(user, pass)) == null)
			{
				login.setEnabled(true);
				newAccount.setEnabled(true);
				status.setText("Intente mas tarde");
				return;
			}
			status.setForeground(Color.green);
			status.setText("Usuario creado");
			login.setEnabled(true);
			newAccount.setEnabled(true);
			
		}).start();
	}
}
