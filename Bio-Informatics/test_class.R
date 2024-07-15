library(ggplot2)

gene_length <- read.delim("/Users/yogesh/Desktop/gene1_length.txt")
df <- data.frame(gene_length)
p <- ggplot(data=df, aes(x=length_bp)) + 
#ggtitle("Megavirus Gene Lengths")
#xlab("Gene Length")
#ylab("Count")
#cowplot
#ggplot(data=df, aes(x=dose, y=len, group=1)) +geom_line()+geom_point()
