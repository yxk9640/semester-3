import pandas as pd
import random
from sklearn.calibration import LinearSVC
from sklearn.neighbors import KNeighborsClassifier
from sklearn.preprocessing import OneHotEncoder
from sklearn.feature_selection import SelectKBest, VarianceThreshold, chi2, f_classif
from sklearn.naive_bayes import GaussianNB, MultinomialNB
from sklearn.model_selection import cross_val_score, train_test_split
from sklearn.tree import DecisionTreeClassifier

# Load example data with multi-class target variable
df = pd.read_csv('nba2021.csv')


# Extract target variable and features
target = df['Pos']
features = df.drop(['Pos','Player','Tm'], axis=1)

# for i in range(3,20):
selCol = SelectKBest(f_classif, k=10)
selectedFeatures = selCol.fit_transform(features,target)

# features_selected = SelectKBest(chi2, k=10).fit_transform(features
X_train, X_test, y_train, y_test = train_test_split(selectedFeatures, target, test_size=0.2)

clf = LinearSVC()
clf.fit(X_train, y_train)
# Evaluate the classifier on the test data
print('Accuracy:', clf.score(X_test, y_test))

scores = cross_val_score(clf, features,target , cv=10)
print("Cross-validation scores: {}".format(scores))
print("Average cross-validation score: {:.2f}".format(scores.mean()))
print("--------------------------------")