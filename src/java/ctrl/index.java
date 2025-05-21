package ctrl;

import bsh.ParseException;
import dao.Agama;
import dao.Jabatan;
import dao.Karyawan;
import dao.Pendidikan;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class index extends GenericForwardComposer {

    static EntityManagerFactory emf=Persistence.createEntityManagerFactory("latihan2PU");
     static EntityManager em=emf.createEntityManager();
     private EntityTransaction tx;

    // Komponen UI
    private Window winUtama;
    private Textbox txtNama, txtNBM, txtAlamat;
    private Radiogroup rgJK;
    private Datebox tglLahir;
    private Combobox cbPendidikan, cbJabatan, cbAgama;
    private Button btnSimpan, btnTutup, btnRefresh;
    private Listbox listboxKaryawan;

    
    // Data untuk binding
    private List<Pendidikan> listPendidikan;
    private List<Jabatan> listJabatan;
    private List<Agama> listAgama;
    private List<Karyawan> listKaryawan;

    // Model utama
    private Karyawan karyawan;

//    @Override
//    public void doAfterCompose(Component comp) throws Exception {
//        super.doAfterCompose(comp);
//        em = Persistence.createEntityManagerFactory("latihan2PU").createEntityManager();
//
//        
//        
//    }
    public void onCreate$winUtama()throws InterruptedException, ParseException {
        String sql = "select * from karyawan limit 50";
//        Messagebox.show(sql);
        listKaryawan = em.createNativeQuery(sql, Karyawan.class)
                            .setHint("eclipselink.refresh", "true")
                            .getResultList();
        karyawan = (Karyawan) listKaryawan.get(0);
        loadMasterData();
        initKaryawan();
        fillComboboxes();
        winUtama.invalidate();
    }
    

    private void loadMasterData() {
        tx = em.getTransaction();
        try {
            tx.begin();
            listPendidikan = em.createQuery("SELECT p FROM Pendidikan p", Pendidikan.class).getResultList();
            listJabatan = em.createQuery("SELECT j FROM Jabatan j", Jabatan.class).getResultList();
            listAgama = em.createQuery("SELECT a FROM Agama a", Agama.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    private void fillComboboxes() {
        cbPendidikan.getItems().clear();
        for (Pendidikan p : listPendidikan) {
            Comboitem item = new Comboitem(p.getNamaPendidikan());
            item.setValue(p);
            cbPendidikan.appendChild(item);
        }

        cbJabatan.getItems().clear();
        for (Jabatan j : listJabatan) {
            Comboitem item = new Comboitem(j.getNamaJabatan());
            item.setValue(j);
            cbJabatan.appendChild(item);
        }

        cbAgama.getItems().clear();
        for (Agama a : listAgama) {
            Comboitem item = new Comboitem(a.getNamaAgama());
            item.setValue(a);
            cbAgama.appendChild(item);
        }
    }

    private void initKaryawan() {
        karyawan = new Karyawan();
        karyawan.setTglLahir(new Date());
        // Default tanggal lahir
    }
    public void onSelect$listKaryawan() {
        Listitem selected = listboxKaryawan.getSelectedItem();
        if (selected == null) return;

        karyawan = (Karyawan) selected.getValue(); // ambil data dari item terpilih

        session.setAttribute("editKaryawan", karyawan); // simpan ke session
        
//        Messagebox.show("Apakah yakin akan mengedit");

        // isi form
        txtNama.setValue(karyawan.getNama());
        txtNBM.setValue(karyawan.getNbm());
        txtAlamat.setValue(karyawan.getAlamat());
        rgJK.setSelectedIndex(karyawan.getJk() == 'L' ? 0 : 1);
        tglLahir.setValue(karyawan.getTglLahir());

        cbPendidikan.setSelectedIndex(getIndexById(cbPendidikan, karyawan.getPendidikan().getId()));
        cbJabatan.setSelectedIndex(getIndexById(cbJabatan, karyawan.getJabatan().getId()));
        cbAgama.setSelectedIndex(getIndexById(cbAgama, karyawan.getAgama().getId()));
    }
    public void onClick$btnRefresh() {
            String sql = "select * from karyawan";
            listKaryawan = em.createNativeQuery(sql, Karyawan.class)
                                .setHint("eclipselink.refresh", "true")
                                .getResultList();
    //        Messagebox.show("ini refresh");
            winUtama.invalidate();
    }
    
    public void onClick$btnSimpan() throws InterruptedException {
        try {
            if (txtNama.getValue() == null || txtNama.getValue().isEmpty()) {
                Messagebox.show("Nama harus diisi!", "Error", Messagebox.OK, Messagebox.ERROR);
                return;
            }

            if (rgJK.getSelectedItem() == null) {
                Messagebox.show("Pilih jenis kelamin!", "Error", Messagebox.OK, Messagebox.ERROR);
                return;
            }

            // Cek apakah sedang edit atau input baru
            Karyawan karyawanEdit = (Karyawan) session.getAttribute("editKaryawan");
            if (karyawanEdit == null) {
                karyawan = new Karyawan(); // input baru
            } else {
                karyawan = em.find(Karyawan.class, karyawanEdit.getId()); // update data lama
            }

            // Set field dari form
            karyawan.setNama(txtNama.getValue());
            karyawan.setNbm(txtNBM.getValue());
            karyawan.setAlamat(txtAlamat.getValue());
            karyawan.setJk(((String) rgJK.getSelectedItem().getValue()).charAt(0));
            karyawan.setTglLahir(tglLahir.getValue());

            if (cbPendidikan.getSelectedItem() != null) {
                Pendidikan p = em.find(Pendidikan.class, ((Pendidikan) cbPendidikan.getSelectedItem().getValue()).getId());
                karyawan.setPendidikan(p);
            } else {
                karyawan.setPendidikan(null);
            }

            if (cbJabatan.getSelectedItem() != null) {
                Jabatan j = em.find(Jabatan.class, ((Jabatan) cbJabatan.getSelectedItem().getValue()).getId());
                karyawan.setJabatan(j);
            } else {
                karyawan.setJabatan(null);
            }

            if (cbAgama.getSelectedItem() != null) {
                Agama a = em.find(Agama.class, ((Agama) cbAgama.getSelectedItem().getValue()).getId());
                karyawan.setAgama(a);
            } else {
                karyawan.setAgama(null);
            }

            // Simpan ke database
            tx = em.getTransaction();
            tx.begin();

            if (karyawanEdit == null) {
                em.persist(karyawan); // insert baru
            } else {
                em.merge(karyawan); // update
            }

            tx.commit();

            Messagebox.show("Data berhasil disimpan!", "Sukses", Messagebox.OK, Messagebox.INFORMATION);

            // Reset form dan data
            initKaryawan();
            clearForm();
            session.removeAttribute("editKaryawan");
            onClick$btnRefresh();

        } catch (WrongValueException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            Messagebox.show("Error: " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }
        onClick$btnRefresh();
    }

    

    public void onClick$btnTutup() {
        winUtama.detach();
        execution.sendRedirect("/zul/index.zul");
    }
    public void onClick$btnClear(){
        session.removeAttribute("editKaryawan");
        clearForm();
    }
    public void onClick$btnEdit() {
        Listitem selected = listboxKaryawan.getSelectedItem();
        if (selected == null) {
            Messagebox.show("Pilih salah satu data karyawan untuk diedit!", "Peringatan", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        // Load ulang combobox
        fillComboboxes();

        karyawan = (Karyawan) selected.getValue();
        session.setAttribute("editKaryawan", karyawan);

        // Isi form dengan data yang dipilih
        txtNama.setValue(karyawan.getNama());
        txtNBM.setValue(karyawan.getNbm());
        txtAlamat.setValue(karyawan.getAlamat());
        rgJK.setSelectedIndex(karyawan.getJk() == 'L' ? 0 : 1);
        tglLahir.setValue(karyawan.getTglLahir());

        cbPendidikan.setSelectedIndex(getIndexById(cbPendidikan, karyawan.getPendidikan().getId()));
        cbJabatan.setSelectedIndex(getIndexById(cbJabatan, karyawan.getJabatan().getId()));
        cbAgama.setSelectedIndex(getIndexById(cbAgama, karyawan.getAgama().getId()));
    }

    public void onClick$btnDelete() {
    Listitem selected = listboxKaryawan.getSelectedItem();
    if (selected == null) {
        Messagebox.show("Pilih data dulu!", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        return;
    }

    Messagebox.show("Yakin hapus data ini?", "Konfirmasi",
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
        event -> {
            if (Messagebox.ON_YES.equals(event.getName())) {
                try {
                    Karyawan k = (Karyawan) selected.getValue();

                    tx = em.getTransaction();
                    tx.begin();
                    Karyawan managed = em.merge(k); // Re-attach entity
                    em.remove(managed);
                    tx.commit();

                    // Refresh data
                    listboxKaryawan.getItems().clear();
                    List<Karyawan> all = em.createQuery("FROM Karyawan", Karyawan.class).getResultList();
                    for (Karyawan karyawan : all) {
                        Listitem item = new Listitem();
                        item.setLabel(karyawan.getNama());
                        item.setValue(karyawan);
                        item.setParent(listboxKaryawan);
                    }

                    Messagebox.show("Data terhapus!", "Success", Messagebox.OK, Messagebox.INFORMATION);
                } catch (Exception e) {
                    if (tx != null && tx.isActive()) tx.rollback();

                    Messagebox.show("Error: " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
                }
            }
        });
    onClick$btnRefresh();
}






    private void clearForm() {
        txtNama.setValue("");
        txtNBM.setValue("");
        txtAlamat.setValue("");
        rgJK.setSelectedItem(null);
        tglLahir.setValue(null);
        cbPendidikan.setSelectedItem(null);
        cbJabatan.setSelectedItem(null);
        cbAgama.setSelectedItem(null);
    }

    // Getter untuk data binding
    public Karyawan getKaryawan() {
        return karyawan;
    }

    public List<Pendidikan> getListPendidikan() {
        return listPendidikan;
    }

    public List<Jabatan> getListJabatan() {
        return listJabatan;
    }

    public List<Agama> getListAgama() {
        return listAgama;
    }

    public List<Karyawan> getListKaryawan() {
        return listKaryawan;
    }

    private int getIndexById(Combobox combo, Integer id) {
    int index = 0;
        for (Comboitem item : combo.getItems()) {
            Object value = item.getValue();
            if (value instanceof Pendidikan && ((Pendidikan) value).getId().equals(id)) {
                return index;
            } else if (value instanceof Jabatan && ((Jabatan) value).getId().equals(id)) {
                return index;
            } else if (value instanceof Agama && ((Agama) value).getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1; // tidak ditemukan
    }
}
