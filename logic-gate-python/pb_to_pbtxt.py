import tensorflow as tf


def pb_to_txt(model_name):
    with tf.Graph().as_default():
        with tf.gfile.FastGFile('models/{}'.format(model_name), 'rb') as file:
            graph_def = tf.GraphDef()
            graph_def.ParseFromString(file.read())
            tf.import_graph_def(graph_def, name='')

        tf.train.write_graph(graph_def, 'models', '{}.txt'.format(model_name), as_text=True)


if __name__ == '__main__':
    pb_to_txt("optimized_logic_and_gate.pb")
