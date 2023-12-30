# -*- coding: utf-8 -*-
"""
Created on Wed Dec 27 15:53:14 2023

@author: Ayman zahir
"""

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import plotly.express as px
df = pd.read_csv("B:\studio\workspace\datamining\out.csv",sep=",")
infos= df.info()

#Checking pour les valeurs nulls 
df.isnull().sum()

#heatMap
plt.figure(figsize=(18,12))
corr=df.corr()
sns.heatmap(data = corr, annot = True, fmt = '.2g', linewidth = 1)
plt.show()
#cheking pour les valeurs uniques
unique=df.nunique()

#eliminons les colonnes qui ont beaucoups de valeurs uniques et qui ne peuvent pas nous aider dans notre classification
to_drop = ['policy_number','policy_bind_date','policy_state','insured_zip','incident_location','incident_date','incident_state','incident_city','insured_hobbies','auto_make','auto_model','auto_year','_c39']
df.drop(to_drop, inplace = True, axis = 1)

# checking pou multicolinéarité  pour eviter  des conclusions erronées

plt.figure(figsize = (18, 12))

corr = df.corr()
mask = np.triu(np.ones_like(corr, dtype = bool))

sns.heatmap(data = corr, mask = mask, annot = True, fmt = '.2g', linewidth = 1)
plt.show()

"""
En observant le graphique ci-dessus, on constate une forte corrélation entre 'Age' et le 'mounths as customer'. 
 Nous allons donc supprimer la colonne 'Age'. 
 De même, il existe une forte corrélation entre 'total_claim_amount', 
'injury_claim', 'property_claim', 'vehicle_claim' , 'total_claim'  est la somme 
 de tous les autres. Nous allons donc supprimer la colonne 'total_claims'
"""

df.drop(columns = ['age', 'total_claim_amount'], inplace = True, axis = 1)

#separation du label du data-frame

X=df.drop('fraud_reported',axis=1)
y=df['fraud_reported']

#Encodage des colonnes categoriels
cat_df=X.select_dtypes(include=['object'])

# affichage des valeurs uniques de chaque colonne
for col in cat_df.columns:
    print(f"{col}: \n{cat_df[col].unique()}\n")

cat_df=pd.get_dummies(cat_df,drop_first=True)

# extraction des colonnes numeriques

num_df = X.select_dtypes(include = ['int64'])

#comibinaison des colonnes numerique et categoriels pour avoir une seule dataset

X = pd.concat([num_df, cat_df], axis = 1)


plotnumber = 1
plt.figure(figsize = (25, 20))
for col in X.columns:
    if plotnumber <= 24:
        ax = plt.subplot(5, 5, plotnumber)
        sns.distplot(X[col])
        plt.xlabel(col, fontsize = 15)
        
    plotnumber += 1
    
plt.tight_layout()
plt.show()


#verifions notre data est ce qu'elle contient des valeurs abberantes "outliers"

plt.figure(figsize = (20, 15))
plotnumber = 1

for col in X.columns:
    if plotnumber <= 24:
        ax = plt.subplot(5, 5, plotnumber)
        sns.boxplot(X[col])
        plt.xlabel(col, fontsize = 15)
    
    plotnumber += 1
plt.tight_layout()
plt.show()

#Des valeurs aberrantes sont présentes dans certaines colonnes numériques. 
#Nous mettrons à l'échelle les colonnes numériques ultérieurement.

"""splitting data"""

from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.25)

X_train.head()
X_test.head()
y_train.head()
y_test.head()

num_df = X_train[['months_as_customer', 'policy_deductable', 'umbrella_limit','capital-gains', 'capital-loss', 'incident_hour_of_the_day','number_of_vehicles_involved', 'bodily_injuries', 'witnesses', 'injury_claim', 'property_claim','vehicle_claim']]

# Scaling des valeurs numerique dans otre dataset

from sklearn.preprocessing import StandardScaler

scaler = StandardScaler()
scaled_data = scaler.fit_transform(num_df)

scaled_num_df = pd.DataFrame(data = scaled_data, columns = num_df.columns, index = X_train.index)
scaled_num_df.head()

X_train.drop(columns = scaled_num_df.columns, inplace = True)

X_train = pd.concat([scaled_num_df, X_train], axis = 1)

"""---------------Model---------------"""
"""SVM"""
from sklearn.svm import SVC

svc = SVC()

svc.fit(X_train, y_train)

y_pred = svc.predict(X_test)

# accuracy_score, confusion_matrix and classification_report


from sklearn.metrics import accuracy_score, confusion_matrix, classification_report

svc_train_acc = accuracy_score(y_train, svc.predict(X_train))
svc_test_acc = accuracy_score(y_test, y_pred)

print(f"Training accuracy of Support Vector Classifier is : {svc_train_acc}")
print(f"Test accuracy of Support Vector Classifier is : {svc_test_acc}")

print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))

#matrice de confusion
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
cm = confusion_matrix(y_test, y_pred, labels=svc.classes_)
disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=svc.classes_)
disp.plot()
plt.show()

"""KNN"""
from sklearn.neighbors import KNeighborsClassifier

knn = KNeighborsClassifier(n_neighbors = 30)
knn.fit(X_train, y_train)

y_pred = knn.predict(X_test)
# accuracy_score, confusion_matrix and classification_report
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report
knn_train_acc = accuracy_score(y_train, knn.predict(X_train))
knn_test_acc = accuracy_score(y_test, y_pred)

print(f"Training accuracy of KNN is : {knn_train_acc}")
print(f"Test accuracy of KNN is : {knn_test_acc}")

print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))

#matrice de confusion
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
cm = confusion_matrix(y_test, y_pred, labels=knn.classes_)
disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=knn.classes_)
disp.plot()
plt.show()

"""DT"""

from sklearn.tree import DecisionTreeClassifier
dtc = DecisionTreeClassifier()
dtc.fit(X_train, y_train)

y_pred = dtc.predict(X_test)


# accuracy_score, confusion_matrix and classification_report

from sklearn.metrics import accuracy_score, confusion_matrix, classification_report

dtc_train_acc = accuracy_score(y_train, dtc.predict(X_train))
dtc_test_acc = accuracy_score(y_test, y_pred)

print(f"Training accuracy of Decision Tree is : {dtc_train_acc}")
print(f"Test accuracy of Decision Tree is : {dtc_test_acc}")

print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))
#matrice de confusion
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
cm = confusion_matrix(y_test, y_pred, labels=dtc.classes_)
disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=dtc.classes_)
disp.plot()
plt.show()

"""RF"""

from sklearn.ensemble import RandomForestClassifier
rand_clf = RandomForestClassifier(criterion= 'entropy', max_depth= 10, max_features= 'sqrt', min_samples_leaf= 1, min_samples_split= 3, n_estimators= 140)
rand_clf.fit(X_train, y_train)

y_pred = rand_clf.predict(X_test)

# accuracy_score, confusion_matrix and classification_report

from sklearn.metrics import accuracy_score, confusion_matrix, classification_report

rand_clf_train_acc = accuracy_score(y_train, rand_clf.predict(X_train))
rand_clf_test_acc = accuracy_score(y_test, y_pred)

print(f"Training accuracy of Random Forest is : {rand_clf_train_acc}")
print(f"Test accuracy of Random Forest is : {rand_clf_test_acc}")

print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))
#matrice de confusion
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
cm = confusion_matrix(y_test, y_pred, labels=rand_clf.classes_)
disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=rand_clf.classes_)
disp.plot()
plt.show()


"""Stochastic Gradient Boosting (SGB)"""
from sklearn.ensemble import GradientBoostingClassifier
sgb = GradientBoostingClassifier(subsample = 0.90, max_features = 0.70)
sgb.fit(X_train, y_train)

# accuracy score, confusion matrix and classification report of stochastic gradient boosting classifier

sgb_acc = accuracy_score(y_test, sgb.predict(X_test))

print(f"Training Accuracy of Stochastic Gradient Boosting is {accuracy_score(y_train, sgb.predict(X_train))}")
print(f"Test Accuracy of Stochastic Gradient Boosting is {sgb_acc} \n")

print(f"Confusion Matrix :- \n{confusion_matrix(y_test, sgb.predict(X_test))}\n")
print(f"Classification Report :- \n {classification_report(y_test, sgb.predict(X_test))}")
#matrice de confusion

from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
cm = confusion_matrix(y_test, y_pred, labels=sgb.classes_)
disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=sgb.classes_)
disp.plot()
plt.show()
