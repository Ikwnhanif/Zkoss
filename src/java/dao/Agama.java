/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ikwnhanif
 */
@Entity
@Table(name = "agama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agama.findAll", query = "SELECT a FROM Agama a"),
    @NamedQuery(name = "Agama.findById", query = "SELECT a FROM Agama a WHERE a.id = :id"),
    @NamedQuery(name = "Agama.findByNamaAgama", query = "SELECT a FROM Agama a WHERE a.namaAgama = :namaAgama")})
public class Agama implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nama_agama")
    private String namaAgama;
    @OneToMany(mappedBy = "idAgama")
    private Collection<Karyawan> karyawanCollection;

    public Agama() {
    }

    public Agama(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaAgama() {
        return namaAgama;
    }

    public void setNamaAgama(String namaAgama) {
        this.namaAgama = namaAgama;
    }

    @XmlTransient
    public Collection<Karyawan> getKaryawanCollection() {
        return karyawanCollection;
    }

    public void setKaryawanCollection(Collection<Karyawan> karyawanCollection) {
        this.karyawanCollection = karyawanCollection;
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
        if (!(object instanceof Agama)) {
            return false;
        }
        Agama other = (Agama) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Agama[ id=" + id + " ]";
    }
    
}
