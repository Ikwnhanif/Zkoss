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
@Table(name = "jabatan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jabatan.findAll", query = "SELECT j FROM Jabatan j"),
    @NamedQuery(name = "Jabatan.findById", query = "SELECT j FROM Jabatan j WHERE j.id = :id"),
    @NamedQuery(name = "Jabatan.findByNamaJabatan", query = "SELECT j FROM Jabatan j WHERE j.namaJabatan = :namaJabatan")})
public class Jabatan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nama_jabatan")
    private String namaJabatan;
    @OneToMany(mappedBy = "idJabatan")
    private Collection<Karyawan> karyawanCollection;

    public Jabatan() {
    }

    public Jabatan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public void setNamaJabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
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
        if (!(object instanceof Jabatan)) {
            return false;
        }
        Jabatan other = (Jabatan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Jabatan[ id=" + id + " ]";
    }
    
}
