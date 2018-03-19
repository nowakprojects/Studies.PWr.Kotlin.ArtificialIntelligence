package io.github.nowakprojects.pwr.ai.lab1.geneticalgorithm

abstract class SelectionStrategy<GENE>(
        private val selectionGoal: SelectionGoal,
        protected val elitism: Boolean = true
) {
    fun selectNewPopulation(chromosomeWithFitnessList: List<ChromosomeWithFitness<GENE>>): Population<GENE> {
        return PopulationSelector(chromosomeWithFitnessList).makeSelection()
    }

    protected abstract fun selectChromosomeForNewPopulation(chromosomeWithFitnessList: List<ChromosomeWithFitness<GENE>>): Chromosome<GENE>

    //FIXME: Write it better, pass FitnessComparator, which takes two chromosomes
    fun findBestChromosomeOf(chromosomeWithFitnessList: List<ChromosomeWithFitness<GENE>>): Chromosome<GENE> = when (selectionGoal) {
        SelectionGoal.MINIMIZE_FITNESS -> chromosomeWithFitnessList.minBy { it.fitness }!!.chromosome
        SelectionGoal.MAXIMIZE_FITNESS -> chromosomeWithFitnessList.maxBy { it.fitness }!!.chromosome
    }


    private inner class PopulationSelector(
            private val chromosomeWithFitnessList: List<ChromosomeWithFitness<GENE>>
    ) {
        fun makeSelection(): Population<GENE> {
            val newPopulationChromosomes: MutableList<Chromosome<GENE>> = mutableListOf()
            var newPopulationSize = chromosomeWithFitnessList.size

            if (elitism) {
                newPopulationChromosomes.add(findBestChromosome())
                newPopulationSize -= 1
            }

            (0..newPopulationSize)
                    .forEach { newPopulationChromosomes.add(selectChromosomeForNewPopulation(chromosomeWithFitnessList)) }
            return Population(newPopulationChromosomes)
        }

        private fun findBestChromosome() = findBestChromosomeOf(chromosomeWithFitnessList)
    }

    enum class SelectionGoal {
        MAXIMIZE_FITNESS,
        MINIMIZE_FITNESS;
    }
}