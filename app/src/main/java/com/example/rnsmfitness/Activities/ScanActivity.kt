package com.example.rnsmfitness.Activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.rnsmfitness.Activities.HomeActivity.Companion.RESULT
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    companion object{
        const val CAMERARESULT = 123
    }

    var scannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)

        setPermission()
    }

    override fun handleResult(p0: Result?) {
        /*val intent = Intent(applicationContext, HomeActivity::class.java)
        intent.putExtra(RESULT, p0.toString())
        startActivity(intent)*/
        val intent = Intent()
        intent.putExtra(RESULT, p0.toString())
        setResult(CAMERARESULT, intent)
        finish()
    }

    override fun onResume(){
        super.onResume()

        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun onStop(){
        super.onStop()

        scannerView?.stopCamera()
        onBackPressed()
    }

    private fun setPermission(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1)
    }

    private fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray

    ){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults)
        when(requestCode){
            1->{
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(applicationContext, "You need camera permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}