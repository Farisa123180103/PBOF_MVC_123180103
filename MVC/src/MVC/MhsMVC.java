package MVC;

//perintah utk menjalankan MVC, dlm hal ini MVC ada banyak macam cara.. ini salah satunya
public class MhsMVC {
    MhsView mhsview = new MhsView();
    MhsModel mhsmodel = new MhsModel();
    MhsDAO mhsDAO = new MhsDAO();
    MhsController mhscontroller = new MhsController(mhsmodel, mhsview, mhsDAO);
}