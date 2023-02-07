package com.example.bluetoothapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var mStatusBlueTv: TextView? = null
    var mPairedTv: TextView? = null
    var mBlueIv: ImageView? = null
    var mOnBtn: Button? = null
    var mOffbtn: Button? = null
    var mDiscoverBtn: Button? = null
    var mPairedBtn: Button? = null
    var mBlueAdapter: BluetoothAdapter? = null
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv)
        mPairedBtn = findViewById(R.id.pairedBtn)
        mBlueIv = findViewById(R.id.bluetoothIv)
        mOnBtn = findViewById(R.id.onBtn)
        mOffbtn = findViewById(R.id.offBtn)
        mDiscoverBtn = findViewById(R.id.discoverableBtn)
        mPairedTv = findViewById(R.id.pairedTv)
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter()

        if (mBlueAdapter == null) {
            mStatusBlueTv.setText("Bloutooth is Not available/Tidak tersedia")
        } else {
            mStatusBlueTv.setText("Bloutooth is available/Tersedia")
        }

        if (mBlueAdapter.isEnabled()) {
            mBlueIv.setImageResource(R.drawable.ic_action_on)
        } else {
            mBlueIv.setImageResource(R.drawable.ic_action_off)
        }
        mOnBtn.setOnClickListener(View.OnClickListener {
            if (!mBlueAdapter.isEnabled()) {
                showToast("Turning ON Bluetooth...")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, com.example.bluethoth.MainActivity.Companion.REQUEST_ENABLE_BT)
            } else {
                showToast("Bluetooth is already ON")
            }
        })
        mDiscoverBtn.setOnClickListener(View.OnClickListener {
            if (!mBlueAdapter.isDiscovering()) {
                showToast("Make your Device Discoverabble")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, com.example.bluethoth.MainActivity.Companion.REQUEST_DISCOVER_BT)
            }
        })
        mOffbtn.setOnClickListener(View.OnClickListener {
            if (mBlueAdapter.isEnabled()) {
                mBlueAdapter.disable()
                showToast("Turning Bluetooth Off")
                mBlueIv.setImageResource(R.drawable.ic_action_off)
            } else {
                showToast("Bluetooth is already Off")
            }
        })
        mPairedBtn.setOnClickListener(View.OnClickListener {
            if (mBlueAdapter.isEnabled()) {
                mPairedTv.setText("Paired Devices")
                val devices = mBlueAdapter.getBondedDevices()
                for (device: BluetoothDevice in devices) {
                    mPairedTv.append(
                        "\nDevice: &quot; + device.getName()" + ","
                                + device
                    )
                }
            } else {
                showToast("Turn On Bluetooth to get paired devices")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            com.example.bluethoth.MainActivity.Companion.REQUEST_ENABLE_BT -> if (resultCode == RESULT_OK) {

                mBlueIv!!.setImageResource(R.drawable.ic_action_on)
                showToast("Bluetooth is ON")
            } else {
                showToast("Bluetooth tidak dapat di Aktifkan")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 0
        private const val REQUEST_DISCOVER_BT = 1
    }
}