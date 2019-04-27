package za.co.thamserios.basilread.contracts;

/**
 * Created by robson on 2017/01/14.
 */

public class OperatorStatusCapture {

    public interface OnSaveStateClicked {
        void saveStateClicked();
        void closeFragment();
        void closeFragmentWithAction(int index);
        void hideFabButton();
    }

    public interface OnActivityClicked{
        void changeFragment(int index);
    }

    private static OperatorStatusCapture mInstance;
    private OnSaveStateClicked mSaveStateClicked;
    private OnActivityClicked mActivityClicked;

    private OperatorStatusCapture() {
    }

    public static OperatorStatusCapture getInstance() {
        if (mInstance == null) {
            mInstance = new OperatorStatusCapture();
        }
        return mInstance;
    }

    public void setmSaveStateClicked(OnSaveStateClicked listener) {
        mSaveStateClicked = listener;
    }

    public void saveButton() {
        if (mSaveStateClicked != null) {
            mSaveStateClicked.saveStateClicked();
        }
    }

    public void closeFragment(){
        if (mSaveStateClicked != null) {
            mSaveStateClicked.closeFragment();
        }
    }

    public void closeFragmentWithAction(int index){
        if (mSaveStateClicked != null) {
            mSaveStateClicked.closeFragmentWithAction(index);
        }
    }

    public void hideFabButton(){
        if (mSaveStateClicked != null) {
            mSaveStateClicked.hideFabButton();
        }
    }

    public void activityClicked(int index) {
        if (mActivityClicked != null) {
            mActivityClicked.changeFragment(index);
        }
    }

    public void setmActivityClicked(OnActivityClicked listener) {
        this.mActivityClicked = listener;
    }
}
