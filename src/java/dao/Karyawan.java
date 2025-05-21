/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zkoss.zul.Listitem;

/**
 *
 * @author ikwnhanif
 */
@Entity
@Table(name = "karyawan")
@NamedQueries({
    @NamedQuery(name = "Karyawan.findAll", query = "SELECT k FROM Karyawan k"),
    @NamedQuery(name = "Karyawan.findById", query = "SELECT k FROM Karyawan k WHERE k.id = :id"),
    @NamedQuery(name = "Karyawan.findByNama", query = "SELECT k FROM Karyawan k WHERE k.nama = :nama"),
    @NamedQuery(name = "Karyawan.findByNbm", query = "SELECT k FROM Karyawan k WHERE k.nbm = :nbm"),
    @NamedQuery(name = "Karyawan.findByJk", query = "SELECT k FROM Karyawan k WHERE k.jk = :jk"),
    @NamedQuery(name = "Karyawan.findByTglLahir", query = "SELECT k FROM Karyawan k WHERE k.tglLahir = :tglLahir")})
public class Karyawan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "nbm")
    private String nbm;
    @Lob
    @Column(name = "alamat")
    private String alamat;
    @Column(name = "jk")
    private Character jk;
    @Column(name = "tgl_lahir")
    @Temporal(TemporalType.DATE)
    private Date tglLahir;
    @JoinColumn(name = "id_agama", referencedColumnName = "id")
    @ManyToOne
    private Agama idAgama;
    @JoinColumn(name = "id_jabatan", referencedColumnName = "id")
    @ManyToOne
    private Jabatan idJabatan;
    @JoinColumn(name = "id_pendidikan", referencedColumnName = "id")
    @ManyToOne
    private Pendidikan idPendidikan;

    public Karyawan() {
    }

    public Karyawan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNbm() {
        return nbm;
    }

    public void setNbm(String nbm) {
        this.nbm = nbm;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Character getJk() {
        return jk;
    }

    public void setJk(Character jk) {
        this.jk = jk;
    }

    public Date getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(Date tglLahir) {
        this.tglLahir = tglLahir;
    }

    public Agama getIdAgama() {
        return idAgama;
    }

    public void setIdAgama(Agama idAgama) {
        this.idAgama = idAgama;
    }

    public Jabatan getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(Jabatan idJabatan) {
        this.idJabatan = idJabatan;
    }

    public Pendidikan getIdPendidikan() {
        return idPendidikan;
    }

    public void setIdPendidikan(Pendidikan idPendidikan) {
        this.idPendidikan = idPendidikan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Karyawan)) {
            return false;
        }
        Karyawan other = (Karyawan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Karyawan[ id=" + id + " ]";
           
    }

    public void setPendidikan(Pendidikan selectedPendidikan) {
        this.idPendidikan = selectedPendidikan;
    }

    public void setJabatan(Jabatan selectedJabatan) {
        this.idJabatan = selectedJabatan;    
    }

    public void setAgama(Agama selectedAgama) {
        this.idAgama = selectedAgama;
    }

    public Pendidikan getPendidikan() {
        return idPendidikan;
    }

    public Jabatan getJabatan() {
        return idJabatan;
    }

    public Agama getAgama() {
        return idAgama;
    }

    

    
}
