package io.github.nowakprojects.pwr.ai.lab1.domain

import io.github.nowakprojects.pwr.ai.lab1.extensions.random
import io.github.nowakprojects.pwr.ai.lab1.geneticalgorithm.Chromosome
import io.github.nowakprojects.pwr.ai.lab1.geneticalgorithm.CrossoverStrategy
import io.github.nowakprojects.pwr.ai.lab1.geneticalgorithm.Population
import java.util.*

class FactoriesQapCrossover(
        private val possibleGenes: Array<Int>,
        crossoverProbability: Double
) : CrossoverStrategy<Int>(crossoverProbability) {

    override fun crossoverPopulation(population: Population<Int>): Population<Int> {
        val crossoverChromosomes = mutableListOf<Chromosome<Int>>()
        val chromosomesToCrossover = 2
        (0..population.size)
                .step(chromosomesToCrossover)
                .forEach { it ->
                    if (toCross()) {
                        crossoverChromosomes.addAll(crossover(population.get(it), population.get(it + 1)).toList())
                    } else {
                        crossoverChromosomes.addAll(listOf(population.get(it), population.get(it + 1)))
                    }
                }
        return Population(crossoverChromosomes)
    }

    fun toCross() = Random().nextDouble() > crossoverProbability

    fun crossover(parent1: Chromosome<Int>, parent2: Chromosome<Int>): Pair<Chromosome<Int>, Chromosome<Int>> {
        val crossPoint = (parent1.size / 2)
        val child1 = Chromosome<Int>(listOf(parent1.genes.take(crossPoint), parent2.genes.drop(crossPoint)).flatten())
        val child2 = Chromosome<Int>(listOf(parent2.genes.take(crossPoint), parent1.genes.drop(crossPoint)).flatten())
        return Pair(replaceDuplicatesInChromosome(child1), replaceDuplicatesInChromosome(child2))
    }

    private fun replaceDuplicatesInChromosome(chromosome: Chromosome<Int>): Chromosome<Int> {
        val newGenesList = chromosome.genes.toMutableList()
        var duplicateIndex = findFirstDuplicateIndex(chromosome.genes)
        while (duplicateIndex != null) {
            val newGene = possibleGenes.filter { !newGenesList.contains(it) }.random()
            newGenesList[duplicateIndex] = newGene!!
            duplicateIndex = findFirstDuplicateIndex(newGenesList)
        }
        return Chromosome<Int>(newGenesList)
    }

    private fun findFirstDuplicateIndex(genes: List<Int>): Int? {
        (0..genes.size).forEach {
            val gene = genes[it]
            if (genes.filterIndexed { index, _ -> index != gene }.contains(gene)) {
                return it
            }
        }
        return null
    }

}