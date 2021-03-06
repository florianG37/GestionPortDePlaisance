package controleur.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import controleur.dao.BateauDAO;
import controleur.dao.EmplacementDAO;
import controleur.dao.PortDAO;
import controleur.patron.BateauPatron;
import modele.Bateau;
import modele.Port;
import modele.Quai;
import vue.ApplicationPrincipaleVue;
import vue.EditerBateauVue;
import vue.EmplacementVue;
import vue.ProprietaireVue;
import vue.QuaiVue;
import vue.VoilierVue;

public class ApplicationPrincipaleControleur 
{
	
	private JComboBox comboboxQuai = ApplicationPrincipaleVue.getComboboxQuais();
	private Port port = PortDAO.retournerPort();
	private BateauPatron modele = ApplicationPrincipaleVue.getModele();
	private JTable table = ApplicationPrincipaleVue.getTable();
	private Quai quai;
	
	public ApplicationPrincipaleControleur()
	{
		ApplicationPrincipaleVue.comboboxListener(new ComboboxListener());
		ApplicationPrincipaleVue.gestionQuaisListener(new GestionQuaisListener());
		ApplicationPrincipaleVue.gestionProprietairesListener(new GestionProprietairesListener());
		ApplicationPrincipaleVue.gestionEmplacementsListener(new GestionEmplacementsListener());
		ApplicationPrincipaleVue.ajouterListener(new AjouterBateauListener());
		ApplicationPrincipaleVue.supprimerListener(new SupprimerBateauListener());
		ApplicationPrincipaleVue.modifierListener(new ModifierBateauListener());
		ApplicationPrincipaleVue.voilierListener(new VoilierListener());
		
		
		//Parcourir tout les quais du port et ajouter au combobox
		for(Quai quai : port.getListeDeQuais())
		{
			comboboxQuai.addItem(quai);
		}
		
		
		if(comboboxQuai.getSelectedItem() != null)
		{
			quai = (Quai) comboboxQuai.getSelectedItem();
			modele.refresh();
		}
		actualiserTexte(quai);
	
	}
	
	public static void actualiserTexte(Quai quai){
		int iVoilier =BateauDAO.recupererNbVoilierDunQuai(quai);
		int iMoteur =BateauDAO.recupererNbBateauMoteurDunQuai(quai);
		int iTotalDispo =EmplacementDAO.recupererNombreEmplacementCreeDansQuai(quai);
		ApplicationPrincipaleVue.rafraichissementTexteEmplacement(iVoilier+iMoteur, iTotalDispo);
		ApplicationPrincipaleVue.rafraichissementTexteBateauAVoile(iVoilier);
		ApplicationPrincipaleVue.rafraichissementTexteBateauAMoteur(iMoteur);
	}
	
	class ComboboxListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			quai = (Quai) comboboxQuai.getSelectedItem();
			modele.refresh();
			actualiserTexte(quai);
		}
	}
	
	class GestionQuaisListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			QuaiVue quaiVue = new QuaiVue(port);
		}
	}
	
	class GestionProprietairesListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			new ProprietaireVue();
		}
	}
	
	class GestionEmplacementsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			new EmplacementVue(quai);
		}
	}
	
	class AjouterBateauListener implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(EmplacementDAO.recupererNombreEmplacementOccupeDansQuai(quai) == EmplacementDAO.recupererNombreEmplacementCreeDansQuai(quai) && EmplacementDAO.recupererNombreEmplacementCreeDansQuai(quai) != quai.getNombreEmplacements())
			{
				JOptionPane.showMessageDialog(null, "Allez d'abord creer un emplacement","Erreur emplacement",JOptionPane.ERROR_MESSAGE);
			}else if(EmplacementDAO.recupererNombreEmplacementOccupeDansQuai(quai) == quai.getNombreEmplacements())
				{
					JOptionPane.showMessageDialog(null, "Ce quai ne dispose plus d'emplacement","Erreur emplacement",JOptionPane.ERROR_MESSAGE);
				}else 
				{
					new EditerBateauVue(null, "Ajouter un bateau", null);
				}
		}
	}
	
	class SupprimerBateauListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int ligneSelectionnee = table.getSelectedRow();
			
			if(ligneSelectionnee != -1)
			{
				Bateau bateau = modele.retournerBateau(ligneSelectionnee);
				BateauDAO.supprimerBateau(bateau);
				bateau.getEmplacement().setBateau(null);
				modele.refresh();
				actualiserTexte(quai);
			}
		}	
	}
	
	class ModifierBateauListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int ligneSelectionnee = table.getSelectedRow();
			
			if(ligneSelectionnee != -1)
			{
				Bateau bateau = modele.retournerBateau(ligneSelectionnee);
				
				new EditerBateauVue(null, "Modifier un bateau",bateau);

				modele.refresh();
			}
		}	
	}
	class VoilierListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			try{
			JOptionPane jop = new JOptionPane();
			String saisie = jop.showInputDialog(null,"Afficher les voiliers ayant une voile plus grande � la valeur saisie","Recherche sur voilier", JOptionPane.QUESTION_MESSAGE);
			Double valeurSaisie = Double.parseDouble(saisie);
			new VoilierVue(valeurSaisie);
			
				}catch(NumberFormatException e){
					 JOptionPane d = new JOptionPane();
				     JOptionPane.showMessageDialog( null, "Des nombres sont attendus", "Recherche sur voilier", JOptionPane.ERROR_MESSAGE);
				}catch(java.lang.NullPointerException e) {
				}
		}
	}
}