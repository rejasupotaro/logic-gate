from keras.models import Sequential
from keras.layers.core import Dense, Activation
from keras.optimizers import SGD
from keras.models import load_model
import tensorflow as tf
from keras import backend
from tensorflow.python.framework import graph_util


def create_dataset():
    x = [[0, 0], [0, 1], [1, 0], [1, 1]]
    y = [[0], [1], [1], [0]]
    return x, y


def create_model():
    model = Sequential()
    model.add(Dense(2, input_dim=2, activation='relu'))
    model.add(Dense(1, activation='sigmoid'))
    model.compile(loss='binary_crossentropy', optimizer=SGD(lr=0.1))
    return model


def convert_h5_to_pb(h5_file_path):
    num_output = 1
    prefix_output_node_names_of_final_network = 'output'

    backend.set_learning_phase(0)
    model = load_model(h5_file_path)
    pred = [None] * num_output
    pred_node_names = [None] * num_output
    for i in range(num_output):
        pred_node_names[i] = prefix_output_node_names_of_final_network + "_" + str(i)
        pred[i] = tf.identity(model.output[i], name=pred_node_names[i])
    print('output nodes are: {}'.format(pred))
    print('output nodes names are: {}'.format(pred_node_names))

    session = backend.get_session()
    graph_def = graph_util.convert_variables_to_constants(session, session.graph_def, pred_node_names)
    tf.train.write_graph(graph_def, 'models', 'xor_keras.pb', as_text=False)
    tf.train.write_graph(graph_def, 'models', 'xor_keras.pb.txt', as_text=True)


def main():
    x, y = create_dataset()
    model = create_model()
    model.fit(x, y, batch_size=4, epochs=1000)
    print(model.predict_proba(x))
    h5_file_path = 'models/xor_keras.h5'
    model.save(h5_file_path)
    convert_h5_to_pb(h5_file_path)


if __name__ == '__main__':
    main()
