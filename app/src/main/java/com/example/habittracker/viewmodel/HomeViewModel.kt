package com.example.habittracker.viewmodel


import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class HomeViewModel(application: Application) : AndroidViewModel(application) {



    private var stepSensor: Sensor? = null
    private var lastStepCount: Int = 0
    private var lastMovementTime: Long = 0
    private val walkingThreshold = 1
    private val cyclingThreshold = 10 * 1000
    private val stepsPerMeter = 5
    private val metersPerStep = 1
    val maxStepCount = 100
    val maxCycling = 10
    private val _currentWalkingSteps = MutableLiveData<Int>()
    val currentWalkingSteps: LiveData<Int> = _currentWalkingSteps
    private val _currentCyclingDistance = MutableLiveData<Double>()
    val currentCyclingDistance: LiveData<Double> = _currentCyclingDistance


    private val sensorManager: SensorManager =
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager


    private fun incrementCyclingDistance() {
        currentCyclingDistance
        val currentValue = currentCyclingDistance.value ?: 0.0
        _currentCyclingDistance.postValue(currentValue + metersPerStep)
    }

    private fun incrementWalkingSteps() {
        _currentWalkingSteps.postValue(
            _currentWalkingSteps.value?.plus(stepsPerMeter) ?: stepsPerMeter
        )
    }


    val sensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                    handleStepCounterEvent(it)
                }
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }


    }

    fun handleStepCounterEvent(event: SensorEvent) {
        val steps = event.values[0].toInt()

        if (steps > lastStepCount) {
            val stepDifference = steps - lastStepCount

            if (stepDifference >= walkingThreshold) {
                incrementWalkingSteps()
            }


            val currentTime = System.currentTimeMillis()
            val timeDifference = currentTime - lastMovementTime


            if (timeDifference >= cyclingThreshold) {
                incrementCyclingDistance()
                lastMovementTime = currentTime
            }


            lastStepCount = steps
        }
    }



    fun registerSensor(sensorType: Int) {
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor != null) {
            sensorManager.registerListener(
                sensorEventListener,
                stepSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Toast.makeText(
                getApplication<Application>(),
                "Step counter sensor not found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun unregisterSensor(sensorType: Int) {
        sensorManager.unregisterListener(sensorEventListener)
    }

//    private fun calculateAcceleration(x: Float, y: Float, z: Float): Double {
//        return Math.sqrt(x.toDouble().pow(0.8) + y.toDouble().pow(0.8) + z.toDouble().pow(0.8))
//    }


}

//        private var stepSensor: Sensor? = null
//        private var lastMovementTime: Long = 0
//        private val walkingThreshold = 1
//        private val cyclingThreshold = 10 * 1000
//        private val stepsPerMeter = 1
//        private val metersPerStep = 1
//        val maxStepCount = 100
//        val maxCycling = 10
//        private var lastAccelerationMagnitude: Double = 0.0
//        private val accelerationThreshold = 0.5
//        private val _currentWalkingSteps = MutableLiveData<Int>()
//        val currentWalkingSteps: LiveData<Int> = _currentWalkingSteps
//        private val _currentCyclingDistance = MutableLiveData<Double>()
//        val currentCyclingDistance: LiveData<Double> = _currentCyclingDistance



//    fun handleAccelerometerEvent(event: SensorEvent) {
//        val acceleration =
//            calculateAcceleration(event.values[0], event.values[1], event.values[2])
//
//        if (Math.abs(acceleration - lastAccelerationMagnitude) > accelerationThreshold) {
//
//            lastAccelerationMagnitude = acceleration
//
//            if (acceleration >= walkingThreshold) {
//                incrementWalkingSteps()
//            }
//            val currentTime = System.currentTimeMillis()
//
//            if (currentTime - lastMovementTime >= cyclingThreshold) {
//
//                incrementCyclingDistance()
//
//                lastMovementTime = currentTime
//            }
//        }
//    }