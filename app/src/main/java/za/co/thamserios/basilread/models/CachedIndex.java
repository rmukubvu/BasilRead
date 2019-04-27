package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/03/10.
 */

public class CachedIndex {

    private String rigTopText;
    private String shiftTopText;
    private String operatorName;
    private String operatorAssistant;
    private int selectedUser;
    private int selectedAssistant;
    private int selectedForeman;
    private int selectedSupervisor;
    private String selectedCrew;
    private int selectedRig;
    private int selectedShift;


    public String getRigTopText() {
        return rigTopText;
    }

    public void setRigTopText(String rigTopText) {
        this.rigTopText = rigTopText;
    }

    public String getShiftTopText() {
        return shiftTopText;
    }

    public void setShiftTopText(String shiftTopText) {
        this.shiftTopText = shiftTopText;
    }

    public int getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(int selectedUser) {
        this.selectedUser = selectedUser;
    }

    public int getSelectedAssistant() {
        return selectedAssistant;
    }

    public void setSelectedAssistant(int selectedAssistant) {
        this.selectedAssistant = selectedAssistant;
    }

    public int getSelectedForeman() {
        return selectedForeman;
    }

    public void setSelectedForeman(int selectedForeman) {
        this.selectedForeman = selectedForeman;
    }

    public int getSelectedSupervisor() {
        return selectedSupervisor;
    }

    public void setSelectedSupervisor(int selectedSupervisor) {
        this.selectedSupervisor = selectedSupervisor;
    }

    public String getSelectedCrew() {
        return selectedCrew;
    }

    public void setSelectedCrew(String selectedCrew) {
        this.selectedCrew = selectedCrew;
    }

    public int getSelectedRig() {
        return selectedRig;
    }

    public void setSelectedRig(int selectedRig) {
        this.selectedRig = selectedRig;
    }

    public int getSelectedShift() {
        return selectedShift;
    }

    public void setSelectedShift(int selectedShift) {
        this.selectedShift = selectedShift;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorAssistant() {
        return operatorAssistant;
    }

    public void setOperatorAssistant(String operatorAssistant) {
        this.operatorAssistant = operatorAssistant;
    }
}
