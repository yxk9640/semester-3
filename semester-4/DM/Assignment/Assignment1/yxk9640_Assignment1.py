import math
import os
import time
from collections import Counter
import nltk
from nltk.corpus import stopwords
from nltk import RegexpTokenizer
from nltk.stem.porter import PorterStemmer

start = time.time()
def calc_log(num):
    return 0 if num == 0 else math.log10(num)

#stopWords
nltk.download() # downloaded once and store
listStopwords = stopwords.words('english')

#Tasks
#Read
N = 15 #total documents in corpus
corpusroot = './US_Inaugural_Addresses'
token_file_dict = {} #dictionary of files with count of terms.
allDocToken = set() #list with all the terms in all documents
df={} # dictionary with all terms of df
dftokenList = [] # list with all terms for df

tokenizer = RegexpTokenizer(r'[a-zA-Z]+')

for filename in os.listdir(corpusroot): #can get the filename
    if filename.startswith('0') or filename.startswith('1'):
        file = open(os.path.join(corpusroot, filename), "r", encoding='windows-1252')
        doc = file.read()
        file.close()
        doc = doc.lower() #all words as single string
        tokens = tokenizer.tokenize(doc.lower())
        # Eliminate stopwords
        stopwordsToken = [token for token in tokens if token not in listStopwords]
        # Stemming
        stemmedTokens = [PorterStemmer().stem(token) for token in stopwordsToken]
        # Add unique tokens to allDocToken take set --> decreased time
        allDocToken.update(stemmedTokens)
        # Add stemmedTokens to dftokenList
        dftokenList.append(stemmedTokens)
        # Add counts to token_file_dict
        token_file_dict[filename] = Counter(stemmedTokens)
# print(dftokenList,'tokenlist')
df = {item: sum(item in doc for doc in dftokenList) for item in allDocToken}



def normalise(token_norm_dict):
    # Get tf_idf
    deno = math.sqrt(sum(val * val for val in token_norm_dict.values()))
    update = {key: val / deno if val != 0 else 0 for key, val in token_norm_dict.items()}
    return update

#getidf
def getidf(token):
    if token not in allDocToken:
        token_stemmed = PorterStemmer().stem(token)
    else:
        token_stemmed = token
    if token_stemmed in df:
        return calc_log(N / df[token_stemmed])
    else:
        return -1


def getweight(filename, token):
    if token not in allDocToken:
        token_stemmed = PorterStemmer().stem(token)
    else:
        token_stemmed = token
    if token_stemmed in token_file_dict[filename]:
        raw_tf = token_file_dict[filename][token_stemmed] if token in token_file_dict[filename] else 0
        tf = 1 + calc_log(raw_tf)
        idf = getidf(token_stemmed) #not affected
        return tf * idf
    return 0


def idf_val(key,str):
    return 1 if str=="n" else getidf(key)


queryStart = time.time()
def query(qstring):
    # [file, score] <- lnc.ltc
    ddd, qqq = "lnc","ltc"
    #query
#tokenize
    query_token = qstring.split(" ")  # list of query
#eliminate stopword
    query_stopword = [token for token in query_token if token not in listStopwords]
#stemming
    query_stemmed = [PorterStemmer().stem(token) for token in query_stopword]
    query_term_freq = Counter(query_stemmed) #raw_tf ---> change query_token to stemmed_query
    query_weight = {item: 1 + calc_log(val) for item,val in query_term_freq.items()}
#IDF
    query_idf = {key: getidf(key) for key,val in query_weight.items() if key in query_term_freq}#get df from 15 doc
#TF*IDF
    query_tf_idf = {key: query_idf[key]*query_weight[key] for key,val in query_weight.items()}
#NORMALISE
    query_norm = normalise(query_tf_idf)

#doc - check for all doc if query term exists do sim check store in list and then return highest
    file_Doc={}
    doc_token = {}
    for file_key,val in token_file_dict.items():
        for item in query_stemmed:
            if item in val:
                doc_token[item] = val.get(item)
            else:
                doc_token[item] = 0
# weigth_tf
        doc_weight = {item: (1 + calc_log(val) if val != 0 else int(0)) for item,val in doc_token.items()} #tf
# IDF and  TF*IDF
        doc_tf_idf = {key: idf_val(key,ddd[1])*doc_weight[key] for key,val in doc_weight.items() if key in doc_weight}
# NORMALISE
        doc_norm = normalise(doc_tf_idf)

        common_keys = set(query_norm.keys()) & set(doc_norm.keys())
        cosineSim = sum(query_norm[k] * doc_norm[k] for k in common_keys)
        file_Doc[file_key] = cosineSim

    MaxSimilarity = max(file_Doc, key=file_Doc.get)

    if all(val==0 for val in file_Doc.values()) == True:
        return (None,0)
    else:
        return ( MaxSimilarity, file_Doc.get(MaxSimilarity))


print("%.12f" % getidf('british'))
print("%.12f" % getidf('union'))
print("%.12f" % getidf('war'))
print("%.12f" % getidf('power'))
print("%.12f" % getidf('great'))
print("--------------")
print("%.12f" % getweight('02_washington_1793.txt','arrive'))
print("%.12f" % getweight('07_madison_1813.txt','war'))
print("%.12f" % getweight('12_jackson_1833.txt','union'))
print("%.12f" % getweight('09_monroe_1821.txt','great'))
print("%.12f" % getweight('05_jefferson_1805.txt','public'))
print("--------------")
print("(%s, %.12f)" % query("pleasing people"))
print("(%s, %.12f)" % query("british war"))
print("(%s, %.12f)" % query("false public"))
print("(%s, %.12f)" % query("people institutions"))
print("(%s, %.12f)" % query("violated willingly"))
print(f"------- {time.time() - start} seconds -------")