package logic

class BMIForKgCm(private val mass: Int, private val height: Int) : Bmi {

    override fun calculateBmi(): Double {
        return (mass*10000.0 / (height*height))
    }
}