package cn.com.nggirl.ngdemo.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.com.nggirl.ngdemo.R;


public class MessengerActivity extends AppCompatActivity {
    public static final String TAG = MessengerActivity.class.getSimpleName();

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected " + name.getClassName());
            Messenger service1 = new Messenger(service);
            Message msg = Message.obtain(null, 1);

            Bundle data = new Bundle();
            data.putString("msg", "hello, this is client");
            msg.setData(data);

            try {
                service1.send(msg);
            } catch (RemoteException e) {
                Log.d(TAG, "error " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static void start(Context context) {
        context.startActivity(new Intent(context, MessengerActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
