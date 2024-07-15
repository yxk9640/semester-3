import math

# doc = "Arlington Computer Science Department Science"
# query = "Computer Science Engineering Computer"
# df = {"arlington":50,"computer":800,"science":500,"engineering":200,"department":300}
# N=10000

doc = "car insurance auto insurance"
query = "best car insurance"
N = 1000000 #total number of documents
df = {"auto":5000,"best":50000,"car":10000,"insurance":1000}
docLower = doc.lower()
queryLower = query.lower()

doc_token = docLower.split(" ")
query_token = queryLower.split(" ")

# print(doc_token , query_token)
# gives the table we got at initial step of quiz
doc_dict = {item:doc_token.count(item) for item in doc_token }
query_dict = {item:query_token.count(item) for item in query_token }

# print(doc_dict)
# print(query_dict)

#custom round
def custRound(num,limit):
    return math.floor( num * 10 ** limit )/ 10 ** limit
#calculate log10(x)
def calcLog(num):
    return 0 if num == 0 else custRound(math.log10(num)%100,3)

#raw_tf
def raw_tf(token,position):
    if position == "document":
        return doc_dict.get(token) if token in doc_dict else -1
    if position == "query":
        return query_dict.get(token) if token in query_token else -1
#weight_tf
def weight_tf(token,position):
    return 0 if raw_tf(token,position)==-1 else 1 + calcLog(raw_tf(token,position))
# idf => log10(N/df)
def idf(token,position):
    df_token = df.get(token) if position != "document" else 1
    return calcLog(N/df_token)
# tf_idf
def tf_idf(token,position):
    result = idf(token,position)*weight_tf(token,position)
    return custRound(result,3)
# normalized
def normalise(token,token_norm_dict):
    #get tf_idf
    nume, deno = token_norm_dict.get(token), 0.0
    tempDeno = 0.0
    for item,value in token_norm_dict.items():
        tempDeno += value * value
    deno = custRound(math.sqrt(tempDeno),3)
    # print(nume, deno)
    result = nume/deno
    return custRound(result,3)

def freqCalculation(queryPos,check_list,norm):
    raw_tf_dict = {item:raw_tf(item,queryPos) for item in check_list}
    weight_tf_dict = {item:weight_tf(item,queryPos) for item in check_list}
    idf_dict = {item:idf(item,queryPos) for item in check_list}
    tf_idf_dict = {item:tf_idf(item,queryPos) for item in check_list}
    normalise_dict = {item: normalise(item,tf_idf_dict) for item,value in tf_idf_dict.items()}
    # print(normalise("computer",tf_idf_dict))
    result = normalise_dict if norm != "n" else tf_idf_dict
    return result
doc_final_dict = freqCalculation("document",doc_token,"c")
query_final_dict = freqCalculation("query",query_token,"n")

cosine = 0
for key,val in query_final_dict.items():
    cosine += val*doc_final_dict.get(key) if doc_final_dict.get(key) else 0
print("Query: ",query_final_dict)
print("Document: ",doc_final_dict)
print(round(cosine,2))

#calculations for doc
#raw_tf
#weight_tf
#idf
#tf_idf
#normalize

#similarity
