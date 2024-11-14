# -*-coding: utf-8 -*-
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
import random
import numpy as np
import os
import pkg_resources.py2_warn
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
mnist = input_data.read_data_sets("d:/study/pj5ML/mnist/data/", one_hot=True)
wb = np.load('d:/study/pj5ML/data.npz')
x = tf.placeholder(tf.float32, [None, 784], name='x')
y = tf.placeholder(tf.float32, [None, 10], name='y')
w = tf.Variable(tf.random_normal([784, 10]), name='w')
b = tf.Variable(tf.random_normal([10]), name='b')
model = tf.nn.softmax(tf.matmul(x, w) + b)
loss = tf.reduce_mean(-tf.reduce_sum(y * tf.log(model), axis=1))
sess = tf.Session()
init = tf.global_variables_initializer()
sess.run(init)
sess.run(tf.assign(w,wb['w']))
sess.run(tf.assign(b,wb['b']))
correct_prediction = tf.equal(tf.argmax(model,1), tf.argmax(y, 1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
if os.path.isfile('d:\\study\\pj5ML\\logintest.txt'):
    os.remove('d:\\study\\pj5ML\\logintest.txt')
for i in range(1,6):
    c = random.randint(0, mnist.test.num_examples - 1)
    ac=sess.run(tf.argmax(mnist.test.labels[c:c+1],1))
    y= sess.run(tf.argmax(model, 1),feed_dict={x:mnist.test.images[c:c+1]})
    f = open("d:\\study\\pj5ML\\logintest.txt", 'a')
    d1='{},'.format(ac[0])
    d2='{},'.format(y[0])
    f.write(d1)
    f.write(d2)
f.close()

