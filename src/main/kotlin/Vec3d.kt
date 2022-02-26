data class Vec3d(val x: Float, val y: Float, val z: Float) {

    constructor(value: Float) : this(value, value, value)
    constructor() : this(0f)

    fun multiply(vec: Vec3d): Vec3d {
        return multiply(this, vec)
    }

    fun add(vec: Vec3d): Vec3d {
        return add(this, vec)
    }

    companion object {

        fun multiply(vec1: Vec3d, vec2: Vec3d): Vec3d {
            return Vec3d(vec1.x * vec2.x, vec1.y * vec2.y, vec1.z * vec2.z)
        }

        fun add(vec1: Vec3d, vec2: Vec3d): Vec3d {
            return Vec3d(vec1.x + vec2.x, vec1.y + vec2.y, vec1.z + vec2.z)
        }

    }

}