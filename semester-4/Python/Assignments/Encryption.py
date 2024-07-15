# Write Your Answer for part#1 Over Here 

#Use a magic function to write encryption() to a file and import it

def encryption(str):
    enc_dict = {'a': 'x', 'b': 'u', 'c': 'z', 'd': 'b', 'e': 'c', 'f': 'd', 'g': 'e', 'h': 'p', 'i': 'q', 'j': 'r', 'k': 'w', 'l': 'y', 'm': 'f', 'n': 'g', 'o': 'h', 'p': 'i', 'q': 'm', 'r': 'n', 's': 'o', 't': 'j', 'u': 'k', 'v': 'l', 'w': 's', 'x': 't', 'y': 'v', 'z': 'a'}
    strLower = str.lower()
    return enc_dict[strLower]


