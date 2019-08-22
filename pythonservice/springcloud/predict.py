import json
from collections import OrderedDict

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from django.http import HttpResponse
from statsmodels.tsa.api import ExponentialSmoothing
import statsmodels.api as sm
import os

# 从远端服务器拿到的训练数据
train_data = []

# 格式化的训练数据
format_train_data = []

# 直接用于训练的数据
train = []


# 按月份进行预测
def predict_mon(request):
    postBody = request.body
    global train_data
    train_data = json.loads(postBody)
    print(train_data)
    load_data()
    result = predict_by_mon(train_data[0]['period'])
    print(result)
    return HttpResponse(result)


# 按季度进行预测
def predict_season(request):
    postBody = request.body
    global train_data
    train_data = json.loads(postBody)
    load_data()
    result = predict_by_season(train_data['period'])
    print(result)
    return HttpResponse(result)


# 按年进行预测
def predict_season(request):
    postBody = request.body
    global train_data
    train_data = json.loads(postBody)
    load_data()
    result = predict_by_year(train_data['period'])
    print(result)
    return HttpResponse(result)


def load_data():
    global format_train_data
    format_train_data = []
    for data in train_data:
        result = {}
        df = pd.read_csv(os.path.dirname(__file__) + '\\static\\tem.csv')
        df['Datetime'] = data['traintime']
        df['Count'] = data['traindata']
        df.index = pd.to_datetime(df['Datetime'], format='%Y-%m-%d')
        del df['Datetime']
        result["data"] = df
        result["bankname"] = data["bankname"]
        format_train_data.append(result)


def predict_by_mon(period):
    result = []
    global format_train_data
    global train
    for data in format_train_data:
        train = data["data"]
        train = train.resample('M').sum()
        predicted = holt_winter(period)
        dic = {"predicted": predicted.tolist(), "bankname": data["bankname"]}
        result.append(dic)
    return json.dumps(result, ensure_ascii=False)


def predict_by_season(period):
    result = []
    global format_train_data
    global train
    for data in format_train_data:
        train = data["data"]
        train = train.resample('3M').sum()
        predicted = holt_winter(period)
        dic = {"predicted": predicted.tolist(), "bankname": data["bankname"]}
        result.append(dic)
    return json.dumps(result, ensure_ascii=False)


def predict_by_year(period):
    result = []
    global format_train_data
    global train
    for data in format_train_data:
        train = data["data"]
        train = train.resample('Y').sum()
        predicted = holt_winter(period)
        dic = {"predicted": predicted.tolist(), "bankname": data["bankname"]}
        result.append(dic)
    return json.dumps(result, ensure_ascii=False)


def holt_winter(period):
    predicted = np.arange(period)
    print('period:', period)
    global train
    fit1 = ExponentialSmoothing(np.asarray(train['Count']), seasonal_periods=7, trend='add', seasonal='add', ).fit()
    predicted = np.around(fit1.forecast(len(predicted)), 2)
    # 返回2个时间单位历史数据和period个时间单位预测数据
    out = np.hstack((np.asarray(train['Count'])[len(train) - 2:], predicted))
    return out

# def arima():
# load_data();
# y_hat_avg = test.copy()
# fit1 = sm.tsa.statespace.SARIMAX(train.Count, order=(2, 1, 4), seasonal_order=(0, 1, 1, 7)).fit()
# y_hat_avg['SARIMA'] = fit1.predict(start="2013-11-1", end="2013-12-31", dynamic=True)
# plt.figure(figsize=(16, 8))
# plt.plot(train['Count'], label='Train')
# plt.plot(test['Count'], label='Test')
# plt.plot(y_hat_avg['SARIMA'], label='SARIMA')
# plt.legend(loc='best')
# plt.show()
