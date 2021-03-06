/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sn.ferme.service;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import sn.ferme.connexionDb.DatabaseConnection;
import sn.ferme.model.ModelDepense;

/**
 *
 * @author hp
 */
public class ServiceDepense {

    private final Connection con;
    ArrayList<String> listeType = new ArrayList<String>();
    ArrayList<ModelDepense> listeDepense = new ArrayList<ModelDepense>();
    ArrayList<ModelDepense> listeDepenseType = new ArrayList<ModelDepense>();
    //  ArrayList<ModelDepense> niveauStock = new ArrayList<ModelDepense>();
    private HashMap<String, ModelDepense> niveauStock = new HashMap<>();
    ArrayList<String> listeAliment = new ArrayList<String>();

    public ServiceDepense() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public void inserType(ModelDepense type) throws SQLException {
        PreparedStatement p = con.prepareStatement("insert into type values (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        int id = genererIdentifiant();
        p.setInt(1, id);
        p.setString(2, type.getNomType());
        p.execute();

    }

    public int insertDepense(ModelDepense depense) throws SQLException {
        PreparedStatement p = con.prepareStatement("insert into depenses values (?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        p.setInt(1, 0);
        p.setInt(2, depense.getIdType());
        p.setString(3, depense.getDateDepense());
        p.setString(4, depense.getLibelle());
        p.setInt(5, depense.getMontant());
        p.setInt(6, depense.getQuantite());

        int rs = p.executeUpdate();

        return rs;
    }

    private int genererIdentifiant() throws SQLException {
        //    DecimalFormat df = new DecimalFormat("000000");
        Random ran = new Random();
        int code = ran.nextInt(1000000);
        while (verifierDoublonCode(code)) {
            code = ran.nextInt(1000000);
        }
        return code;
    }

    private boolean verifierDoublonCode(int code) throws SQLException {
        boolean doublon = false;
        PreparedStatement p = con.prepareStatement("select idType from type where idType=? limit 1");
        p.setInt(1, code);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            doublon = true;
        }
        r.close();
        p.close();
        return doublon;
    }

    public ArrayList<String> listProfession() throws SQLException {
        PreparedStatement p = con.prepareStatement("select Distinct nomType from type");
        ResultSet r = p.executeQuery();

        while (r.next()) {
            listeType.add(r.getString("nomType"));
        }
        return listeType;
    }

    public int recupererCodeType(String type) throws SQLException {
        int code = 0;
        PreparedStatement p = con.prepareStatement("select idType from type where nomType=? limit 1");
        p.setString(1, type);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            code = r.getInt("idType");
        }
        return code;
    }

    public ArrayList<ModelDepense> afficherDepense() {
        String select = "SELECT * FROM `depenses`,`type` WHERE `depenses`.`idType`=`type`.`idType` ";

        try {
            PreparedStatement ps = con.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelDepense depense = new ModelDepense();
                depense.setIdDepense(rs.getInt("idDepense"));
                depense.setLibelle(rs.getString("libelle"));
                depense.setDateDepense(rs.getString("dateDepense"));
                depense.setMontant(rs.getInt("montant"));
                depense.setNomType(rs.getString("nomType"));
                listeDepense.add(depense);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return listeDepense;
    }

    public int ligne() throws SQLException {
        int nb = 0;
        PreparedStatement p = con.prepareStatement("SELECT * FROM `depenses`,`type` WHERE `depenses`.`idType`=`type`.`idType` ");
        ResultSet r = p.executeQuery();
        if (r.last()) {
            nb = r.getRow();
        }

        return nb;
    }

    public ArrayList<ModelDepense> depenseParType() throws SQLException {
        String select = "SELECT `nomType`,SUM(`montant`) AS montant FROM `depenses`,`type` WHERE `depenses`.`idType`=`type`.`idType` group BY nomType";

        try {
            PreparedStatement ps = con.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelDepense depense = new ModelDepense();
                depense.setNomType(rs.getString("nomType"));
                depense.setMontant(rs.getInt("montant"));
                listeDepenseType.add(depense);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return listeDepenseType;
    }

    public ArrayList<String> listeAliment() throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT DISTINCT libelle FROM `depenses`, type WHERE type.idType =depenses.idType and type.nomType=?");
        p.setString(1, "Achat aliment");
        ResultSet r = p.executeQuery();

        while (r.next()) {
            listeAliment.add(r.getString("libelle"));
        }
        return listeAliment;
    }

    public HashMap<String, ModelDepense> niveauStock() throws SQLException {
        String select = "SELECT DISTINCT alimentationdujour.nomAliment as aliment, Sum(depenses.quantite) as stock , SUM(alimentationdujour.quantite) as consomes FROM `depenses`,type , alimentationdujour WHERE type.idType=depenses.idType AND type.nomType=? AND depenses.libelle=alimentationdujour.nomAliment GROUP by aliment";
        try {
            PreparedStatement ps = con.prepareStatement(select);
            ps.setString(1, "Achat aliment");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModelDepense depense = new ModelDepense();

                depense.setAliment(rs.getString("aliment"));
                depense.setStock(rs.getInt("stock"));
                depense.setConsomes(rs.getInt("consomes"));

                niveauStock.put(depense.getAliment(), depense);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return niveauStock;
    }

    public int insertAliment(ModelDepense depense) throws SQLException {
        PreparedStatement p = con.prepareStatement("insert into alimentationdujour values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        p.setInt(1, 0);
        p.setInt(2, depense.getIdUtilisateur());
        p.setString(3, depense.getNomAliment());
        p.setInt(4, depense.getQuantite());
        p.setString(5, depense.getDate());

        int rs = p.executeUpdate();
        return rs;
    }

    public int TotalDepense() throws SQLException {
        int nb = 0;
        PreparedStatement p = con.prepareStatement("SELECT sum(montant) as montant FROM `depenses`");
        ResultSet r = p.executeQuery();
        if (r.first()) {
            nb = r.getInt("montant");
        }

        return nb;
    }
}
