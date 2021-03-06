package MVC;

import java.awt.event.*;
import javax.swing.*;

public class MhsController {
    MhsModel mhsmodel;//utk import class Model
    MhsView mhsview; //utk import class View
    MhsDAO mhsdao; //utk import class DAO
    static String dataTerpilih;
    
    public MhsController(MhsModel mhsmodel, MhsView mhsview, MhsDAO mhsdao){
        this.mhsmodel = mhsmodel;
        this.mhsview = mhsview;
        this.mhsdao = mhsdao;
        
        if(mhsdao.getJmldata() != 0){ //utk mengecek apakah DB ada data atau tidak
            String dataMahasiswa[][] = mhsdao.readMahasiswa();
            mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
        } else { 
            // kalau tdk ada mka akan muncul pop up
            JOptionPane.showMessageDialog(null, "Data tidak ada!");
        }
        
        mhsview.simpan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String nim = mhsview.getNim();
                String nama = mhsview.getNama();
                String alamat = mhsview.getAlamat();
                
                if(nim.isEmpty() || nama.isEmpty() || alamat.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Harap isi semua field");
                } else {
                    //memasukkan data kedlm Model
                    mhsmodel.setMhsModel(nim, nama, alamat);
                    //menjalankan perintah insert di DAO
                    mhsdao.Insert(mhsmodel);
                    
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable (dataMahasiswa, mhsview.namaKolom)).getModel());
                }
            }
        });
        
        mhsview.tabel.addMouseListener(new MouseAdapter(){
           @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = mhsview.tabel.getSelectedRow();
                int kolom = mhsview.tabel.getSelectedColumn();

                dataTerpilih = mhsview.tabel.getValueAt(baris,0).toString();
                mhsview.delete.setEnabled(true);
            }
        });
        
        mhsview.delete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                int input = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus Data dengan Nim " + dataTerpilih + "?","Delete Contact",JOptionPane.YES_NO_OPTION);

                if (input == 0){
                    mhsmodel.setNim(dataTerpilih);
                    mhsdao.Delete(mhsmodel);
//                    mhsdao.Delete(dataTerpilih);
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable (dataMahasiswa, mhsview.namaKolom)).getModel());
                }
            }
        });
    }
}