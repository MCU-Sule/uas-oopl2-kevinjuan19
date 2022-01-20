package Model;

import DAO.memberDao;
import DAO.pointDao;
import DAO.transaksiDao;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainController {

    @FXML
    private Label totalMember;

    @FXML
    private Label totalTransaksi;

    @FXML
    private Label totalPoin;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUsername;

    @FXML
    private DatePicker dateBrith;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<FeMemberEntity> tableCitizen;

    @FXML
    private TableColumn<?, ?> colCitizenId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colBirth;

    @FXML
    private TextField nominal;

    @FXML
    private DatePicker dateTransaksi;

    @FXML
    private Button btnSaveTransaksi;

    @FXML
    private TableView<FeTransactionEntity> tableTransaksi;

    @FXML
    private TableColumn<?, ?> colTransaksi;

    @FXML
    private TableColumn<?, ?> colNominal;

    @FXML
    private TableView<FePointEntity> tablePoint;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPoint;


    private final memberDao mDao = new memberDao();
    private final pointDao pDao = new pointDao();
    private final transaksiDao tDao = new transaksiDao();
    private ObservableList<FeMemberEntity> feMemberEntities = mDao.showData();
    private ObservableList<FePointEntity> fePointEntities = pDao.showData();
    private ObservableList<FeTransactionEntity> feTransactionEntities = tDao.showData();
    private FeMemberEntity selected;


    @FXML
    public void initialize(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                tableCitizen.setItems(feMemberEntities);
                colCitizenId.setCellValueFactory(new PropertyValueFactory<>("citizenId"));
                colName.setCellValueFactory(new PropertyValueFactory<>("name"));
                colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                colBirth.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

                tablePoint.setItems(fePointEntities);
                colId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colPoint.setCellValueFactory(new PropertyValueFactory<>("value"));



                tableCitizen.setOnMouseClicked(e->tableUpdate());
                return null;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();

    }


    private void tableUpdate(){
        selected = tableCitizen.getSelectionModel().getSelectedItem();
        if (selected != null){
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            txtId.setText(String.valueOf(selected.getCitizenId()));
            txtId.setDisable(true);

            txtName.setText(selected.getName());
            txtPhone.setText(selected.getPhone());
            txtAddress.setText(selected.getAddress());
            txtEmail.setText(selected.getEmail());
            txtUsername.setText(selected.getUsername());
            dateBrith.setValue(LocalDate.parse(String.valueOf(selected.getBirthdate())));
            btnUpdate.setDisable(false);
            tableTransaksi.setItems(feTransactionEntities);
            colTransaksi.setCellValueFactory(new PropertyValueFactory<>("transDate"));
            colNominal.setCellValueFactory(new PropertyValueFactory<>("nominal"));
        }

    }


    @FXML
    void resetAction(ActionEvent event) {
        clearAll();
    }

    @FXML
    void saveMemberAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (txtId.getText().isEmpty()){
                    showAlert("Please fill in field!");
                }else if(txtName.getText().isEmpty()){
                    showAlert("Please fill in field!");
                }else if (txtAddress.getText().isEmpty()){
                    showAlert("Please fill in field!");
                }else if (txtPhone.getText().isEmpty()){
                    showAlert("Please fill in field!");
                }else if (txtPhone.getText().isEmpty()){
                    showAlert("Please fill in field!");
                }else if (txtEmail.getText().isEmpty()){
                    showAlert("Please fill in field!");
                }else{
                    FeMemberEntity member = new FeMemberEntity();
                    member.setCitizenId(txtId.getText());
                    member.setName(txtName.getText());
                    member.setAddress(txtAddress.getText());
                    member.setPhone(txtPhone.getText());
                    member.setEmail(txtEmail.getText());
                    member.setUsername(txtUsername.getText());
                    member.setBirthdate(Date.valueOf(dateBrith.getValue()));
                    mDao.addData(member);
                    feMemberEntities.clear();
                    feMemberEntities.addAll(mDao.showData());
                    tableCitizen.refresh();
                }
                clearAll();
                return null;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();

    }

    public void clearAll(){
        txtId.clear();
        txtId.setDisable(false);
        txtName.clear();
        txtAddress.clear();
        txtPhone.clear();
        txtEmail.clear();
        nominal.clear();
        dateTransaksi.setValue(null);
        txtUsername.clear();
        dateBrith.setValue(null);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
    }



    @FXML
    void saveTransAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (nominal.getText().isEmpty()){
                    showAlert("Please fill in field!");
                }else {
                    try{
                        selected = tableCitizen.getSelectionModel().getSelectedItem();
                        FeTransactionEntity trans = new FeTransactionEntity();
                        trans.setNominal(Long.parseLong(nominal.getText()));
                        trans.setTransDate(Date.valueOf(dateBrith.getValue()));
                        trans.setFeMemberByMemberCitizenId(selected);
                        tDao.addData(trans);
                        feTransactionEntities.clear();
                        feTransactionEntities.addAll(tDao.showData());
                        tableTransaksi.refresh();
                    }catch (NumberFormatException e){
                        System.out.println(e.getMessage());
                    }
                }
                return null;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();

    }
    public void showAlert(String kalimat){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.show();
        alert.setContentText(kalimat);
    }


    @FXML
    void updateAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if(selected!=null){
                    if (txtId.getText().isEmpty()){
                        showAlert("Please fill in field!");
                    }else if(txtName.getText().isEmpty()){
                        showAlert("Please fill in field!");
                    }else if (txtAddress.getText().isEmpty()){
                        showAlert("Please fill in field!");
                    }else if (txtPhone.getText().isEmpty()){
                        showAlert("Please fill in field!");
                    }else if (txtPhone.getText().isEmpty()){
                        showAlert("Please fill in field!");
                    }else if (txtEmail.getText().isEmpty()){
                        showAlert("Please fill in field!");
                    }else {
                        selected.setCitizenId(txtId.getText());
                        selected.setName(txtName.getText());
                        selected.setAddress(txtAddress.getText());
                        selected.setPhone(txtPhone.getText());
                        selected.setEmail(txtEmail.getText());
                        selected.setUsername(txtUsername.getText());
                        selected.setBirthdate(Date.valueOf(dateBrith.getValue()));
                        mDao.updateData(selected);
                        feMemberEntities.clear();
                        feMemberEntities.addAll(mDao.showData());
                        tableCitizen.refresh();
                    }
                    clearAll();
                }
                return null;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();

    }

}
