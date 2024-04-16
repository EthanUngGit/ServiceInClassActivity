package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import java.util.Timer
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    var timerBinder: TimerService.TimerBinder? = null

    val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerBinder = service as TimerService.TimerBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            timerBinder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )

        //findViewById<Button>(R.id.startButton).setOnClickListener {
            //timerBinder?.start(100)
        //}

        //findViewById<Button>(R.id.pauseButton).setOnClickListener {
            //timerBinder?.pause()
        //}

        //findViewById<Button>(R.id.stopButton).setOnClickListener {
            //timerBinder?.stop()
        //}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_start -> {
                timerBinder?.start(100)
                Toast.makeText(this, "Starting counter...", Toast.LENGTH_SHORT).show()
            }

            R.id.action_pause -> {
                timerBinder?.pause()
                Toast.makeText(this, "Pausing counter...", Toast.LENGTH_SHORT).show()
            }

            R.id.action_stop -> {
                timerBinder?.stop()
                Toast.makeText(this, "Stopping counter...", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}