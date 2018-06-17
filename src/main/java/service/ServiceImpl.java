package service;

import java.util.List;

import dao.DaoImpl;
import dao.IDao;
import domaine.Client;
import domaine.CompteCourant;
import domaine.Conseille;

/**
 * @author Adminl Classe d'implémentation de l'interface IService
 */
public class ServiceImpl implements IService {	
	IDao dao = new DaoImpl();

	// FIXME instancier le conseiller au moment du login

	public void createClient(Client c, Conseille csl) {
		dao.createClient(c);
		dao.addClient(c.getId(), csl.getId());
	}

	public void upClient(Client c) {
		dao.upClient(c);
	}

	public void deleteClient(int id) {
		dao.removeClient(id);
		dao.deleteClient(id);

	}

	public Client getClientByID(int id) {
		return dao.getClientByID(id);
	}

	public List<Client> allClient(Conseille csl) {
		return dao.allClient(csl.getId());
	}

	public void createCompteCourant(CompteCourant cpt, Client clt) {
		// TODO Recupérer un client avec les infos du front
		// TODO Créer un compte à partir du front
		dao.createCompteCourant(cpt);
		dao.addCompteCourant(cpt.getId(), clt.getId());

	}

	public void upCompteCourant(CompteCourant c) {
		dao.upCompteCourant(c);

	}

	public void deleteCompteCourant(int id) {
		dao.removeCompteCourant(id);
		dao.deleteCompteCourant(id);

	}

	public CompteCourant getCompteCourantByID(int id) {
		return dao.getCompteCourantByID(id);
	}

	public List<CompteCourant> allCompteCourant() {
		return dao.allCompteCourant();
	}

	public Conseille identifyConseille(String login, String password) {
		int idConseille;
		idConseille = dao.checkConseilleId(login, password);
		if (idConseille >= 0) {
			return dao.getConseilleByID(idConseille);
		} else {
			return null;
		}
	}

	public boolean transfer(int idComptePreleve, int idCompteCredite, double montant) {
		if (dao.getCompteCourantByID(idComptePreleve).getSolde() >= montant) {
			dao.moneyTransfer(idComptePreleve, idCompteCredite, montant);
			return true;
		}else {
			return false;
		}
	}

}