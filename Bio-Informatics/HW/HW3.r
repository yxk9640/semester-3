install.packages("htmltools")
library(htmltools)
source("https://bioconductor.org/biocLite.R")
#---line:3 is not working so tried line:5,6----
BiocManager::install( )
BiocManager::valid()
library("DESeq2")
library(utils)
biocLite("DESeq2")
