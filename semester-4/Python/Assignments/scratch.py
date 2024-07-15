# myfamily = {
#   "child1" : {
#     "name" : "Emil",
#     "year" : 2004
#   },
#   "child2" : {
#     "name" : "Tobias",
#     "year" : 2007
#   },
#   "child3" : {
#     "name" : "Linus",
#     "year" : 2011
#   }
# }

# print(myfamily.get("child1").values)

# Write Your Answer for Q#1 Over Here 
def removeSpaces(string):
    str = ""
    lst = string.split(" ")
    for i in range(0,len(lst)):
        str += lst[i] 
    return str
#open file and read each line as an item of list 
file = open("string.txt",'r')
anagrams = ''
#convert to lower case letters as given Uppercase and lower case are considered as same
lines = file.read().lower().splitlines()
print(lines)

#generate anagrams from the word list
# -- 1. Length 2 strings should be equal, do not consider spaces
# for i in range(0,len(lines)):
#   for j in range(i+1,len(lines)):
#     print(lines[i], lines[j] )
#     if ( set(lines[i]) == set(lines[j]) ):
#       anagrams+="("+lines[i]+","+lines[j]+")\n" #can you avoid printing last empty line
#       # anagrams.append(tuple( (lines[i],lines[j]) ))

# print(anagrams)

print(removeSpaces(lines[12]))

print(set(removeSpaces(lines[12])), " 12" )
print(set(lines[6]))

#print the output to a file.
# out = open("anagrams.txt",'w')
# out.write(anagrams)

#close file
# out.close()
file.close()