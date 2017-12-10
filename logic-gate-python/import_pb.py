import tensorflow as tf


def import_graph(model_name):
    with tf.Graph().as_default():
        # Load
        with tf.gfile.FastGFile('models/{}'.format(model_name), 'rb') as file:
            graph_def = tf.GraphDef()
            graph_def.ParseFromString(file.read())
            tf.import_graph_def(graph_def, name='')

        # Analyze
        for op in tf.get_default_graph().get_operations():
            print(op.name)
            for output in op.outputs:
                print('  ', output.name)
        print('=' * 60)

        # Run
        with tf.Session() as session:
            y_pred = session.run('y_pred:0', feed_dict={
                'x:0': [[1, 1]]
            })
            print('y_pred: ', y_pred)


if __name__ == '__main__':
    import_graph('optimized_and.pb')
