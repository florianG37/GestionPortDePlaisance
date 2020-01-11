package controleur.controleurVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.dao.PortDAO;
import controleur.dao.QuaiDAO;
import controleur.patronJTable.QuaiPatron;
import modele.Port;
import modele.Quai;
import vue.ApplicationPrincipaleVue;
import vue.QuaiVue;

public class QuaiControleurVue 
{
	private JTable table = QuaiVue.getTable();
	private QuaiPatron modele = QuaiVue.getModele();
	
	public QuaiControleurVue(Port port)
	{
		modele.setListesQuais(port.getListeDeQuais());
		
		QuaiVue.ajouterListener(new AjouterListener());
		QuaiVue.supprimerListener(new SupprimerListener());
		
	}
	
	class AjouterListener implements ActionListener
	{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    JTextField codeTF = new JTextField();
		    JTextField nbEmplacementsTF = new JTextField();
		    JOptionPane.showOptionDialog(null, new Object[] {"Code :", codeTF, "Nombre d'emplacements :", nbEmplacementsTF},
		      "Quais",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, null, null); 
		    
		    if(codeTF.getText().isEmpty() || nbEmplacementsTF.getText().isEmpty()){
		    	JOptionPane d = new JOptionPane();
		    	d.showMessageDialog( null, "Champ(s) manquant(s)", "Erreur ajout quai", JOptionPane.ERROR_MESSAGE);
		    }else
		    {
		    	//MANQUE A VERIFIER QUE SAISIE VALIDE ET CODE SOIT UNIQUE 
		    	//Convertir les saisies en un entier
		    	int code = Integer.parseInt(codeTF.getText());
		    	int nombreEmplacements = Integer.parseInt(nbEmplacementsTF.getText());
		    	
		    	//Ajout du quai dans la BDD
		    	QuaiDAO.ajouterQuai(code, nombreEmplacements, PortDAO.retournerPort());
		    	Quai quai = QuaiDAO.trouverQuaiAvecSonCode(code);
		    	
		    	//Ajout du quai dans la vue
		    	modele.ajouterQuai(quai);
		    	//Ajout du quai dans le combobox
		    	ApplicationPrincipaleVue.getComboboxQuais().addItem(quai);
		    }
		}
	}
	
	class SupprimerListener implements ActionListener
	{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			int ligneSelectionnee = table.getSelectedRow();
			
			if(ligneSelectionnee != -1)
			{
				Quai quai = modele.getListesQuais().get(ligneSelectionnee);
				QuaiDAO.supprimerQuai(quai);
				modele.getListesQuais().remove(ligneSelectionnee);
				modele.fireTableDataChanged();
				
				ApplicationPrincipaleVue.getComboboxQuais().removeItem(quai);
			}
		}
	}
}