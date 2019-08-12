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
train = []


def predict(request):
    # 存储训练数据
    postBody = request.body
    global train_data
    # 这里有个坑，使用dic接收json数组，顺序会被打乱，类似于java中的map
    train_data = json.loads(postBody, object_pairs_hook=OrderedDict)
    load_data()
    show_data()
    return HttpResponse("hello")


def predict_mon(request):
    # 存储训练数据
    postBody = request.body
    global train_data
    # 这里有个坑，使用dic接收json数组，顺序会被打乱，类似于java中的map
    train_data = json.loads(postBody, object_pairs_hook=OrderedDict)
    load_data()
    # predict_by_mon()
    return HttpResponse("predict_mon")


def load_data():
    df = pd.read_csv(os.path.dirname(__file__) + '\\static\\tem.csv')
    df['Datetime'] = train_data['traintime']
    df['Count'] = train_data['traindata']
    df.index = pd.to_datetime(df['Datetime'], format='%Y-%m-%d')
    del df['Datetime']
    pd.DataFrame(df).to_csv(os.path.dirname(__file__) + '\\static\\out.csv')
    global train
    train = df
    # train = train.resample('3M').mean()
    if train_data['business'] == '开卡数':
        print(train)
    if train_data['business'] == '贷款数':
        print(df)
    if train_data['business'] == '存款数':
        print(df)
    if train_data['business'] == '中间收入':
        print(df)

        # df = pd.read_csv(os.path.dirname(__file__) + '\\static\\train.csv', nrows=11856)
        # global train
        # train = df[0:10392]
        # global test
        # test = df[10392:]
        # # Aggregating the dataset at daily level
        # df['Timestamp'] = pd.to_datetime(df['Datetime'], format='%d-%m-%Y %H:%M')
        # df.index = df['Timestamp']
        # df = df.resample('D').mean()
        # print(df)
        #
        # train['Timestamp'] = pd.to_datetime(train['Datetime'], format='%d-%m-%Y %H:%M')
        # train.index = train['Timestamp']
        # train = train.resample('D').mean()
        #
        # test['Timestamp'] = pd.to_datetime(test['Datetime'], format='%d-%m-%Y %H:%M')
        # test.index = test['Timestamp']
        # test = test.resample('D').mean()


# def predict_by_mon():


def show_data():
    # Plotting data
    load_data();
    train.Count.plot(figsize=(15, 8), title='Daily Ridership', fontsize=14)
    # test.Count.plot(figsize=(15, 8), title='Daily Ridership', fontsize=14)
    plt.show()


# def holt_winter():
    # load_data();
    # y_hat_avg = test.copy()
    # fit1 = ExponentialSmoothing(np.asarray(train['Count']), seasonal_periods=7, trend='add', seasonal='add', ).fit()
    # y_hat_avg['Holt_Winter'] = fit1.forecast(len(test))
    # plt.figure(figsize=(16, 8))
    # plt.plot(train['Count'], label='Train')
    # plt.plot(test['Count'], label='Test')
    # plt.plot(y_hat_avg['Holt_Winter'], label='Holt_Winter')
    # plt.legend(loc='best')
    # plt.show()
    # print(y_hat_avg)


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
