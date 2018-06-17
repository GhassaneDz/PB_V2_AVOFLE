package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domaine.Client;
import domaine.Compte;
import domaine.CompteCourant;
import domaine.Conseille;

public class DaoImpl implements IDao {

	public void createClient(Client clt) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql_clt = "INSERT INTO clients (prenom,nom,tel,adresse,code_Postal,ville) VALUES (?,?,?,?,?,?)";
			pstat = con.prepareStatement(sql_clt);
			pstat.setString(1, clt.getPrenom());
			pstat.setString(2, clt.getNom());
			pstat.setString(3, clt.getTel());
			pstat.setString(4, clt.getAdresse());
			pstat.setInt(5, clt.getCodePostal());
			pstat.setString(6, clt.getVille());
			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void addClient(int id_clt, int id_csl) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql_link = "INSERT INTO conseilles_clients (id_conseille, id_client) VALUES (?,?)";
			pstat = con.prepareStatement(sql_link);
			pstat.setInt(1, id_csl);
			pstat.setInt(2, id_clt);

			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void upClient(Client c) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "UPDATE clients SET prenom=?,nom=?,tel=?,adresse=?,code_Postal=?,ville=? WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1, c.getPrenom());
			pstat.setString(2, c.getNom());
			pstat.setString(3, c.getTel());
			pstat.setString(4, c.getAdresse());
			pstat.setInt(5, c.getCodePostal());
			pstat.setString(6, c.getVille());
			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void deleteClient(int id_clt) {
		PreparedStatement pstat = null;
		Connection con = null;

		removeClient(id_clt);

		try {
			con = DbUtil.seConnecter();
			String sql = "DELETE FROM clients WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id_clt);
			pstat.executeUpdate();

			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void removeClient(int id) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "DELETE FROM conseilles_clients WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id);
			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public Client getClientByID(int id) {
		PreparedStatement pstat = null;
		Connection con = null;
		ResultSet res = null;
		Client clt = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "SELECT * FROM clients WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id);
			res = pstat.executeQuery();
			con.commit();
			clt = new Client();
			while (res.next()) {
				clt.setId(res.getInt(1));
				clt.setTypeClient(res.getString(2));
				clt.setPrenom(res.getString(3));
				clt.setNom(res.getString(4));
				clt.setTel(res.getString(5));
				clt.setAdresse(res.getString(6));
				clt.setCodePostal(res.getInt(7));
				clt.setVille(res.getString(8));
			}
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			DbUtil.seDeconnecter(pstat, res, con);
		}
		return clt;
	}

	public List<Client> allClient(int id_csl) {
		PreparedStatement pstat = null;
		Connection con = null;
		ResultSet res = null;
		List<Client> listClt = new ArrayList<Client>();

		try {
			con = DbUtil.seConnecter();
			String sql = "SELECT * FROM conseilles_clients l JOIN clients c on l.id_client=c.id WHERE l.id_conseille =?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id_csl);
			res = pstat.executeQuery();
			con.commit();

			while (res.next()) {
				Client clt = new Client();
				clt.setId(res.getInt(1));
				clt.setTypeClient(res.getString(2));
				clt.setPrenom(res.getString(3));
				clt.setNom(res.getString(4));
				clt.setTel(res.getString(5));
				clt.setAdresse(res.getString(6));
				clt.setCodePostal(res.getInt(7));
				clt.setVille(res.getString(8));
				listClt.add(clt);
			}
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			DbUtil.seDeconnecter(pstat, res, con);
		}
		return listClt;
	}

	public void createCompteCourant(CompteCourant cpt) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "INSERT INTO comptescourants (solde,decouvert) VALUES (?,?)";
			pstat = con.prepareStatement(sql);
			pstat.setDouble(1, cpt.getSolde());
			pstat.setDouble(2, cpt.getDec());
			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void addCompteCourant(int id_cpt, int id_clt) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "INSERT INTO clients_comptescourants (id_client,id_comptecourant) VALUES (?,?)";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id_clt);
			pstat.setInt(2, id_cpt);
			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void upCompteCourant(CompteCourant c) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "UPDATE comptescourants SET solde=?,decouvert=? WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setDouble(1, c.getSolde());
			pstat.setDouble(2, c.getDec());
			pstat.setInt(3, c.getId());

			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void deleteCompteCourant(int id) {
		PreparedStatement pstat = null;
		Connection con = null;
		removeCompteCourant(id);
		try {
			con = DbUtil.seConnecter();
			String sql = "DELETE FROM comptescourants WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id);
			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}

	}

	public void removeCompteCourant(int id) {
		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "DELETE FROM clients_comptescourants WHERE id_comptecourant=?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id);
			pstat.executeUpdate();
			con.commit();
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}
	}

	public CompteCourant getCompteCourantByID(int id) {
		PreparedStatement pstat = null;
		Connection con = null;
		ResultSet res = null;
		CompteCourant cpt = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "SELECT * FROM comptescourants WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id);
			res = pstat.executeQuery();
			con.commit();
			cpt = new CompteCourant();
			while (res.next()) {
				cpt.setId(res.getInt(1));
				cpt.setSolde(res.getDouble(2));
				cpt.setDate(res.getString(3));
				cpt.setDec(res.getDouble(4));
			}
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			DbUtil.seDeconnecter(pstat, res, con);
		}
		return cpt;
	}

	public List<CompteCourant> allCompteCourant() {
		Statement stat = null;
		Connection con = null;
		ResultSet res = null;
		List<CompteCourant> listCpt = new ArrayList<CompteCourant>();

		try {
			con = DbUtil.seConnecter();
			String sql = "SELECT * FROM comptescourants";
			stat = con.createStatement();
			res = stat.executeQuery(sql);
			con.commit();

			while (res.next()) {
				CompteCourant cpt = new CompteCourant();
				cpt.setId(res.getInt(1));
				cpt.setSolde(res.getDouble(2));
				cpt.setDate(res.getString(3));
				cpt.setDec(res.getDouble(4));
				;
				listCpt.add(cpt);
			}
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			DbUtil.seDeconnecter(stat, res, con);
		}
		return listCpt;
	}
	
	public boolean moneyTransfer(int idComptePreleve, int idCompteCredite, double montant) {
		if(moneyPay(idComptePreleve, montant) && moneyWithdraw(idCompteCredite, montant)) {
			return true;
		}else {
			return false;
		}		
	}
	public boolean moneyWithdraw(int idCompte, double montant) {
		PreparedStatement pstat = null;
		Connection con = null;
		Compte cpt;
		cpt = getCompteCourantByID(idCompte);

		try {
			con = DbUtil.seConnecter();
			String sql = "UPDATE comptescourants SET solde=? WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setDouble(1, cpt.getSolde());
			pstat.setDouble(2, cpt.getSolde()+montant);
			pstat.executeUpdate();
			con.commit();
			return true;
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			try {
				con.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}
	}
	
	public boolean moneyPay(int idCompte, double montant) {
		PreparedStatement pstat = null;
		Connection con = null;
		Compte cpt;
		cpt = getCompteCourantByID(idCompte);

		try {
			con = DbUtil.seConnecter();
			String sql = "UPDATE comptescourants SET solde=? WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setDouble(1, cpt.getSolde());
			pstat.setDouble(2, cpt.getSolde()-montant);
			pstat.executeUpdate();
			con.commit();
			return true;
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			try {
				con.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			DbUtil.seDeconnecter(pstat, null, con);
		}
	}
	public int checkConseilleId(String login, String password) {
		PreparedStatement pstat = null;
		Connection con = null;
		ResultSet res = null;

		try {
			con = DbUtil.seConnecter();
			String sql = "SELECT id FROM conseilles WHERE login=? AND password =?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1, login);
			pstat.setString(2, password);
			res = pstat.executeQuery();
			con.commit();
			if (res.next()) {
				return res.getInt(1);
			}
			;
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, res, con);
		}
		return -1;
	}

	public Conseille getConseilleByID(int id) {
		PreparedStatement pstat = null;
		Connection con = null;
		ResultSet res = null;
		Conseille csl = new Conseille();

		try {
			con = DbUtil.seConnecter();
			String sql = "SELECT prenom, nom FROM conseilles WHERE id=?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, id);
			res = pstat.executeQuery();
			con.commit();
			if (res.next()) {
				csl.setPrenom(res.getString(1));
				csl.setNom(res.getString(2));
				return csl;
			}
			;
			// TODO mututaliser les deux erreurs
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtil.seDeconnecter(pstat, res, con);
		}
		return csl;
	}
	

}