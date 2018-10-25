package dj.example.main.presenter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CSC on 10/26/2018.
 */

public interface GenericPresenterInterface {

    void setErrMsgWithOk(String msg, String btnTxt);

    void setErrMsg(String msg);

    void setErrMsgIndefinite(String msg);

    void setErrMsg(String msg, boolean lengthLong);

    void setErrMsg(Object json);

    void setWarningMsg(String msg);

    void setInfoMsg(String msg);

    void setInfoMsg(String msg, int length);

    void setInfoMsgIndefinite(String msg);

    void showDialogInfo(String msg, boolean isPositive);

    void onSuccessAPICall(Object object);

}
