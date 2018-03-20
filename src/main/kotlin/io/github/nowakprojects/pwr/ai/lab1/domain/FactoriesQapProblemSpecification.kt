package io.github.nowakprojects.pwr.ai.lab1.domain

import io.github.nowakprojects.pwr.ai.lab1.infrastructure.IntMatrix

class FactoriesQapProblemSpecification(
        val factoriesAmount: Int,
        val flowMatrix: IntMatrix,
        val distanceMatrix: IntMatrix
) {
    constructor(factoriesCount: Int, flowMatrix: Array<Array<Int>>, distanceMatrix: Array<Array<Int>>)
            : this(factoriesCount, IntMatrix(flowMatrix), IntMatrix(distanceMatrix))

    fun getPossibleFactories() = (0..factoriesAmount).map { it }.toTypedArray()
}