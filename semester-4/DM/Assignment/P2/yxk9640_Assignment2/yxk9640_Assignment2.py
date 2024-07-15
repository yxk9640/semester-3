import pandas as pd
import random
from sklearn.metrics import accuracy_score
from sklearn.calibration import LinearSVC
from sklearn.discriminant_analysis import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.preprocessing import OneHotEncoder
from sklearn.feature_selection import SelectKBest, VarianceThreshold, chi2, f_classif
from sklearn.naive_bayes import GaussianNB, MultinomialNB
from sklearn.model_selection import StratifiedKFold, cross_val_score, train_test_split
from sklearn.tree import DecisionTreeClassifier

# Load example data with multi-class target variable
df = pd.read_csv('nba2021.csv')


# Extract target variable and features
target = df['Pos']
features = df.drop(['Pos','Player','Tm'], axis=1)


#Apply a model for Features selection 
featSelect = RandomForestClassifier(n_estimators=500 )                                                   
featSelect.fit(features, target)

# Alternate feature selection algorithm
# features_selected = SelectKBest(chi2, k=10).fit_transform(features
# X_train, X_test, y_train, y_test = train_test_split(selectedFeatures, target, test_size=0.2, random_state=42)

importances = featSelect.feature_importances_
indices = sorted(range(len(importances)), key=lambda i:importances[i], reverse=True)
                                                    
print("Feature ranking:")
for f in range(features.shape[1]):
    print("%d. feature %d (%f)" % (f + 1, indices[f], importances[indices[f]]))


top_features = features.iloc[:, indices[:11]]
print(top_features.head(5))
#Data Split
X_train, X_test, y_train, y_test = train_test_split(top_features, target, test_size=0.25,stratify=target)



#Apply Classificatoin/Prediction Model
clf_top = LogisticRegression(max_iter=1000,multi_class='multinomial',solver='lbfgs') 
clf_top.fit(X_train, y_train)
y_p = clf_top.predict(X_test[top_features.columns])


# Accuracy
# print(f"for {i} columns Accuracy:", clf_top.score(X_test, y_test))
print(f"Accuracy with Train and Test as {75} and {25} respectively:", accuracy_score(y_test, y_p))



#Cross-Validation Accuracy
scores = cross_val_score(clf_top, top_features,target , cv=10)
print("Cross-validation scores: {}".format(scores))
print("Average cross-validation score: {:.2f}".format(scores.mean()))
print("--------------------------------")

#Confusion Matrix
print("Confusion matrix:")
print(pd.crosstab(y_test, y_p, rownames=['True'], colnames=['Predicted'], margins=True))
print("--------------------------------")
